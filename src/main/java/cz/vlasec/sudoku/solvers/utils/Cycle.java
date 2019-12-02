package cz.vlasec.sudoku.solvers.utils;

import java.util.Iterator;
import java.util.Set;

public abstract class Cycle<A, B, I, O> {
    private Node<A, B> first;
    private Node<A, B> last;

    protected Cycle(I firstEntry) {
        Iterator<A> iter = getBoth(firstEntry).iterator();
        this.first = new Node<>(iter.next());
        this.last = new Node<>(iter.next());
        Node<B, A> glue = new Node<>(getGlue(firstEntry));
        glue.setPrev(first);
        glue.setNext(last);
    }

    public boolean add(I pair) {
        if (isComplete()) {
            return false;
        }
        if (getBoth(pair).contains(first.value)) {
            first.setPrev(new Node<>(getGlue(pair)));
            A other = getOther(pair, first.value);
            first.prev.setPrev((other == last.value) ? last : new Node<>(other));
            first = first.prev.prev;
            return true;
        } else if (getBoth(pair).contains(last.value)) {
            last.setNext(new Node<>(getGlue(pair)));
            A other = getOther(pair, last.value);
            last.next.setNext((other == first.value) ? first : new Node<>(other));
            last = last.next.next;
            return true;
        } else {
            return false;
        }
    }

    protected abstract Set<A> getBoth(I i);

    protected abstract A getOther(I i, A one);

    protected abstract B getGlue(I i);

    protected abstract O createO(B leftGlue, A value, B rightGlue);

    protected abstract I createI(A leftValue, B glue, A rightValue);

    private void checkComplete() {
        if (!isComplete()) {
            throw new IllegalStateException("This is not a complete cycle!");
        }
    }

    public Iterator<O> iteratorO() {
        checkComplete();
        return new IteratorO();
    }

    public Iterator<O> iteratorI() {
        checkComplete();
        return new IteratorO();
    }

    public boolean isComplete() {
        return first == last;
    }

    private class Node<X, Y> {
        private final X value;
        private Node<Y, X> prev;
        private Node<Y, X> next;

        public Node(X value) {
            this.value = value;
        }

        public void setPrev(Node<Y, X> previous) {
            this.prev = previous;
            previous.next = this;
        }

        public void setNext(Node<Y, X> next) {
            this.next = next;
            next.prev = this;
        }
    }

    private abstract class BaseIterator<X> implements Iterator<X> {
        Node<A, B> currentNode = first;
        boolean atStart = true;

        @Override
        public boolean hasNext() {
            return atStart || (currentNode != last);
        }

        @Override
        public X next() {
            X pair = createPair();
            currentNode = currentNode.next.next;
            atStart = false;
            return pair;
        }

        protected abstract X createPair();
    }

    private class IteratorO extends BaseIterator<O> {
        public O createPair() {
            return createO(currentNode.prev.value, currentNode.value, currentNode.next.value);
        }
    }

    private class IteratorI extends BaseIterator<I> {
        public I createPair() {
            return createI(currentNode.value, currentNode.next.value, currentNode.next.next.value);
        }
    }

}
