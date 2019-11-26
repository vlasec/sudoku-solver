package cz.vlasec.sudoku.core.solver;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;

/**
 * A reaction to something that changed on the board.
 * The implementation is empty by default, only implement what is needed.
 */
public interface Reaction {

    /**
     * Reacts to a value being set to a tile.
     *
     * @param callback Reactions being made by this solver component must be reported this way.
     * @param board    The affected board.
     * @param tile     The affected tile.
     */
    default void onValueSet(SolverCallback callback, Board board, Tile tile) {
    }

    /**
     * Reacts to a candidate being ruled out on the tile.
     *
     * @param callback Reactions being made by this solver component must be reported this way.
     * @param board    The affected board.
     * @param tile     The affected tile.
     * @param value    The value that was ruled out.
     */
    default void onCandidateRuledOut(SolverCallback callback, Board board, Tile tile, Value value) {
    }
}
