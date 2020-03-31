package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Objects;

public class ArrayQueue extends AbstractQueue<Integer> {

    private int[] values;
    private int head;
    private int tail;

    public ArrayQueue() {
        this(100);
    }

    public ArrayQueue(int capacity) {
        values = new int[capacity];
    }

    @Override
    public int size() {
        int diff = tail - head;
        return diff >= 0 ? diff : diff + values.length;
    }

    @Override
    public Integer peek() {
        return isEmpty() ? null : values[head];
    }

    @Override
    public Integer poll() {
        if (isEmpty()) {
            return null;
        }
        int value = values[head];
        head = (head + 1) % values.length;
        return value;
    }

    @Override
    public boolean offer(Integer element) {
        Objects.requireNonNull(element);
        if (size() == values.length - 1) {
            boolean extensionSuccessful = extendValues();
            if (!extensionSuccessful) {
                return false;
            }
        }
        values[tail] = element;
        tail = (tail + 1) % values.length;
        return true;
    }

    private boolean extendValues() {
        ArrayQueue extendedQueue;
        try {
            int newCapacity = Math.multiplyExact(values.length, 2);
            extendedQueue = new ArrayQueue(newCapacity);
        } catch (Exception e) {
            return false;
        }
        Integer element;
        while ((element = poll()) != null) {
            extendedQueue.add(element);
        }
        values = extendedQueue.values;
        head = extendedQueue.head;
        tail = extendedQueue.tail;
        return true;
    }

    @Override
    public void clear() {
        head = 0;
        tail = 0;
    }

    @Override
    public Iterator<Integer> iterator() {
        return null;
    }
}
