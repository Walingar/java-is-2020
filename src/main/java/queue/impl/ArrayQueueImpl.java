package queue.impl;

import java.util.*;

public class ArrayQueueImpl<T> extends AbstractQueue<T> {

    private static final int DEFAULT_CAPACITY = 10_001; // Picked by a dice roll =)
    private int capacity;
    private final boolean blocking;
    private Object[] items;
    private int head;
    private int tail;

    public ArrayQueueImpl() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayQueueImpl(int capacity) {
        this(capacity, true);
    }

    public ArrayQueueImpl(int capacity, boolean blocking) {
        this.capacity = capacity;
        this.items = new Object[capacity];
        this.blocking = blocking;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        return tail - head;
    }

    @Override
    public boolean offer(T t) {
        if (tail >= capacity) {
            return false;
        }
        items[tail++] = t;
        return true;
    }

    @Override
    public T poll() {
        if (tail <= head) {
            return null;
        }
        return (T) items[head++];
    }

    @Override
    public T peek() {
        return (T) items[head];
    }
}
