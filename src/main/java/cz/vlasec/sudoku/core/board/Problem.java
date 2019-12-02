package cz.vlasec.sudoku.core.board;

import cz.vlasec.sudoku.core.SudokuStateException;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile.TileMutator;
import cz.vlasec.sudoku.core.board.TileSet.TileSetMutator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Problem as a whole - This object also has mutable access to tiles. The solver shouldn't give away the reference.
 */
public class Problem {
    private final Map<Tile, TileMutator> mutatorMap;
    private final Map<TileSet, TileSetMutator> setMutatorMap;
    private final Board board;
    private final Rules rules;

    private Problem(Map<Tile, TileMutator> mutatorMap, Map<TileSet, TileSetMutator> setMutatorMap, Board board, Rules rules) {
        this.mutatorMap = mutatorMap;
        this.setMutatorMap = setMutatorMap;
        this.board = board;
        this.rules = rules;
    }

    public static Problem createProblem(Rules rules) {
        Map<Tile, TileMutator> mutatorMap = new HashMap<>();
        int xSize = rules.xSize();
        int ySize = rules.ySize();
        Tile[][] tiles = new Tile[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                TileMutator mutator = Tile.createTile(rules.values(), "[" + i + "," + j + "]");
                tiles[i][j] = mutator.getTile();
                mutatorMap.put(mutator.getTile(), mutator);
            }
        }
        List<TileSetMutator> setMutators = rules.sets(tiles);
        Board board = Board.create(tiles, toTileSets(setMutators));
        return new Problem(mutatorMap, toSetMutatorsMap(setMutators), board, rules);
    }

    public static Problem copyProblem(Rules rules, Board originalBoard) {
        Map<Tile, TileMutator> mutatorMap = new HashMap<>();
        int xSize = rules.xSize();
        int ySize = rules.ySize();
        Tile[][] tiles = new Tile[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                TileMutator mutator = originalBoard.tileAt(i, j).copyTile();
                tiles[i][j] = mutator.getTile();
                mutatorMap.put(mutator.getTile(), mutator);
            }
        }
        List<TileSetMutator> setMutators = rules.sets(tiles);
        Board board = Board.create(tiles, toTileSets(setMutators));
        return new Problem(mutatorMap, toSetMutatorsMap(setMutators), board, rules);
    }

    private static List<TileSet> toTileSets(List<TileSetMutator> setMutators) {
//        return setMutators.stream()
//                .map(TileSetMutator::getSet)
//                .collect(Collectors.toList());
        List<TileSet> result = new ArrayList<>(setMutators.size());
        for (TileSetMutator mutator : setMutators) {
            result.add(mutator.getSet());
        }
        return result;
    }

    private static Map<TileSet, TileSetMutator> toSetMutatorsMap(List<TileSetMutator> setMutators) {
//        return setMutators.stream()
//                .collect(Collectors.toMap(TileSetMutator::getSet, identity()));
        Map<TileSet, TileSetMutator> result = new HashMap<>();
        for (TileSetMutator mutator : setMutators) {
            result.put(mutator.getSet(), mutator);
        }
        return result;
    }

    private TileMutator getMutator(Tile tile) {
        return mutatorMap.get(tile);
    }

    private TileSetMutator getMutator(TileSet tileSet) {
        return setMutatorMap.get(tileSet);
    }

    public void setValue(Tile tile, Value value) {
        if (!tile.candidates().contains(value)) {
            throw new SudokuStateException("A value that is not one of candidates was set to a tile", tile);
        }
        if (tile.value() != null && tile.value() != value) {
            throw new SudokuStateException("A tile already has a value, but there was an attempt to set it to " + value, tile);
        }
        getMutator(tile).setValue(value);
    }

    public void removeCandidate(Tile tile, Value exCandidate) {
        if (tile.value() == exCandidate) {
            throw new SudokuStateException("Tile's value ruled out as a candidate!", tile);
        }
        if (!tile.candidates().contains(exCandidate)) {
            return;
        }
        if (tile.candidates().size() == 1) {
            throw new SudokuStateException("Removing last candidate from tile!", tile);
        }
//        if ("G".equals(exCandidate.getDescription()) && tile.description().matches("\\[\\d{1,2},48]")) {
//            System.out.println("Oh shit not again ...");
//        }
        for (TileSet set : getBoard().setsAt(tile)) {
            if (set.candidatesForValue(exCandidate).size() == 1) {
                throw new SudokuStateException("Removing last candidate tile for value from tile set!", tile);
            }
        }
        getMutator(tile).removeCandidate(exCandidate);
        for (TileSet set : getBoard().setsAt(tile)) {
            getMutator(set).removeCandidate(tile, exCandidate);
        }
    }

    public Board getBoard() {
        return board;
    }

    public Rules getRules() {
        return rules;
    }
}
