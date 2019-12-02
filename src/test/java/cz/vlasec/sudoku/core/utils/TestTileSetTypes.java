package cz.vlasec.sudoku.core.utils;

import cz.vlasec.sudoku.core.board.TileSet;

public enum TestTileSetTypes implements TileSet.TileSetType {
    ROW {
        public boolean canIntersectWith(TileSet.TileSetType other) {
            return CELL == other;
        }
        public boolean isPerpendicularTo(TileSet.TileSetType other) {
            return COLUMN == other;
        }
    },
    COLUMN {
        public boolean canIntersectWith(TileSet.TileSetType other) {
            return CELL == other;
        }
        public boolean isPerpendicularTo(TileSet.TileSetType other) {
            return ROW == other;
        }
    },
    CELL {
        public boolean canIntersectWith(TileSet.TileSetType other) {
            return CELL != other;
        }
        public boolean isPerpendicularTo(TileSet.TileSetType other) {
            return false;
        }
    },
    ;

    @Override
    public String description() {
        return name();
    }
}
