package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

public class ArrayQueueImpl<T> implements Queue<T> {

    private final boolean blocking;
    private Object[] storage;
    private int head;
    private int tail;

    public ArrayQueueImpl() {
        this(1_000_001);
    }

    public ArrayQueueImpl(int capacity) {
        this(capacity, true);
    }

    public ArrayQueueImpl(int capacity, boolean blocking) {
        this.storage = new Object[capacity];
        this.blocking = blocking;
    }

    @Override
    public int size() {
        return tail - head;
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public boolean contains(Object o) {
        return IntStream.range(head, tail).anyMatch(i -> storage[i] == o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(
                Arrays.copyOfRange(storage, head, tail))
                .iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return Arrays.copyOfRange(storage, head, tail);
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] t1s) {
        return null;
    }


    @Override
    public boolean add(T t) {
        if (tail < storage.length) {
            storage[tail++] = t;
            return true;
        }
        if (blocking) {
            throw new IllegalStateException();
        }
        storage = Arrays.copyOf(storage, storage.length * 2);
        return add(t);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> collection) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {
        head = 0;
        tail = 0;
    }

    @Override
    public boolean offer(T t) {
        return false;
    }

    @Override
    public T remove() {
        return null;
    }

    @Override
    public T poll() {
        if (tail > head) {
            return (T)storage[head++];
        }
        return null;
    }

    @Override
    public T element() {
        return null;
    }

    @Override
    public T peek() {
        if (tail > head) {
            return storage[head];
        }
        throw new IllegalStateException();
    }
}
