package queue.impl;

import org.jetbrains.annotations.NotNull;

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
        head = getNextPositionOfValuesPointer(head);
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
        tail = getNextPositionOfValuesPointer(tail);
        return true;
    }

    private boolean extendValues() {
        int newCapacity = values.length * 2;
        ArrayQueue extendedQueue = new ArrayQueue(newCapacity);
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

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {

            private int current = head;

            @Override
            public boolean hasNext() {
                return current != tail;
            }

            @Override
            public Integer next() {
                if (current == tail) {
                    return null;
                }
                int value = values[current];
                current = getNextPositionOfValuesPointer(current);
                return value;
            }
        };
    }

    private int getNextPositionOfValuesPointer(int currentPosition) {
        return (currentPosition + 1) % values.length;
    }
}
