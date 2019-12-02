package cz.vlasec.sudoku.solvers.sweep;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;
import cz.vlasec.sudoku.core.solver.SolverCallback;
import cz.vlasec.sudoku.core.solver.Sweep;
import cz.vlasec.sudoku.core.solver.SweepRequest;

import java.util.*;

public class HiddenPairSweep implements Sweep {

    public void sweep(SweepRequest request) {
        for (TileSet tileSet : request.getBoard().tileSets()) {
            LinkedList<Pair> pairs = new LinkedList<>();
            for (Value value : request.getAllValues()) {
                Set<Tile> potentialPair = tileSet.candidatesForValue(value);
                if (potentialPair.size() == 2) {
                    pairs.add(Pair.create(value, potentialPair));
                }
            }
            findAndClearPairsOrCycles(request, pairs);
        }
    }

    @Override
    public void sweep(SolverCallback callback, Board board, List<Value> allValues) {
        sweep(new SweepRequest(callback, board, allValues));
    }

    /**
     * Finds pairs or cycles in given list of pairs.
     * @param pairs Caution, destructive operations performed on this input.
     */
    private void findAndClearPairsOrCycles(SweepRequest request, LinkedList<Pair> pairs) {
        while (!pairs.isEmpty()) {
            Cycle cycle = new Cycle(pairs.removeFirst());
            while(true) {
                boolean added = false;
                for (Iterator<Pair> iterator = pairs.iterator(); iterator.hasNext(); ) {
                    Pair pair = iterator.next();
                    if (added = cycle.add(pair)) {
                        iterator.remove();
                        break;
                    }
                }
                if (!added) {
                    break;
                }
                if (cycle.isComplete()) {
                    clearForCycle(request, cycle);
                    break;
                }
            }
        }
    }

    private void clearForCycle(SweepRequest request, Cycle cycle) {
        for (ValuePair pair : cycle) {
            Set<Value> valuesToRemove = new HashSet<>(pair.tile.candidates());
            valuesToRemove.removeAll(pair.values);
            request.getCallback().ruleOutCandidate(pair.tile, valuesToRemove);
        }
    }

    private static class Pair {
        private final Value matchingValue;
        private final Set<Tile> tiles;

        private Pair(Value matchingValue, Set<Tile> tiles) {
            this.matchingValue = matchingValue;
            this.tiles = new HashSet<>(tiles);
        }

        private static Pair create(Value matchingValue, Set<Tile> pair) {
            if (pair.size() != 2) {
                throw new IllegalStateException("A pair must be of size 2");
            }
            return new Pair(matchingValue, pair);
        }

        @Override
        public String toString() {
            return "Value " + matchingValue + " in " + tiles;
        }
    }

    private static class ValuePair {
        private final Tile tile;
        private final List<Value> values;

        public ValuePair(Tile tile, List<Value> values) {
            this.tile = tile;
            this.values = Collections.unmodifiableList(values);
        }

        @Override
        public String toString() {
            return "Tile " + tile + " should only contain " + values;
        }
    }

    private static class Cycle implements Iterable<ValuePair> {
        private CycleTileNode first;
        private CycleTileNode last;

        public Cycle(Pair pair) {
            Iterator<Tile> iterator = pair.tiles.iterator();
            this.first = new CycleTileNode(iterator.next());
            this.last = new CycleTileNode(iterator.next());
            CycleValueNode valueNode = new CycleValueNode(pair.matchingValue);
            valueNode.setPrevious(this.first);
            valueNode.setNext(this.last);
        }

        private boolean isComplete() {
            return first == last;
        }

        private boolean add(Pair pair) {
            if (isComplete()) {
                return false;
            }
            if (pair.tiles.contains(first.tile)) {
                first.setPreviousValue(new CycleValueNode(pair.matchingValue));
                Tile otherTile = getOtherTile(pair, first.tile);
                first.previousValue.setPrevious((otherTile == last.tile) ? last : new CycleTileNode(otherTile));
                first = first.previousValue.previous;
                return true;
            } else if (pair.tiles.contains(last.tile)) {
                last.setNextValue(new CycleValueNode(pair.matchingValue));
                Tile otherTile = getOtherTile(pair, last.tile);
                last.nextValue.setNext((otherTile == first.tile) ? first : new CycleTileNode(otherTile));
                last = last.nextValue.next;
                return true;
            } else {
                return false;
            }
        }

        private static Tile getOtherTile(Pair pair, Tile thatTile) {
            for (Tile tile : pair.tiles) {
                if (tile != thatTile) {
                    return tile;
                }
            }
            throw new IllegalStateException("There was supposed to be another tile in the pair!");
        }

        @Override
        public Iterator<ValuePair> iterator() {
            if (!isComplete()) {
                throw new IllegalStateException("This is not a complete cycle!");
            }
            return new MyIterator();
        }

        private class MyIterator implements Iterator<ValuePair> {
            private CycleTileNode currentNode = first;
            private boolean atStart = true;

            @Override
            public boolean hasNext() {
                return atStart || (currentNode != last);
            }

            @Override
            public ValuePair next() {
                ValuePair pair = new ValuePair(currentNode.tile,
                        Arrays.asList(currentNode.previousValue.value, currentNode.nextValue.value));
                currentNode = currentNode.nextValue.next;
                atStart = false;
                return pair;
            }
        }

        private static class CycleTileNode {
            private final Tile tile;
            private CycleValueNode previousValue;
            private CycleValueNode nextValue;

            public CycleTileNode(Tile tile) {
                this.tile = Objects.requireNonNull(tile, "null tile in cycle");
            }

            public void setPreviousValue(CycleValueNode previousValue) {
                this.previousValue = previousValue;
                previousValue.next = this;
            }

            public void setNextValue(CycleValueNode nextValue) {
                this.nextValue = nextValue;
                nextValue.previous = this;
            }
        }
        private static class CycleValueNode {
            private final Value value;
            private CycleTileNode previous;
            private CycleTileNode next;

            public CycleValueNode(Value value) {
                this.value = Objects.requireNonNull(value, "null value in cycle");
            }

            public void setPrevious(CycleTileNode previous) {
                this.previous = previous;
                previous.nextValue = this;
            }

            public void setNext(CycleTileNode next) {
                this.next = next;
                next.previousValue = this;
            }
        }
    }
}
