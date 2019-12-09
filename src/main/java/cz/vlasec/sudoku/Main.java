package cz.vlasec.sudoku;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Puzzle;
import cz.vlasec.sudoku.core.board.Rules;
import cz.vlasec.sudoku.core.solver.Solver;
import cz.vlasec.sudoku.io.Formatter;
import cz.vlasec.sudoku.rules.XSudokuRules;
import cz.vlasec.sudoku.solvers.ClassicSolver;

import java.io.InputStream;
import java.util.*;
import java.util.function.Supplier;

import static cz.vlasec.sudoku.io.Formatter.Format.*;
import static cz.vlasec.sudoku.io.Formatter.Conversions.*;

public class Main {

    public static final List<String> ALPHADOKU = Collections.unmodifiableList(Arrays.asList(
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y"
    ));

    public static void main(String[] args) {
        System.out.println("Hello, world! Let's solve some sudoku ...");
//        Rules rules = new ClassicRules(3, 3);
//        Rules rules = new ClassicRules(4, 4);
        Rules rules = new XSudokuRules(6,6);
//        Rules rules = new ClassicRules(5, 5, ALPHADOKU);
//        Rules rules = new ClassicRules(7, 7);
        Solver solver = new ClassicSolver();
        Puzzle puzzle = Puzzle.createProblem(rules);
        Formatter.importFrom(resourceSupplier("easier36x36.txt"), puzzle, NUMERIC, numericInput(rules));
//        applyFileToGrid(puzzle, "hard36x36.txt");
//        applyArrayToGrid(puzzle, SamplePuzzles.WORLDS_HARDEST);
//        applyArrayToGrid(puzzle, SamplePuzzles.EASY16);
//        applyArrayToGrid(puzzle, SamplePuzzles.HARD25);
//        applyArrayToGrid(puzzle, SamplePuzzles.HARD49_STEP3);
//        applyArrayToGrid(puzzle, SamplePuzzles.HARD49_STEP3);

        System.out.println("Today we are solving this puzzle:");
        System.out.println(Formatter.exportToString(puzzle.getBoard(), PRETTY_PRINT, simpleOutput(" ")));
        List<Board> results = solver.solve(puzzle.getBoard(), puzzle.getRules());
        System.out.println("The solution(s) were following:");
        results
                .stream()
                .map(res -> Formatter.exportToString(res, PRETTY_PRINT, simpleOutput(" ")))
                .forEach(System.out::println);
        System.out.println("Solution count was: " + results.size());
    }

    private static Supplier<InputStream> resourceSupplier(String resourceName) {
        return () -> Main.class.getResourceAsStream(resourceName);
    }
}
