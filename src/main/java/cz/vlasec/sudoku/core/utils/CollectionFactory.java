package cz.vlasec.sudoku.core.utils;

import cz.vlasec.sudoku.core.board.Rules.Value;

import java.util.*;

public class CollectionFactory {
    private CollectionFactory() {
    }

    public static Set<Value> copyValuesSet(Collection<Value> values) {
        return new HashSet<>(values);
    }
}
