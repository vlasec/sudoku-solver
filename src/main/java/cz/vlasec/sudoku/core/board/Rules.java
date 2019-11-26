package cz.vlasec.sudoku.core.board;

import cz.vlasec.sudoku.core.utils.CollectionFactory;

import java.util.*;

public abstract class Rules {
    private final List<Value> values;

    protected Rules() {
        this.values = Collections.unmodifiableList(new ArrayList<>(createValues()));
    }

    public List<Value> values() {
        return values;
    }

    /**
     * Creates sets (e.g. rows, columns, cells, diagonals) of tiles from the grid.
     * The solution is required to find a solution so that there are no duplicates in these sets.
     */
    public abstract Collection<Set<Tile>> sets(Tile[][] grid);

    /**
     * Tests the board for validity. A board is valid as long as there are no broken rules. Blank tiles are allowed.
     */
    public abstract boolean isValid(Board board);

    /**
     * Tests the board for completeness. A board is complete as long as it is valid and there are no blank tiles.
     */
    public abstract boolean isComplete(Board board);

    public abstract int xSize();
    public abstract int ySize();

    /**
     * Creates a set of values that can be used in this rule set.
     */
    protected abstract List<Value> createValues();

    protected Value createValue(int value, String description) {
        return new Value(value, description);
    }

    public static class Value {
        private final Integer value;
        private final String description;

        private Value(Integer value, String description) {
            this.value = value;
            this.description = description;
        }

        public Integer getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }
    }
}
