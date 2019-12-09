package cz.vlasec.sudoku.io;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Puzzle;
import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.board.Tile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

class NumericFormat implements Format {
    @Override
    public void importFrom(BufferedReader input, Puzzle puzzle, Function<String, Value> conversion) throws IOException {
        try (Scanner scanner = new Scanner(input);) {
            for (int i = 0; i < puzzle.getBoard().xSize(); i++) {
                for (int j = 0; j < puzzle.getBoard().ySize(); j++) {
                    String valueStr = scanner.next();
                    puzzle.setValue(puzzle.getBoard().tileAt(i, j), conversion.apply(valueStr));
                }
            }
        }
    }

    @Override
    public void exportTo(BufferedWriter output, Board board, Function<Value, String> conversion) throws IOException {
        int maxLength = maxLength(board, conversion);
        for (int i = 0; i < board.xSize(); i++) {
            for (int j = 0; j < board.ySize(); j++) {
                output.write(String.format(" %" + maxLength + "s", conversion.apply(board.tileAt(i, j).value())));
            }
            output.newLine();
        }
    }

    private int maxLength(Board board, Function<Value, String> conversion) {
        return Stream.iterate(0, i -> i + 1)
                .limit(board.xSize() * board.ySize())
                .map(i -> board.tileAt(i / board.ySize(), i % board.ySize()))
                .map(Tile::value)
                .map(conversion)
                .map(String::length)
                .max(Integer::compare)
                .orElse(1);
    }
}
