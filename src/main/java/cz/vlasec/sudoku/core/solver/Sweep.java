package cz.vlasec.sudoku.core.solver;

import cz.vlasec.sudoku.core.board.Board;

/**
 * A sweep through the whole board. Used when reactions are out of clues.
 */
public interface Sweep {
    /**
     * Sweeps the whole board and tries to find something.
     * @param callback Changes being made by this solver component must be reported this way.
     * @param board    The board to sweep.
     */
    void sweep(SolverCallback callback, Board board);
}
