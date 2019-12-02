package cz.vlasec.sudoku.core.solver;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules.Value;

import java.util.List;

/**
 * A sweep through the whole board. Used when reactions are out of clues.
 */
public interface Sweep {
    /**
     * Sweeps the whole board and tries to find something.
     * @param callback Changes being made by this solver component must be reported this way.
     * @param board    The board to sweep.
     * @param allValues A list of all (or all potentially applicable) values.
     */
    void sweep(SolverCallback callback, Board board, List<Value> allValues);

    default void sweep(SweepRequest request) {
        sweep(request.getCallback(), request.getBoard(), request.getAllValues());
    }
}
