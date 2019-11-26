package cz.vlasec.sudoku.core.solver;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Tile;

public interface Reaction {
    void reactTo(Board b, Tile tile, SolverCallback callback);
}
