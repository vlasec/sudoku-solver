package cz.vlasec.sudoku.solvers;

import cz.vlasec.sudoku.Main;
import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Puzzle;
import cz.vlasec.sudoku.io.Formatter;
import org.junit.jupiter.api.Assertions;

import java.io.InputStream;
import java.util.List;
import java.util.function.Supplier;

import static cz.vlasec.sudoku.io.Formatter.Format.*;
import static cz.vlasec.sudoku.io.Formatter.Conversions.*;

public class SolverTestBase {
    protected Supplier<InputStream> resourceSupplier(String resourceName) {
        return () -> SolverTestBase.class.getResourceAsStream(resourceName);
    }

    protected void prettyPrintPuzzleAndResults(Puzzle puzzle, List<Board> results) {
        System.out.println("Puzzle:");
        System.out.println(toPrettyPrint(puzzle.getBoard()));
        System.out.println("Solution(s):");
        results.stream()
                .map(this::toPrettyPrint)
                .forEach(System.out::println);
    }

    protected String toPrettyPrint(Board board) {
        return Formatter.exportToString(board, PRETTY_PRINT, simpleOutput(" "));
    }

    protected void assertBoardsEquals(Board expected, Board actual) {
        // Comparing pretty prints can be nice for spotting the difference.
        Assertions.assertEquals(toPrettyPrint(expected), toPrettyPrint(actual));
    }
}
