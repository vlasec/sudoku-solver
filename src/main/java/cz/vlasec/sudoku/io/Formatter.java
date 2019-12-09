package cz.vlasec.sudoku.io;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Puzzle;
import cz.vlasec.sudoku.core.board.Rules;
import cz.vlasec.sudoku.core.board.Rules.Value;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

/**
 * This class serves input/output purposes - the puzzle needs to be imported from some format.
 * The result also needs to be formatted for output.
 * Since there are multiple formats used online, this formatter should cover a better part of those used there.
 */
public class Formatter {
    public static final Set<String> EMPTY_TILES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(".", "_", " ")));

    public static void importFrom(Supplier<InputStream> supplier, Puzzle puzzle, Format format, Function<String, Value> conversion) {
        try (
                InputStream inputStream = supplier.get();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
        ) {
            getFormat(format).importFrom(reader, puzzle, conversion);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static void importFromString(String input, Puzzle puzzle, Format format, Function<String, Value> conversion) {
        ByteArrayInputStream stream = new ByteArrayInputStream(input.getBytes());
        importFrom(() -> stream, puzzle, format, conversion);
    }

    public static void exportTo(Supplier<OutputStream> supplier, Board board, Format format, Function<Value, String> conversion) {
        try (
                OutputStream outputStream = supplier.get();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                BufferedWriter writer = new BufferedWriter(outputStreamWriter);
        ) {
            getFormat(format).exportTo(writer, board, conversion);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static String exportToString(Board board, Format format, Function<Value, String> conversion) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        exportTo(() -> stream, board, format, conversion);
        return stream.toString();
    }

    private static cz.vlasec.sudoku.io.Format getFormat(Format format) {
        switch (format) {
            case COMPACT:
                return new CompactFormat();
            case NUMERIC:
                return new NumericFormat();
            case PRETTY_PRINT:
                return new PrettyPrintFormat();
            default:
                throw new IllegalStateException("No formatter exists for format " + format);
        }
    }

    public enum Format {
        COMPACT,
        NUMERIC,
        PRETTY_PRINT,
        /**/;
    }

    /**
     * Basic set of conversions
     */
    public interface Conversions {
        static Function<String, Value> simpleInput(Rules rules) {
            Map<String, Value> map = rules.values().stream()
                    .collect(Collectors.toMap(Value::getDescription, identity()));
            return val -> EMPTY_TILES.contains(val) ? null : map.get(val);
        }

        static Function<Value, String> simpleOutput(String descriptionForEmpty) {
            return val -> val == null ? descriptionForEmpty : val.getDescription();
        }

        static Function<String, Value> numericInput(Rules rules) {
            return val -> EMPTY_TILES.contains(val) ? null : rules.values().get(Integer.parseInt(val) - 1);
        }

        static Function<Value, String> numericOutput(Rules rules) {
            Map<Value, String> map = Stream.iterate(0, i -> i + 1)
                    .limit(rules.values().size())
                    .collect(Collectors.toMap(rules.values()::get, i -> "" + (i+1)));
            return val -> val == null ? "." : map.get(val);
        }
    }
}
