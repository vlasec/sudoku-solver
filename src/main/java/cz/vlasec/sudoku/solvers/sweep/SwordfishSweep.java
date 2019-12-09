package cz.vlasec.sudoku.solvers.sweep;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;
import cz.vlasec.sudoku.core.board.TileSet.TileSetType;
import cz.vlasec.sudoku.core.solver.SolverCallback;
import cz.vlasec.sudoku.core.solver.Sweep;
import cz.vlasec.sudoku.solvers.utils.Cycle;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SwordfishSweep implements Sweep {
    @Override
    public void sweep(SolverCallback callback, Board board, List<Value> allValues) {
        Map<TileSetType, List<TileSet>> types = board.tileSets().stream()
                .collect(Collectors.groupingBy(TileSet::type));
        for (Map.Entry<TileSetType, List<TileSet>> entry : types.entrySet()) {
            for (Value value : allValues) {
                List<TileSet> mainDimension = entry.getValue().stream()
                        .filter(set -> set.candidatesForValue(value).size() == 2)
                        .collect(Collectors.toList());
                if (mainDimension.size() < 2) {
                    continue;
                }
                LinkedList<MainDimensionPair> pairs = mainDimension.stream()
                        .flatMap((TileSet mainSet) -> createMainDimensionPair(mainSet, board, value))
                        .collect(Collectors.toCollection(LinkedList::new));
                while (!pairs.isEmpty()) {
                    DimensionCycle cycle = new DimensionCycle(pairs.removeFirst());
                    while (true){
                        if (!tryAdd(pairs, cycle)) {
                            break;
                        }
                        if (cycle.isComplete()) {
                            clearForCycle(callback, cycle, value);
                            break;
                        }
                    }
                }

            }
        }
    }

    private boolean tryAdd(LinkedList<MainDimensionPair> pairs, DimensionCycle cycle) {
        for (Iterator<MainDimensionPair> iterator = pairs.iterator(); iterator.hasNext(); ) {
            MainDimensionPair pair = iterator.next();
            if (cycle.add(pair)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private Stream<MainDimensionPair> createMainDimensionPair(TileSet mainSet, Board board, Value value) {
        Set<TileSet> secondarySets = mainSet.candidatesForValue(value).stream()
                .map(tile -> board.setsAt(tile).stream()
                        .filter(set -> set.type().isPerpendicularTo(mainSet.type()))
                        .findAny().orElse(null)
                ).filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (secondarySets.size() < 2) {
            return Stream.empty();
        }
        return Stream.of(new MainDimensionPair(mainSet, secondarySets));
    }

    private void clearForCycle(SolverCallback callback, DimensionCycle cycle, Value value) {
        for (SecondaryDimensionTilesPair pair : cycle) {
            callback.ruleOutCandidate(pair.secondarySet, pair.toKeep, value);
        }
    }

    private static class MainDimensionPair {
        private final TileSet mainSet;
        private final Set<TileSet> secondarySets;

        public MainDimensionPair(TileSet mainSet, Set<TileSet> secondarySets) {
            this.mainSet = mainSet;
            this.secondarySets = secondarySets;
        }
    }

    private static class SecondaryDimensionTilesPair {
        private final Set<Tile> toKeep;
        private final TileSet secondarySet;

        public SecondaryDimensionTilesPair(TileSet secondarySet, Set<Tile> toKeep) {
            this.toKeep = toKeep;
            this.secondarySet = secondarySet;
        }
    }

    private static class DimensionCycle extends Cycle<TileSet, TileSet, MainDimensionPair, SecondaryDimensionTilesPair>
            implements Iterable<SecondaryDimensionTilesPair> {

        protected DimensionCycle(MainDimensionPair firstEntry) {
            super(firstEntry);
        }

        @Override
        protected Set<TileSet> getBoth(MainDimensionPair mainDimensionPair) {
            return mainDimensionPair.secondarySets;
        }

        @Override
        protected TileSet getOther(MainDimensionPair mainDimensionPair, TileSet one) {
            for (TileSet tileSet : mainDimensionPair.secondarySets) {
                if (tileSet != one) {
                    return tileSet;
                }
            }
            throw new IllegalStateException("There was supposed to be another tile in the pair!");
        }

        @Override
        protected TileSet getGlue(MainDimensionPair mainDimensionPair) {
            return mainDimensionPair.mainSet;
        }

        @Override
        protected SecondaryDimensionTilesPair createO(TileSet leftGlue, TileSet value, TileSet rightGlue) {
            HashSet<Tile> tilesToKeep = new HashSet<>(value.intersection(leftGlue));
            tilesToKeep.addAll(value.intersection(rightGlue));
            return new SecondaryDimensionTilesPair(value, tilesToKeep);
        }

        @Override
        protected MainDimensionPair createI(TileSet leftValue, TileSet glue, TileSet rightValue) {
            return new MainDimensionPair(glue, new HashSet<>(Arrays.asList(leftValue, rightValue)));
        }

        @Override
        public Iterator<SecondaryDimensionTilesPair> iterator() {
            return iteratorO();
        }
    }


}
