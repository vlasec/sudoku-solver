package cz.vlasec.sudoku.core.board;

import cz.vlasec.sudoku.core.board.TileSet.TileSetMutator;

import java.util.*;
import java.util.function.Consumer;

public abstract class Rules {
    protected Rules() {}

    /**
     * A list of values used in this problem. Should be immutable or a copy.
     */
    public abstract List<Value> values();

    /**
     * Creates sets (e.g. rows, columns, cells, diagonals) of tiles from the grid.
     * The solution is required to find a solution so that there are no duplicates in these sets.
     */
    public abstract List<TileSetMutator> sets(Tile[][] grid); //Consumer<Set<Tile>> setConsumer?

    /**
     * Tests the board for validity. A board is valid as long as there are no broken rules. Blank tiles are allowed.
     */
    public boolean isValid(Board board) {
        for (final TileSet tileSet : board.tileSets()) {
            if (!isValid(tileSet)) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid(TileSet tileSet) {
        final Set<Value> values = new HashSet<>();
        for (final Tile tile : tileSet) {
            final Value value = tile.value();
            // if value is not null, but duplicate, tile set is invalid
            if (value != null && !values.add(tile.value())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests the board for completeness. A board is complete as long as it is valid and there are no blank tiles.
     */
    public boolean isComplete(Board board) {
        for (final TileSet tileSet : board.tileSets()) {
            if (!isComplete(tileSet)) {
                return false;
            }
        }
        return true;
    }

    /**
     * The basics implementation for any sudoku. Can be overridden if the rules are more complex.
     */
    public boolean isComplete(TileSet tileSet) {
        final Set<Value> values = new HashSet<>();
        for (final Tile tile : tileSet) {
            final Value value = tile.value();
            // if value is null, tile set is incomplete
            if (value == null) {
                return false;
            }
            // if value is duplicate, tile set is invalid
            if (!values.add(tile.value())) {
                return false;
            }
        }
        return true;
    }

    /**
     * X-size of the board. In cases of some special intersecting puzzles it can differ from {@link #ySize}.
     * Note that the board doesn't necessarily have tiles on the whole x * y area.
     */
    public abstract int xSize();

    /**
     * Y-size of the board. In cases of some special intersecting puzzles it can differ from {@link #xSize}
     * Note that the board doesn't necessarily have tiles on the whole x * y area.
     */
    public abstract int ySize();

    protected Value createValue(String description) {
        return new Value(description);
    }

    public static class Value {
        private final String description;

        private Value(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

}
