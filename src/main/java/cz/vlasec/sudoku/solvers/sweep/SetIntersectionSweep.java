package cz.vlasec.sudoku.solvers.sweep;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;
import cz.vlasec.sudoku.core.solver.SolverCallback;
import cz.vlasec.sudoku.core.solver.Sweep;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cz.vlasec.sudoku.solvers.utils.CandidateHelper.hasCandidate;

public class SetIntersectionSweep implements Sweep {

    @Override
    public void sweep(SolverCallback callback, Board board, List<Value> allValues) {
        // The intersection check is bidirectional, no need to return to the same pair from the opposite side.
        Set<TileSet> checkedSets = new HashSet<>();
        for (TileSet setA : board.tileSets()) {
            if (checkedSets.add(setA)) {
                for (TileSet setB : board.intersectingSetsTo(setA)) {
                    checkIntersection(callback, setA, setB, allValues);
                }
            }
        }
    }

    public static void checkIntersection(SolverCallback callback, TileSet a, TileSet b, List<Value> values) {
        Set<Tile> intersection = a.intersection(b);
        Set<Tile> remainderOfA = a.except(intersection);
        Set<Tile> remainderOfB = b.except(intersection);

        for (Value value : values) {
            if (hasCandidate(intersection, value)) {
                boolean remainderOfAHasCandidate = hasCandidate(remainderOfA, value);
                boolean remainderOfBHasCandidate = hasCandidate(remainderOfB, value);
                if (remainderOfAHasCandidate && !remainderOfBHasCandidate ) {
                    callback.ruleOutCandidate(remainderOfA, value);
                }
                if (remainderOfBHasCandidate && !remainderOfAHasCandidate) {
                    callback.ruleOutCandidate(remainderOfB, value);
                }
            }
        }
    }
}
