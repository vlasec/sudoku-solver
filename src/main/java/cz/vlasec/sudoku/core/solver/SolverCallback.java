package cz.vlasec.sudoku.core.solver;

import cz.vlasec.sudoku.core.SudokuStateException;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;

import java.util.Collection;
import java.util.Set;

/**
 * <p>This callback allows reactions and sweepers to send information back to the solver.</p>
 * <p>
 *     The implementation on solver side is supposed to only make the change instantly and queue the reactions.
 *     It should also fail fast when there is some illegal state on the board, throwing {@link SudokuStateException}.
 * </p>
 */
public interface SolverCallback {
    /**
     * The solver sets a value to the tile and reacts accordingly.<br/>
     * Setting a value also rules out all remaining candidates if there were any left.<br/>
     * If value is already set, setting an equal value must have no effect (and no reactions).
     *
     * @param tile  Tile where the value is to be set
     * @param value The value to be set
     * @throws SudokuStateException If value is already set and the newly set value differs.<br/>
     *                              This exception should only be caught by a Solver.
     */
    void setValue(Tile tile, Value value);

    /**
     * The solver rules out a candidate for the tile and reacts accordingly.<br/>
     * If the candidate was no longer present, there must be no effect (and no reaction).
     *
     * @param tile        Tile where the value is to be ruled out
     * @param exCandidate The candidate to be ruled out
     * @throws SudokuStateException If tile has its value set and the ruled out candidate was the value.<br/>
     *                              This exception should only be caught by a Solver.
     */
    void ruleOutCandidate(Tile tile, Value exCandidate);

    /**
     * The solver rules out a candidate for the tile and reacts accordingly.<br/>
     * If the candidate was no longer present, there must be no effect (and no reaction).
     *
     * @param tile        Tile where the value is to be ruled out
     * @param exCandidate The candidate to be ruled out
     * @throws SudokuStateException If tile has its value set and the ruled out candidate was the value.<br/>
     *                              This exception should only be caught by a Solver.
     */
    default void ruleOutCandidate(Tile tile, Collection<Value> exCandidate) {
        for (Value value : exCandidate) {
            ruleOutCandidate(tile, value);
        }
    }

    /**
     * A bulk variant of {@link #ruleOutCandidate(Tile, Value)}.
     * Removes candidate from all tiles in collection. There may be a candidate given that has exception from that.
     *
     * @param tiles       Tiles where the candidate is to be ruled out
     * @param exceptFor   This tile, if not null, will not be ruled out
     * @param exCandidate The candidate to be ruled out
     */
    default void ruleOutCandidate(Collection<Tile> tiles, Tile exceptFor, Value exCandidate) {
        for (Tile tile : tiles) {
            if (tile != exceptFor) {
                ruleOutCandidate(tile, exCandidate);
            }
        }
    }

    default void ruleOutCandidate(Collection<Tile> tiles, Set<Tile> exceptFor, Value exCandidate) {
        for (Tile tile : tiles) {
            if (!exceptFor.contains(tile)) {
                ruleOutCandidate(tile, exCandidate);
            }
        }
    }

    /**
     * A convenience variant of A bulk variant of {@link #ruleOutCandidate(Collection, Tile, Value)}
     * Removes candidate from all tiles in collection, with no exception.
     *
     * @param tiles       Tiles where the candidate is to be ruled out
     * @param exCandidate The candidate to be ruled out
     */
    default void ruleOutCandidate(Collection<Tile> tiles, Value exCandidate) {
        ruleOutCandidate(tiles, (Tile) null, exCandidate);
    }
}
