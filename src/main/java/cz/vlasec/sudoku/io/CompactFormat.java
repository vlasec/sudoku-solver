package cz.vlasec.sudoku.io;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Puzzle;
import cz.vlasec.sudoku.core.board.Rules.Value;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.function.Function;

/**
 * This format requires the conversion table to have 1-length strings in it. It can behave incorrectly otherwise.
 */
class CompactFormat implements Format {

    @Override
    public void importFrom(BufferedReader input, Puzzle puzzle, Function<String, Value> conversion) throws IOException {
        for (int i = 0; i < puzzle.getBoard().xSize(); i++) {
            String row = input.readLine();
            for (int j = 0; j < puzzle.getBoard().ySize(); j++) {
                puzzle.setValue(puzzle.getBoard().tileAt(i, j), conversion.apply("" + row.charAt(j)));
            }
        }
    }

    @Override
    public void exportTo(BufferedWriter output, Board board, Function<Value, String> conversion) throws IOException {
        for (int i = 0; i < board.xSize(); i++) {
            for (int j = 0; j < board.ySize(); j++) {
                output.write(conversion.apply(board.tileAt(i, j).value()));
            }
            output.newLine();
        }
    }
}
