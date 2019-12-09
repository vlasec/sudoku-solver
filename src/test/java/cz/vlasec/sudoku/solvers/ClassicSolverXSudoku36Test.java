package cz.vlasec.sudoku.solvers;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Puzzle;
import cz.vlasec.sudoku.core.board.Rules;
import cz.vlasec.sudoku.core.solver.Solver;
import cz.vlasec.sudoku.io.Formatter;
import cz.vlasec.sudoku.rules.XSudokuRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static cz.vlasec.sudoku.io.Formatter.Format.*;
import static cz.vlasec.sudoku.io.Formatter.Conversions.*;

public class ClassicSolverXSudoku36Test extends SolverTestBase{

    @ParameterizedTest
    @CsvSource({
            "xsudoku-36-easier-puzzle.txt,xsudoku-36-easier-solution.txt",
//            "xsudoku-36-medium-puzzle.txt,xsudoku-36-medium-solution.txt",
    })
    public void test(String puzzleFile, String solutionFile) {
        Rules rules = new XSudokuRules(6,6);
        Solver solver = new ClassicSolver();
        Puzzle puzzle = Puzzle.createProblem(rules);
        Formatter.importFrom(resourceSupplier(puzzleFile), puzzle, NUMERIC, numericInput(rules));

        List<Board> results = solver.solve(puzzle.getBoard(), puzzle.getRules());
        prettyPrintPuzzleAndResults(puzzle, results);
        Assertions.assertEquals(1, results.size());

        Puzzle solution = Puzzle.createProblem(rules);
        Formatter.importFrom(resourceSupplier(solutionFile), solution, NUMERIC, numericInput(rules));

        assertBoardsEquals(solution.getBoard(), results.get(0));
    }

}
