package cz.vlasec.sudoku.core.solver;

import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;

/**
 * This callback allows reactions and sweepers to send information back to the solver.
 * The implementation on solver side is supposed to only call very simple reactions instantly, the rest should be queued.
 */
public interface SolverCallback {
    /** The solver sets a value to the tile and reacts accordingly */
    void setValue(Tile tile, Value value);
    /** The solver rules out a candidate for the tile and reacts accordingly */
    void ruleOutCandidate(Tile tile, Value value);
}
