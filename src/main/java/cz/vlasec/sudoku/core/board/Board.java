package cz.vlasec.sudoku.core.board;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private final Tile[][] grid;
    private final Collection<Set<Tile>> tileSets;
    private final Map<Tile, Collection<Set<Tile>>> tileSetsForTile;

    private Board(Tile[][] grid, Collection<Set<Tile>> tileSets, Map<Tile, Collection<Set<Tile>>> tileSetsForTile) {
        this.grid = grid;
        this.tileSets = tileSets;
        this.tileSetsForTile = tileSetsForTile;
    }

    public static Board create(Tile[][] grid, Collection<Set<Tile>> tileSets) {
        tileSets = tileSets.stream()
                .map(HashSet::new)
                .map(Collections::unmodifiableSet)
                .collect(Collectors.toList());
        return new Board(grid, tileSets, computeTileSetsForTile(tileSets));
    }

    private static Map<Tile, Collection<Set<Tile>>> computeTileSetsForTile(Collection<Set<Tile>> tileSets) {
        Map<Tile, Collection<Set<Tile>>> result = new HashMap<>();
        for (Set<Tile> tileSet : tileSets) {
            for (Tile tile : tileSet) {
                result.computeIfAbsent(tile, x -> new ArrayList<>())
                        .add(tileSet);
            }
        }
        return result;
    }

    /**
     * The grid - an array of tiles. Caution: The array may contain nulls for some special types of Sudoku.
     */
    public Tile tileAt(int x, int y) {
        return grid[x][y];
    }

    public Collection<Set<Tile>> setsAt(Tile tile) {
        return tileSetsForTile.getOrDefault(tile, Collections.emptyList());
    }

    /**
     * Sets of tiles where no duplicates are allowed by the rules.
     */
    public Collection<Set<Tile>> tileSets() {
        return tileSets;
    }
}
