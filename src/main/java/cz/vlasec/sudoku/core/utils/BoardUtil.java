package cz.vlasec.sudoku.core.utils;

import cz.vlasec.sudoku.core.board.Tile;
import cz.vlasec.sudoku.core.board.TileSet;

import java.util.*;
import java.util.stream.Collectors;

public class BoardUtil {
    public static Map<Tile, List<TileSet>> computeTileSetsForTiles(List<TileSet> tileSets) {
        Map<Tile, List<TileSet>> result = new HashMap<>();
        for (TileSet tileSet : tileSets) {
            for (Tile tile : tileSet) {
                result.computeIfAbsent(tile, x -> new ArrayList<>())
                        .add(tileSet);
            }
        }
        return Collections.unmodifiableMap(result.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Collections.unmodifiableList(e.getValue())
                )));
    }

    public static Map<TileSet, List<TileSet>> computeIntersections(List<TileSet> tileSets) {
        Map<TileSet, List<TileSet>> result = new HashMap<>();
        for (TileSet tileSet : tileSets) {
            for (TileSet otherSet : tileSets) {
                if (tileSet != otherSet && tileSet.type().canIntersectWith(otherSet.type()) && intersectionSize(tileSet, otherSet) > 1) {
                    result.computeIfAbsent(tileSet, $ -> new ArrayList<>())
                        .add(otherSet);
                }
            }
        }
        return Collections.unmodifiableMap(result.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Collections.unmodifiableList(e.getValue())
                )));
    }

    private static int intersectionSize(TileSet setA, TileSet setB) {
        int result = 0;
        for (Tile tile : setA) {
            if (setB.contains(tile)) {
                result++;
            }
        }
        return result;
    }
}
