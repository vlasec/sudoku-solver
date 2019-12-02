package cz.vlasec.sudoku.core;

import cz.vlasec.sudoku.core.board.Tile;

/**
 * This exception says that the state of said sudoku tile is in contradiction with rules.
 */
public class SudokuStateException extends SudokuException {
    private Tile tile;

    public SudokuStateException(String message, Tile tile) {
        super(message);
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}
