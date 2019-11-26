package cz.vlasec.sudoku.core.solver;

import cz.vlasec.sudoku.core.board.Board;

public interface Sweep {
    void sweep(Board b, SolverCallback callback);
}
