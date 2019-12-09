package cz.vlasec.sudoku.core.board;

import cz.vlasec.sudoku.core.board.Rules.Value;
import cz.vlasec.sudoku.core.utils.CollectionFactory;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * A tile in the board. It is mutable as long as you have a reference to its mutator.
 */
public class Tile {
    private final String description;

    private Value value;
    private Set<Value> candidates;

    private Tile(String description) {
        this.description = description;
    }

    public static TileMutator createTile(Collection<Value> initialCandidates, String description) {
        Tile tile = new Tile(description);
        tile.candidates = CollectionFactory.copyValuesSet(initialCandidates);
        return new TileMutator(tile);
    }

    public TileMutator copyTile() {
        Tile copy = new Tile(description);
        copy.candidates = CollectionFactory.copyValuesSet(candidates);
        copy.value = value;
        return new TileMutator(copy);
    }

    /** Unique description of the tile. */
    public String description() {
        return description;
    }

    /** Value (if there is one) or null. */
    public Value value() {
        return value;
    }

    /** A list of candidates for the value. Candidates are being ruled out by the solver. */
    public Set<Value> candidates() {
        return Collections.unmodifiableSet(candidates);
    }

    /** Equality only says that it is the same tile. Value and candidates aren't relevant. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return Objects.equals(description, tile.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return description + "=" + (value != null ? value : "_") + " " + candidates;
    }

    /** A mutator - basically a handle that allows mutation of the Tile. Ensures  */
    public static class TileMutator {
        private final Tile tile;
        private List<BiConsumer<Tile, Value>> onRemove = new ArrayList<>();

        private TileMutator(Tile tile) {
            this.tile = tile;
        }

        public void setOnRemove(BiConsumer<Tile, Value> onRemove) {
            this.onRemove.add(onRemove);
        }

        public Tile getTile() {
            return tile;
        }

        public void setValue(Value newValue) {
            tile.value = newValue;
        }

        public void removeCandidate(Value exCandidate) {
            tile.candidates.remove(exCandidate);
            for (BiConsumer<Tile, Value> consumer : onRemove) {
                consumer.accept(tile, exCandidate);
            }
        }
    }
}
