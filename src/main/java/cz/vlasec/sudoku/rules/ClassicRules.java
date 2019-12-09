package cz.vlasec.sudoku.rules;

import cz.vlasec.sudoku.core.board.Rules;
import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;
import cz.vlasec.sudoku.core.board.TileSet.TileSetMutator;
import cz.vlasec.sudoku.core.board.TileSet.TileSetType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cz.vlasec.sudoku.core.board.TileSet.createTileSet;

public class ClassicRules extends Rules {
    private final int cellXSize;
    private final int cellYSize;
    private final int size;
    protected List<Value> values;

    public ClassicRules(int cellXSize, int cellYSize) {
        this.cellXSize = cellXSize;
        this.cellYSize = cellYSize;
        this.size = cellXSize * cellYSize;
        this.values = Collections.unmodifiableList(createValues());
    }

    public ClassicRules(int cellXSize, int cellYSize, List<String> values) {
        this.cellXSize = cellXSize;
        this.cellYSize = cellYSize;
        this.size = cellXSize * cellYSize;
        this.values = Collections.unmodifiableList(createValues(values));
    }

    @Override
    public List<TileSetMutator> sets(Tile[][] grid) {
        List<TileSetMutator> result = new ArrayList<>(size * 3);
        prepareRowsAndCols(result, grid, ClassicSetTypes.ROW, ClassicSetTypes.COLUMN);
        prepareCells(result, grid, ClassicSetTypes.CELL);

        return result;
    }

    protected void prepareRowsAndCols(List<TileSetMutator> output, Tile[][] grid, TileSetType rowType, TileSetType colType) {
        for (int i = 0; i < size; i++) {
            Set<Tile> row = new HashSet<>();
            Set<Tile> col = new HashSet<>();
            for (int j = 0; j < size; j++) {
                row.add(grid[i][j]);
                col.add(grid[j][i]);
            }
            output.add(createTileSet(row, rowType));
            output.add(createTileSet(col, colType));
        }
    }

    protected void prepareCells(List<TileSetMutator> output, Tile[][] grid, TileSetType cellType) {
        for (int cellX = 0; cellX < cellYSize; cellX++) {
            for (int cellY = 0; cellY < cellXSize; cellY++) {
                Set<Tile> cell = new HashSet<>();
                for (int x = 0; x < cellXSize; x++) {
                    for (int y = 0; y < cellYSize; y++) {
                        cell.add(grid[cellX * cellXSize + x][cellY * cellYSize + y]);
                    }
                }
                output.add(createTileSet(cell, cellType));
            }
        }
    }

    public int size() {
        return size;
    }

    public List<Value> values() {
        return values;
    }

    @Override
    public int xSize() {
        return size;
    }

    @Override
    public int ySize() {
        return size;
    }

    public static String descriptValue(int value) {
        if (value < 10) {
            return "" + value;
        }
        if (value < 36) {
            char x = 'A';
            x+= (value - 10);
            return "" + x;
        }
        if (value < 62) {
            char x = 'a';
            x+= (value - 36);
            return "" + x;
        }
        if (value == 62)
            return "+";
        if (value == 63)
            return "/";
        return "" + value;
    }

    protected List<Value> createValues() {
        return Stream.iterate(1, i -> i + 1)
                .limit(size)
                .map(ClassicRules::descriptValue)
                .map(this::createValue)
                .collect(Collectors.toList());
    }

    protected List<Value> createValues(List<String> values) {
        return values.stream()
                .map(this::createValue)
                .collect(Collectors.toList());
    }

    private enum ClassicSetTypes implements TileSetType {
        ROW {
            public boolean canIntersectWith(TileSetType other) {
                return CELL == other;
            }
            public boolean isPerpendicularTo(TileSetType other) {
                return COLUMN == other;
            }
        },
        COLUMN {
            public boolean canIntersectWith(TileSetType other) {
                return CELL == other;
            }
            public boolean isPerpendicularTo(TileSetType other) {
                return ROW == other;
            }
        },
        CELL {
            public boolean canIntersectWith(TileSetType other) {
                return CELL != other;
            }
            public boolean isPerpendicularTo(TileSetType other) {
                return false;
            }
        },
        ;

        @Override
        public String description() {
            return name();
        }
    }
}
