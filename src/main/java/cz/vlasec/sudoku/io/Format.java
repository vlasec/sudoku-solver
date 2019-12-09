package cz.vlasec.sudoku.io;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Puzzle;
import cz.vlasec.sudoku.core.board.Rules.Value;

import java.io.*;
import java.util.function.Function;

interface Format {
    void importFrom(BufferedReader input, Puzzle puzzle, Function<String, Value> conversion) throws IOException;

    void exportTo(BufferedWriter output, Board board, Function<Value, String> conversion) throws IOException;
}
