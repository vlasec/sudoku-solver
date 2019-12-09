package cz.vlasec.sudoku.rules;

import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;
import cz.vlasec.sudoku.core.board.TileSet.TileSetType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cz.vlasec.sudoku.core.board.TileSet.createTileSet;

public class XSudokuRules extends ClassicRules {
    public XSudokuRules(int cellXSize, int cellYSize) {
        super(cellXSize, cellYSize);
    }

    public XSudokuRules(int cellXSize, int cellYSize, List<String> values) {
        super(cellXSize, cellYSize, values);
    }

    @Override
    public List<TileSet.TileSetMutator> sets(Tile[][] grid) {
        List<TileSet.TileSetMutator> result = new ArrayList<>(size() * 3 + 2);
        prepareRowsAndCols(result, grid, XSudokuSetTypes.ROW, XSudokuSetTypes.COLUMN);
        prepareCells(result, grid, XSudokuSetTypes.CELL);
        Set<Tile> diagA = new HashSet<>();
        Set<Tile> diagB = new HashSet<>();
        for (int i = 0; i < size(); i++) {
            diagA.add(grid[i][i]);
            diagB.add(grid[size() - i - 1][i]);
        }
        result.add(createTileSet(diagA, XSudokuSetTypes.DIAGONAL));
        result.add(createTileSet(diagB, XSudokuSetTypes.DIAGONAL));
        return result;
    }

    private enum XSudokuSetTypes implements TileSetType {
        ROW {
            public boolean canIntersectWith(TileSetType other) {
                return CELL == other;
            }
            public boolean isPerpendicularTo(TileSetType other) {
                return COLUMN == other || DIAGONAL == other;
            }
        },
        COLUMN {
            public boolean canIntersectWith(TileSetType other) {
                return CELL == other;
            }
            public boolean isPerpendicularTo(TileSetType other) {
                return ROW == other || DIAGONAL == other;
            }
        },
        DIAGONAL {
            public boolean canIntersectWith(TileSetType other) {
                return CELL == other;
            }
            public boolean isPerpendicularTo(TileSetType other) {
                return false;
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
