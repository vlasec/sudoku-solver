package cz.vlasec.sudoku.io;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Puzzle;
import cz.vlasec.sudoku.core.board.Rules;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PrettyPrintFormat implements Format {

    @Override
    public void importFrom(BufferedReader input, Puzzle puzzle, Function<String, Rules.Value> conversion) throws IOException {
        input.readLine();
        for (int i = 0; i < puzzle.getBoard().xSize(); i++) {
            CharSequence line = input.readLine();
            for (int j = 0; j < puzzle.getBoard().ySize(); j++) {
                String value = line.subSequence(1, 3).toString().trim();
                puzzle.setValue(puzzle.getBoard().tileAt(i, j), conversion.apply(value));
                line = line.subSequence(4, line.length());
            }
            input.readLine();
        }
    }

    @Override
    public void exportTo(BufferedWriter output, Board board, Function<Rules.Value, String> conversion) throws IOException {
        output.write(lineRow(board.ySize()));
        output.newLine();
        for (int i = 0; i < board.xSize(); i++) {
            for (int j = 0; j < board.ySize(); j++) {
                output.write(String.format("|%2s ", conversion.apply(board.tileAt(i, j).value())));
            }
            output.write("|");
            output.newLine();
            output.write(lineRow(board.ySize()));
            output.newLine();
        }
    }

    private String lineRow(int len) {
        return Stream.generate(() -> "---").limit(len)
                .collect(Collectors.joining("+", "+", "+"));
    }
}
