package cz.vlasec.sudoku.solvers;

import cz.vlasec.sudoku.core.SudokuException;
import cz.vlasec.sudoku.core.board.*;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.solver.Reaction;
import cz.vlasec.sudoku.core.solver.Solver;
import cz.vlasec.sudoku.core.solver.SolverCallback;
import cz.vlasec.sudoku.core.solver.Sweep;
import cz.vlasec.sudoku.solvers.reaction.BasicTileReaction;
import cz.vlasec.sudoku.solvers.reaction.LastInSetReaction;
import cz.vlasec.sudoku.solvers.sweep.HiddenPairSweep;
import cz.vlasec.sudoku.solvers.sweep.NakedPairSweep;
import cz.vlasec.sudoku.solvers.sweep.SetIntersectionSweep;
import cz.vlasec.sudoku.solvers.sweep.SwordfishSweep;

import java.util.*;

/**
 * Can solve classic rules and some minor modifications of it that don't add anything too special.
 */
public class ClassicSolver implements Solver {
    public static List<Reaction> REACTIONS = Collections.unmodifiableList(Arrays.asList(new Reaction[]{
            new BasicTileReaction(),
            new LastInSetReaction(),
    }));
    public static List<Sweep> SWEEPS = Collections.unmodifiableList(Arrays.asList(new Sweep[]{
            new SetIntersectionSweep(),
            new NakedPairSweep(),
            new HiddenPairSweep(),
            new SwordfishSweep(),
    }));

    @Override
    public List<Board> solve(Board problem, Rules rules) {
        Puzzle x = Puzzle.copyProblem(rules, problem);
        Statistics stats = new Statistics();
        StatefulSolver solver = new StatefulSolver(x, stats);
        solver.initialSweep();
        try {
            List<Board> boards = solver.solve();
            stats.writeStatistics();
            return boards;
        } catch (SudokuException ex) {
            stats.writeStatistics();
            throw ex;
        }
    }

    private class StatefulSolver implements SolverCallback {
        private final Puzzle puzzle;
        private final Board board;
        private final Rules rules;
        private final Queue<Event> events = new LinkedList<>();
        private final Statistics stats;
        private Object activeComponent;
        private int currentDepth = 0;

        public StatefulSolver(Puzzle puzzle, Statistics stats) {
            this(puzzle, stats, 0);
        }

        public StatefulSolver(Puzzle puzzle, Statistics stats, int currentDepth) {
            this.puzzle = puzzle;
            this.board = puzzle.getBoard();
            this.rules = puzzle.getRules();
            this.stats = stats;
            this.currentDepth = currentDepth;
        }

        public List<Board> solve() {
            currentDepth++;
            stats.depth(currentDepth);
            Event event;
            do {
                while ((event = events.poll()) != null) {
                    reactionStep(event);
                }
                // a save attempt with a sweep
                if (!rules.isComplete(board)) {
                    sweepStep();
                }
            } while (!events.isEmpty());
            final List<Board> result;
            if (rules.isComplete(board)) {
                System.out.println(String.format("Solved in %.3f seconds", (System.currentTimeMillis() - stats.startMillis) / 1000.0));
                result = Collections.singletonList(board);
            } else {
                result = splitUp();
            }
            currentDepth--;
            return result;
        }

        public List<Board> splitUp() {
            if (currentDepth == 1) {
                System.out.println("Splitting with " + puzzle.unsolvedTiles() + " tiles unsolved.");
            }
            int fittestSize = Integer.MAX_VALUE;
            int fittestX = 0, fittestY = 0;
            Tile fittestTile = null;
            for (int x = 0; x < rules.xSize(); x++) {
                for (int y = 0; y < rules.ySize(); y++) {
                    Tile tile = board.tileAt(x, y);
                    if (tile != null && tile.value() == null && tile.candidates().size() < fittestSize) {
                        fittestTile = tile;
                        fittestSize = tile.candidates().size();
                        fittestX = x;
                        fittestY = y;
                    }
                }
            }
            if (fittestTile == null) {
                throw new IllegalStateException("No fittest tile to split up on found, even though board is not solved yet.");
            }
            List<Value> candidates = new ArrayList<>(fittestTile.candidates());
            List<Board> results = new ArrayList<>(1);
            stats.mapCopyCount.inc();
            for (int i = 0; i < fittestSize; i++) {
                try {
                    // Saving some resources on split by using the current solver for the last part.
                    StatefulSolver solver = (i == fittestSize - 1) ? this
                            : new StatefulSolver(Puzzle.copyProblem(rules, board), stats, currentDepth);
                    Object previousActiveComponent = activeComponent;
                    solver.activeComponent = "SplitUp";
                    solver.setValue(solver.board.tileAt(fittestX, fittestY), candidates.get(i));
                    solver.activeComponent = previousActiveComponent;
                    results.addAll(solver.solve());
                } catch (SudokuException e) {
                    // noop, it was a split
                }
            }
            return results;
        }

        private void reactionStep(Event event) {
            for (Reaction reaction : REACTIONS) {
                Object previousActiveComponent = activeComponent;
                activeComponent = reaction;
                if (event.eventType == EventType.SET_VALUE) {
                    reaction.onValueSet(this, board, event.affectedTile, event.value);
                } else if (event.eventType == EventType.RULE_OUT) {
                    reaction.onCandidateRuledOut(this, board, event.affectedTile, event.value);
                }
                activeComponent = previousActiveComponent;
            }
        }

        private void sweepStep() {
            for (Sweep sweep : SWEEPS) {
                Object previousActiveComponent = activeComponent;
                activeComponent = sweep;
                sweep.sweep(this, board, rules.values());
                activeComponent = previousActiveComponent;
            }
        }

        private void initialSweep() {
            activeComponent = "InitialSweep";
            for (int i = 0; i < puzzle.getRules().xSize(); i++) {
                for (int j = 0; j < puzzle.getRules().ySize(); j++) {
                    Tile tile = puzzle.getBoard().tileAt(i, j);
                    if (tile.value() != null) {
                        valueWasSet(tile, tile.value());
                    }
                }
            }
            activeComponent = null;
        }

        @Override
        public void setValue(Tile tile, Value value) {
            if (tile.value() == value) {
                return;
            }
            puzzle.setValue(tile, value);
            valueWasSet(tile, value);
        }

        private void valueWasSet(Tile tile, Value value) {
            for (Value exCandidate : new HashSet<>(tile.candidates())) {
                if (exCandidate != value) {
                    ruleOutCandidate(tile, exCandidate);
                }
            }
            events.add(new Event(tile, value, EventType.SET_VALUE));
        }

        @Override
        public void ruleOutCandidate(Tile tile, Value exCandidate) {
            if (tile.candidates().contains(exCandidate)) {
                if (activeComponent == null) {
                    System.out.println("???");
                }
                stats.hitCounter(activeComponent).inc();
                puzzle.removeCandidate(tile, exCandidate);
                events.add(new Event(tile, exCandidate, EventType.RULE_OUT));
            }
        }
    }

    private static class Event {
        private Tile affectedTile;
        private Value value;
        private EventType eventType;

        public Event(Tile affectedTile, Value value, EventType eventType) {
            this.affectedTile = affectedTile;
            this.value = value;
            this.eventType = eventType;
        }
    }

    private enum EventType {
        SET_VALUE,
        RULE_OUT,
    }

    private static class Statistics {
        private long startMillis = System.currentTimeMillis();
        private Map<Object, Counter> ruleHits = new HashMap<>();
        private Counter mapCopyCount = new Counter();
        private long lastCheckMillis = System.currentTimeMillis();
        private int maxReachedDepth = 0;
        public Counter hitCounter(Object obj) {
            if (System.currentTimeMillis() - 5000 > lastCheckMillis) {
                writeStatistics();
            }
            String name = (obj == null ? "null" : (obj instanceof String ? (String) obj : obj.getClass().getSimpleName()));
            return ruleHits.computeIfAbsent(name, $ -> new Counter());
        }

        public void depth(int depth) {
            if (maxReachedDepth < depth) {
                maxReachedDepth = depth;
            }
        }

        void writeStatistics() {
            lastCheckMillis = System.currentTimeMillis();
            System.out.println("Statistics of rule usage: " + ruleHits);

            System.out.println("Map split count: " + mapCopyCount);
            System.out.println("Max depth so far: " + maxReachedDepth);
            System.out.println("Duration so far: " + (lastCheckMillis - startMillis) / 1000.0 + " s" );
        }
    }

    private static class Counter {
        int count = 0;

        void inc() {
            count++;
        }

        int get() {
            return count;
        }

        @Override
        public String toString() {
            return "" + count;
        }
    }
}
