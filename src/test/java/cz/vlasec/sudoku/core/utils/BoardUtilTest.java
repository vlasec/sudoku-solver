package cz.vlasec.sudoku.core.utils;

import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static cz.vlasec.sudoku.core.utils.TestTileSetTypes.CELL;
import static cz.vlasec.sudoku.core.utils.TestTileSetTypes.COLUMN;
import static cz.vlasec.sudoku.core.utils.TestTileSetTypes.ROW;

class BoardUtilTest {
    private Tile[] createTiles(int count) {
        return Stream.iterate(1, i -> i + 1)
                .map(x -> Tile.createTile(Collections.emptySet(), "" + x).getTile())
                .limit(count)
                .toArray(Tile[]::new);
    }

    private List<TileSet> testData1() {
        Tile[] t = createTiles(16);
        TileSet tsCell = tileSet(CELL, t[0], t[1], t[4], t[5]);
        TileSet tsRow = tileSet(ROW, t[0], t[1], t[2], t[3]);
        TileSet tsCol = tileSet(COLUMN, t[0], t[4], t[8], t[12]);
        return Arrays.asList(tsCell, tsRow, tsCol);
    }

    private TileSet tileSet(TestTileSetTypes type, Tile first, Tile... tiles) {
        return TileSet.createTileSet(set(first, tiles), type).getSet();
    }

    @Test
    void computeTileSetsForTile() {
        List<TileSet> sets = testData1();
        Map<Tile, List<TileSet>> map = BoardUtil.computeTileSetsForTiles(sets);
        Assertions.assertEquals(8, map.size());
        Assertions.assertEquals(12, map.values().stream().mapToLong(Collection::size).sum());
    }

    @Test
    void computeIntersections() {
        List<TileSet> sets = testData1();
        Map<TileSet, List<TileSet>> map = BoardUtil.computeIntersections(sets);
        Assertions.assertEquals(3, map.size());
        Assertions.assertEquals(4, map.values().stream().mapToLong(Collection::size).sum());
    }

    @SafeVarargs
    private final <T> Set<T> set(T first, T... rest) {
        Set<T> set = new HashSet<>(Arrays.asList(rest));
        set.add(first);
        return set;
    }
}