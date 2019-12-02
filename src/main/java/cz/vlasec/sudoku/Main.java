package cz.vlasec.sudoku;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Problem;
import cz.vlasec.sudoku.core.board.Rules;
import cz.vlasec.sudoku.core.solver.Solver;
import cz.vlasec.sudoku.rules.ClassicRules;
import cz.vlasec.sudoku.solvers.ClassicSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
//        Rules rules = new ClassicRules(5, 5, ALPHADOKU);
        Rules rules = new ClassicRules(7, 7);
        Solver solver = new ClassicSolver();
        Problem problem = Problem.createProblem(rules);
//        applyArrayToGrid(problem, SamplePuzzles.HARD25, 1.0d);
        applyArrayToGrid(problem, SamplePuzzles.HARD49_STEP3, 1.98d);

        System.out.println("Today we are solving this puzzle:");
        System.out.println(problem.getBoard().toUglyString());
        System.out.println(problem.getBoard().toString());
        List<Board> results = solver.solve(problem.getBoard(), problem.getRules());
        System.out.println("The solution(s) were following:");
        results.forEach(System.out::println);
        System.out.println("Solution count was: " + results.size());
    }

    private static void applyArrayToGrid(Problem problem, String[] rows, double threshold) {
        Random rnd = new Random();
        int total = problem.getRules().xSize() * problem.getRules().ySize();
        int blanks = total;
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            for (int j = 0; j < row.length(); j++) {
                char ch = row.charAt(j);
                if (ch != '.' && ch != ' ' && ch != '_' && rnd.nextDouble() < threshold) {
                    problem.setValue(problem.getBoard().tileAt(i, j), getValue(problem, "" + ch));
                    blanks--;
                }
            }
        }
        System.out.println("Out of " + total + " fields, " + blanks + " are blank.");
    }

    private static void applyFileToGrid(Problem problem, String resourceFileName, double threshold) {
        Random rnd = new Random();
        try (
                InputStream stream = Main.class.getResourceAsStream(resourceFileName);
                InputStreamReader rawReader = new InputStreamReader(stream);
                BufferedReader reader = new BufferedReader(rawReader);
        ) {
            int total = problem.getRules().xSize() * problem.getRules().ySize();
            int blanks = total;
            for (int i = 0; i < problem.getRules().xSize(); i++) {
                for (int j = 0; j < problem.getRules().ySize(); j++) {
                    String line = reader.readLine();
                    Integer x = (line != null && !"".equals(line.trim()) && !".".equals(line.trim())) ? Integer.valueOf(line) : null;
                    if (x != null && rnd.nextDouble() < threshold) {
                        blanks--;
                        problem.setValue(problem.getBoard().tileAt(i, j), getValue(problem, ClassicRules.descriptValue(x)));
                    }
                }
            }
            System.out.println("Out of " + total + "fields, " + blanks + " are blank.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Rules.Value getValue(Problem problem, String val) {
        return problem.getRules().values().stream()
                .filter(v -> v.getDescription().equals(val))
                .findAny().orElseThrow(() -> new IllegalStateException("No value " + val
                        + " found in values: " + problem.getRules().values()));
    }
}
