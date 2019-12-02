package cz.vlasec.sudoku.core;

/**
 * This exception says that the state of said sudoku tile is in contradiction with rules.
 */
public class SudokuException extends RuntimeException {
    public SudokuException(String message) {
        super(message);
    }
}
