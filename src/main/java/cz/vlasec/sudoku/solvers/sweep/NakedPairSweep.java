package cz.vlasec.sudoku.solvers.sweep;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;
import cz.vlasec.sudoku.core.solver.SolverCallback;
import cz.vlasec.sudoku.core.solver.Sweep;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class NakedPairSweep implements Sweep {
    @Override
    public void sweep(SolverCallback callback, Board board, List<Value> allValues) {
        for (TileSet set : board.tileSets()) {
            for (Tile tile : set) {
                Tile paired = findMatchingPair(tile, set);
                if (paired != null) {
                    ruleOut(callback, set, tile, paired);
                }
            }
        }
    }

    private Tile findMatchingPair(Tile tile, Set<Tile> otherTiles) {
        if (tile.candidates().size() > 2) {
            return null;
        }
        for (Tile otherTile : otherTiles) {
            if (otherTile != tile && Objects.equals(otherTile.candidates(), tile.candidates())) {
                return otherTile;
            }
        }
        return null;
    }

    private void ruleOut(SolverCallback callback, TileSet set, Tile pairA, Tile pairB) {
        for (Tile tile : set) {
            if (tile != pairA && tile != pairB) {
                callback.ruleOutCandidate(tile, pairA.candidates());
            }
        }
    }
}
