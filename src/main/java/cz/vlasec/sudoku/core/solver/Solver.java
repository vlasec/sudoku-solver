package cz.vlasec.sudoku.core.solver;

import cz.vlasec.sudoku.core.SudokuException;
import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules;

import java.util.List;

public interface Solver {
    /**
     * Solves the problem. A well behaved sudoku problem should return a single solution.
     * However, there can be cases when there are multiple solutions.
     * @throws SudokuException if the problem has no solution.
     */
    List<Board> solve(Board problem, Rules rules);
}
