package cz.vlasec.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BruteForceSolver {
    private static final Set<Character> BLANKS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList('.', ' ', '_')));
    private final char[] values;
    private final int cellXSize;
    private final int cellYSize;
    private final int size;
    private final int tiles;

    public static void main(String[] args) {
        System.out.println("Bruteforcing one of the hardest 9x9 puzzles for warmup ...");
        long start = System.currentTimeMillis();
//        outputArray(SamplePuzzles.WORLDS_HARDEST);
        String[] result = new BruteForceSolver(3, 3, "123456789").solve(SamplePuzzles.WORLDS_HARDEST);
        System.out.println("And done (in " + (System.currentTimeMillis() - start) / 1000.0 + " s): ");
        Arrays.stream(result).forEach(System.out::println);
    }

    public BruteForceSolver(int cellXSize, int cellYSize, String values) {
        this.values = values.toCharArray();
        this.cellXSize = cellXSize;
        this.cellYSize = cellYSize;
        this.size = cellXSize * cellYSize;
        tiles = size * size;
    }

    public String[] solve(String[] rows) {
        char[][] grid = Arrays.stream(rows)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        solve(grid, 0);
        return Arrays.stream(grid)
                .map(String::new)
                .toArray(String[]::new);
    }

    private boolean solve(char[][] grid, int searchFrom) {
        for (int i = searchFrom; i < tiles; i++) {
            int x = i / size, y = i % size;
            if (BLANKS.contains(grid[x][y])) {
                for (char c : values) {
                    if (isValidMove(grid, c, x, y)) {
                        grid[x][y] = c;
                        if (solve(grid, i+1)) {
                            return true;
                        }
                        grid[x][y] = '.';
                    }
                }
                return false;
            }
        }
        return true;
    }

    private boolean isValidMove(char[][] grid, char c, int x, int y) {
        int xc = x - x % cellXSize;
        int yc = y - y % cellYSize;
        for (int i = 0; i < size; i++) {
            if (grid[x][i] == c)
                return false;
            if (grid[i][y] == c)
                return false;
            if (grid[xc + i / cellYSize][yc + i % cellYSize] == c)
                return false;
        }
        return true;
    }
}
