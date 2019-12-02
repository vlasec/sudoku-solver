package cz.vlasec.sudoku.solvers.reaction;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;
import cz.vlasec.sudoku.core.solver.Reaction;
import cz.vlasec.sudoku.core.solver.SolverCallback;
import cz.vlasec.sudoku.solvers.utils.CandidateHelper;

import java.util.Set;

public class LastInSetReaction implements Reaction {
    @Override
    public void onCandidateRuledOut(SolverCallback callback, Board board, Tile affectedTile, Value value) {
        for (TileSet set : board.setsAt(affectedTile)) {
            Set<Tile> candidates = set.candidatesForValue(value);
            if (candidates.size() == 1) {
                callback.setValue(candidates.iterator().next(), value);
            }
        }
    }
}
