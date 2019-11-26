package cz.vlasec.sudoku.core.solver;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules;

public interface Solver {
    /**
     * Solves the problem. The given board is not mutable,
     */
    Board solve(Board problem, Rules rules);
}
