package cz.vlasec.sudoku.solvers.reaction;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;
import cz.vlasec.sudoku.core.solver.Reaction;
import cz.vlasec.sudoku.core.solver.SolverCallback;

import java.util.List;
import java.util.Set;

import static cz.vlasec.sudoku.solvers.utils.CandidateHelper.hasCandidate;

/**
 * A bit more complex reaction.
 * If the candidate that has been ruled out is only a candidate in an intersection with some other tile set,
 * then the other tile set can also only contain the candidate in the intersection (and not anywhere else).
 */
public class SetIntersectionReaction implements Reaction {
    @Override
    public void onCandidateRuledOut(SolverCallback callback, Board board, Tile affectedTile, Value value) {
        List<TileSet> affectedSets = board.setsAt(affectedTile);
        for (TileSet affectedSet : affectedSets) {
            for (TileSet otherSet : board.intersectingSetsTo(affectedSet)) {
                // We are interested in other sets intersecting with the one we ruled out a candidate in.
                if (!affectedSets.contains(otherSet)) {
                    checkIntersectionAndRemainder(callback, affectedSet, otherSet, value);
                }
            }
        }
    }

    /**
     * Only unidirectional check, compared to the counterpart in sweep.
     *
     * Considering it is a reaction, it is only meaningful to expect a change in setA.
     */
    private void checkIntersectionAndRemainder(SolverCallback callback, TileSet affectedSet, TileSet otherSet, Value value) {
        Set<Tile> intersection = affectedSet.intersection(otherSet);
        if (hasCandidate(intersection, value)) {
            Set<Tile> remainder = affectedSet.except(intersection);
            if (!hasCandidate(remainder, value)) {
                callback.ruleOutCandidate(otherSet.except(intersection), value);
            }
        }
    }
}
