package cz.vlasec.sudoku.core.board;

import cz.vlasec.sudoku.core.utils.BoardUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    private final Tile[][] grid;
    private final List<TileSet> tileSets;
    private final Map<Tile, List<TileSet>> tileSetsForTile;
    private final Map<TileSet, List<TileSet>> intersectingTileSets;

    private Board(Tile[][] grid, List<TileSet> tileSets, Map<Tile, List<TileSet>> tileSetsForTile,
                  Map<TileSet, List<TileSet>> intersectingTileSets) {
        this.grid = grid;
        this.tileSets = tileSets;
        this.tileSetsForTile = tileSetsForTile;
        this.intersectingTileSets = intersectingTileSets;
    }

    public static Board create(Tile[][] grid, List<TileSet> tileSets) {
        tileSets = Collections.unmodifiableList(tileSets);
        return new Board(grid, tileSets, BoardUtil.computeTileSetsForTiles(tileSets), BoardUtil.computeIntersections(tileSets));
    }

    /**
     * The grid - an array of tiles. Caution: The array may contain nulls for some special types of Sudoku.
     */
    public Tile tileAt(int x, int y) {
        return grid[x][y];
    }

    /**
     * Returns sets that the tile is a part of.
     */
    public List<TileSet> setsAt(Tile tile) {
        return tileSetsForTile.getOrDefault(tile, Collections.emptyList());
    }

    public List<TileSet> intersectingSetsTo(TileSet tileSet) {
        return intersectingTileSets.getOrDefault(tileSet, Collections.emptyList());
    }

    public int xSize() {
        return grid.length;
    }

    public int ySize() {
        return grid[0].length;
    }

    /**
     * Sets of tiles where no duplicates are allowed by the rules.
     */
    public List<TileSet> tileSets() {
        return tileSets;
    }
}
