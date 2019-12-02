package cz.vlasec.sudoku.solvers.utils;

import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;

import java.util.Optional;
import java.util.Set;

public class CandidateHelper {
    private CandidateHelper() {
    }

    /**
     * Computes candidate's presence in given set of tiles.
     * @param tiles     the tiles to search in
     * @param candidate the candidate to search for
     * @return true if the candidate is found, false otherwise
     */
    public static int candidatePresence(Set<Tile> tiles, Value candidate) {
        int count = 0;
        for (Tile tile : tiles) {
            if (tile.candidates().contains(candidate)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Computes candidate's presence in given set of tiles.
     * @param tiles     the tiles to search in
     * @param candidate the candidate to search for
     * @return true if the candidate is found, false otherwise
     */
    public static boolean hasCandidate(Set<Tile> tiles, Value candidate) {
        for (Tile tile : tiles) {
            if (tile.candidates().contains(candidate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the candidate's unique location as a tile, provided that it is contained only once in the set.
     * @param tiles     the tiles to search in
     * @param candidate the candidate to search for
     * @return candidate's unique location, empty if candidate is found in multiple tiles or not present
     */
    public static Optional<Tile> candidateUniqueLocation(Set<Tile> tiles, Value candidate) {
        Tile foundAt = null;
        for (Tile tile : tiles) {
            if (tile.candidates().contains(candidate)) {
                if (foundAt != null) {
                    return Optional.empty();
                }
                foundAt = tile;
            }
        }
        return Optional.ofNullable(foundAt);
    }
}
