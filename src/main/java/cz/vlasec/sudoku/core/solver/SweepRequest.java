package cz.vlasec.sudoku.core.solver;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Rules;
import cz.vlasec.sudoku.core.board.Rules.Value;

import java.util.List;

public class SweepRequest {
    private final SolverCallback callback;
    private final Board board;
    private final List<Value> allValues;

    public SweepRequest(SolverCallback callback, Board board, List<Value> allValues) {
        this.callback = callback;
        this.board = board;
        this.allValues = allValues;
    }

    public SolverCallback getCallback() {
        return callback;
    }

    public Board getBoard() {
        return board;
    }

    public List<Value> getAllValues() {
        return allValues;
    }
}
