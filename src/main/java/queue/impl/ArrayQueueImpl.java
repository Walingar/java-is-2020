package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ArrayQueueImpl<T> extends AbstractQueue<T> {

    private static final int DEFAULT_CAPACITY = 10_001; // Picked by a dice roll =)
    private static final int CAPACITY_CHANGE_RATE = 2;
    private int capacity;
    private final boolean fixed;
    private Object[] items;
    private int head;
    private int tail;

    public ArrayQueueImpl() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayQueueImpl(int capacity) {
        this(capacity, true);
    }

    public ArrayQueueImpl(int capacity, boolean fixed) {
        this.capacity = capacity;
        this.items = new Object[capacity];
        this.fixed = fixed;
    }

    private void extendCapacity() throws ArithmeticException { // Explicitly notify that we can throw here
        capacity = Math.multiplyExact(capacity, CAPACITY_CHANGE_RATE);
        items = Arrays.copyOf(items, capacity);
//        Here we don't need to adjust head/tail since we just extended the array
    }

    private void shrinkCapacity() {
        var oldCapacity = capacity;
        capacity = capacity / CAPACITY_CHANGE_RATE;
        var newStart = oldCapacity - capacity;
        items = Arrays.copyOfRange(items, newStart, oldCapacity);
        head = head - newStart;
        tail = tail - newStart;
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new ArrayQueueIterator();
    }

    @Override
    public int size() {
        return tail - head;
    }

    @Override
    public boolean offer(T t) {
        if (tail >= capacity) {
            if (fixed) {
                return false;
            } else {
                try {
                    extendCapacity();
                } catch (ArithmeticException e) {
                    System.err.println("Queue reached max possible size");
                    return false;
                }
            }
        }
        items[tail++] = t;
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T poll() {
        if (tail <= head) {
            return null;
        }
//        Here we check that we are beyond a point from which we can safely drop to capacity/rate
//        This means that we are no farther _from the end_ then capacity/rate
        if (head > capacity - capacity / CAPACITY_CHANGE_RATE) {
            shrinkCapacity();
        }
        return (T) items[head++];
    }


    @Override
    @SuppressWarnings("unchecked")
    public T peek() {
        return (T) items[head];
    }

    private class ArrayQueueIterator implements Iterator<T> {
        private int iteratorHead = head;

        @Override
        public boolean hasNext() {
            return iteratorHead != tail;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (iteratorHead == tail) {
                throw new NoSuchElementException("");
            }
            return (T) items[iteratorHead++];
        }
    }
}
