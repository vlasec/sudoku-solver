package cz.vlasec.sudoku.solvers.sweep;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;
import cz.vlasec.sudoku.core.solver.SolverCallback;
import cz.vlasec.sudoku.core.solver.Sweep;
import cz.vlasec.sudoku.core.solver.SweepRequest;
import cz.vlasec.sudoku.solvers.utils.Cycle;

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
            TileCycle cycle = new TileCycle(pairs.removeFirst());
            while(true) {
                if (!tryAdd(pairs, cycle)) {
                    break;
                }
                if (cycle.isComplete()) {
                    clearForCycle(request, cycle);
                    break;
                }
            }
        }
    }

    private boolean tryAdd(LinkedList<Pair> pairs, TileCycle cycle) {
        for (Iterator<Pair> iterator = pairs.iterator(); iterator.hasNext(); ) {
            Pair pair = iterator.next();
            if (cycle.add(pair)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private void clearForCycle(SweepRequest request, TileCycle cycle) {
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

    private static class TileCycle extends Cycle<Tile, Value, Pair, ValuePair> implements Iterable<ValuePair> {
        public TileCycle(Pair pair) {
            super(pair);
        }

        protected Set<Tile> getBoth(Pair pair) {
            return pair.tiles;
        }

        protected Tile getOther(Pair pair, Tile thatTile) {
            for (Tile tile : pair.tiles) {
                if (tile != thatTile) {
                    return tile;
                }
            }
            throw new IllegalStateException("There was supposed to be another tile in the pair!");
        }

        protected Value getGlue(Pair pair) {
            return pair.matchingValue;
        }

        protected ValuePair createO(Value leftGlue, Tile value, Value rightGlue) {
            return new ValuePair(value, Arrays.asList(leftGlue, rightGlue));
        }

        protected Pair createI(Tile leftValue, Value glue, Tile rightValue) {
            return new Pair(glue, new HashSet<>(Arrays.asList(leftValue, rightValue)));
        }

        public Iterator<ValuePair> iterator() {
            return iteratorO();
        }
    }
}
