package cz.vlasec.sudoku.core.board;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem as a whole - This object also has mutable access to tiles. The solver shouldn't give away the reference.
 */
public class Problem {
    private final Map<Tile, Tile.TileMutator> mutatorMap;
    private final Board board;
    private final Rules rules;

    private Problem(Map<Tile, Tile.TileMutator> mutatorMap, Board board, Rules rules) {
        this.mutatorMap = mutatorMap;
        this.board = board;
        this.rules = rules;
    }

    public static Problem createProblem(Rules rules) {
        Map<Tile, Tile.TileMutator> mutatorMap = new HashMap<>();
        int xSize = rules.xSize();
        int ySize = rules.ySize();
        Tile[][] tiles = new Tile[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Tile.TileMutator mutator = Tile.createTile(rules.values(), "[" + i + "," + j + "]");
                tiles[i][j] = mutator.getTile();
                mutatorMap.put(mutator.getTile(), mutator);
            }
        }
        Board board = Board.create(tiles, rules.sets(tiles));
        return new Problem(mutatorMap, board, rules);
    }

    public static Problem copyProblem(Rules rules, Board originalBoard) {
        Map<Tile, Tile.TileMutator> mutatorMap = new HashMap<>();
        int xSize = rules.xSize();
        int ySize = rules.ySize();
        Tile[][] tiles = new Tile[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Tile.TileMutator mutator = originalBoard.tileAt(i, j).copyTile();
                tiles[i][j] = mutator.getTile();
                mutatorMap.put(mutator.getTile(), mutator);
            }
        }
        Board board = Board.create(tiles, rules.sets(tiles));
        return new Problem(mutatorMap, board, rules);
    }

    public Tile.TileMutator getMutator(Tile tile) {
        return mutatorMap.get(tile);
    }

    public Board getBoard() {
        return board;
    }

    public Rules getRules() {
        return rules;
    }
}
