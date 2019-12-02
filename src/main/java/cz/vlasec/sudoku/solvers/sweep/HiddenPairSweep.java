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
            LinkedList<Pair> cycle = new LinkedList<>();
            cycle.add(pairs.removeFirst());
            while(true) {
                boolean added = false;
                for (Iterator<Pair> iterator = pairs.iterator(); iterator.hasNext(); ) {
                    Pair pair = iterator.next();
                    if (pair.touches(cycle.getLast())) {
                        cycle.addLast(pair);
                        iterator.remove();
                        added = true;
                        break;
                    } else if (pair.touches(cycle.getFirst())) {
                        cycle.addFirst(pair);
                        iterator.remove();
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    break;
                }
                if (cycle.getFirst().matches(cycle.getLast())
                        || (cycle.size() > 2 && cycle.getFirst().touches(cycle.getLast()))) {
                    clearForCycle(request, cycle);
                    break;
                }

            }
        }
    }

    private void clearForCycle(SweepRequest request, LinkedList<Pair> cycle) {
        Pair previousPair = cycle.getLast();
        for (Pair pair : cycle) {
            for (Tile tile : pair.pair) {
                if (tile.value() != null) {
                    System.out.println("WTF!");
                }
                if (previousPair.pair.contains(tile)) {
                    for (Value value : new HashSet<>(tile.candidates())) {
                        if (value != pair.matchingValue && value != previousPair.matchingValue) {
                            request.getCallback().ruleOutCandidate(tile, value);
                        }
                    }
                }
            }
            previousPair = pair;
        }
    }

    private static class Pair {
        private Value matchingValue;
        private Set<Tile> pair;

        private Pair(Value matchingValue, Set<Tile> pair) {
            this.matchingValue = matchingValue;
            this.pair = pair;
        }

        private static Pair create(Value matchingValue, Set<Tile> pair) {
            if (pair.size() != 2) {
                throw new IllegalStateException("A pair must be of size 2");
            }
            return new Pair(matchingValue, pair);
        }

        public boolean matches(Pair other) {
            return Objects.equals(this.pair, other.pair);
        }

        public boolean touches(Pair other) {
            for (Tile tile : pair) {
                if (other.pair.contains(tile)) {
                    return true;
                }
            }
            return false;
        }
    }
}
