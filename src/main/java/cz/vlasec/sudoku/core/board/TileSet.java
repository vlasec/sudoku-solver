package cz.vlasec.sudoku.core.board;

import cz.vlasec.sudoku.core.board.Rules.Value;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wrapper for a set of tiles. Can be used as a simple set, but compared to that, it can hold extra information.
 */
public class TileSet extends AbstractSet<Tile> {
    private final TileSetType type;
    private final Set<Tile> delegate;

    private final Map<Value, Set<Tile>> valueCandidates;

    private TileSet(Set<Tile> delegate, TileSetType type, Map<Value, Set<Tile>> valueCandidates) {
        this.type = type;
        this.delegate = Collections.unmodifiableSet(new HashSet<>(delegate));
        this.valueCandidates = valueCandidates;
    }

    public Set<Tile> candidatesForValue(Value value) {
        return Collections.unmodifiableSet(valueCandidates.getOrDefault(value, Collections.emptySet()));
    }

    public static TileSetMutator createTileSet(Set<Tile> delegate, TileSetType type) {
        Map<Value, Set<Tile>> valueCandidates = delegate.stream()
                .map(Tile::candidates)
                .flatMap(Set::stream)
                .distinct()
                .collect(Collectors.toMap(
                        x -> x,
                        x -> delegate.stream().filter(tile -> tile.candidates().contains(x))
                                .collect(Collectors.toSet())
                ));
        TileSet set = new TileSet(delegate, type, valueCandidates);
        return new TileSetMutator(set);
    }

    public TileSetType type() {
        return type;
    }

    /**
     * Returns the tiles of this set except for those excluded.
     * Similar to {@link #retainAll} which is unsupported in here as this set is unmodifiable.
     */
    public Set<Tile> intersection(Set<Tile> otherSet) {
        Set<Tile> intersection = new HashSet<>();
        for (Tile tile : delegate) {
            if (otherSet.contains(tile)) {
                intersection.add(tile);
            }
        }
        return intersection;
    }


    /**
     * Returns the tiles of this set except for those excluded.
     * Similar to {@link #removeAll} which is unsupported in here as this set is unmodifiable.
     */
    public Set<Tile> except(Set<Tile> exclude) {
        Set<Tile> copy = new HashSet<>();
        for (Tile tile : delegate) {
            if (!exclude.contains(tile)) {
                copy.add(tile);
            }
        }
        return copy;
    }

    @Override
    public Iterator<Tile> iterator() {
        return delegate.iterator();
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    /**
     * Type of the tile set
     */
    public interface TileSetType {
        /** Description of the tile set. For example "row", "column", "cell" or "diagonal". */
        String description();

        /**
         * @return true if this TileSet type can have an intersection larger than 1 tile with the other type.
         */
        boolean canIntersectWith(TileSetType otherType);

        boolean isPerpendicularTo(TileSetType otherType);
    }

    public static class TileSetMutator {
        private final TileSet set;

        private TileSetMutator(TileSet set) {
            this.set = set;
        }

        public TileSet getSet() {
            return set;
        }

        void removeCandidate(Tile exCandidate, Value value) {
            set.valueCandidates.get(value).remove(exCandidate);
        }
    }
}
