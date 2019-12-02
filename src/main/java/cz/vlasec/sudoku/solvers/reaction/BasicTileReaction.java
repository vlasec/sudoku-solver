package cz.vlasec.sudoku.solvers.reaction;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;
        import cz.vlasec.sudoku.core.solver.Reaction;
        import cz.vlasec.sudoku.core.solver.SolverCallback;

import java.util.Set;

/**
 * The very basics: Rule out the value being set from all candidates in its sets. Set the last remaining candidate.
 */
public class BasicTileReaction implements Reaction {

    /**
     * A candidate is ruled out in all sets the tile lies in.
     */
    @Override
    public void onValueSet(SolverCallback callback, Board board, Tile affectedTile, Value value) {
        for (Set<Tile> tiles : board.setsAt(affectedTile)) {
            callback.ruleOutCandidate(tiles, affectedTile, value);
        }
    }

    /**
     * If there is last candidate remaining, then it is the value to be set.
     */
    @Override
    public void onCandidateRuledOut(SolverCallback callback, Board board, Tile affectedTile, Value value) {
        if (affectedTile.candidates().size() == 1) {
            callback.setValue(affectedTile, affectedTile.candidates().iterator().next());
        }
    }
}
