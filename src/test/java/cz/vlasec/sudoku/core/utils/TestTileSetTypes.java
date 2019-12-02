package cz.vlasec.sudoku.core.utils;

import cz.vlasec.sudoku.core.board.TileSet;

public enum TestTileSetTypes implements TileSet.TileSetType {
    ROW {
        public boolean canIntersectWith(TileSet.TileSetType otherType) {
            return CELL == otherType;
        }
        public boolean isPerpendicularTo(TileSet.TileSetType otherType) {
            return COLUMN == otherType;
        }
    },
    COLUMN {
        public boolean canIntersectWith(TileSet.TileSetType otherType) {
            return CELL == otherType;
        }
        public boolean isPerpendicularTo(TileSet.TileSetType otherType) {
            return ROW == otherType;
        }
    },
    CELL {
        public boolean canIntersectWith(TileSet.TileSetType otherType) {
            return CELL != otherType;
        }
        public boolean isPerpendicularTo(TileSet.TileSetType otherType) {
            return false;
        }
    },
    ;

    @Override
    public String description() {
        return name();
    }
}
