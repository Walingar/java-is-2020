package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Objects;

public class ArrayQueueImpl extends AbstractQueue<Integer> {

    private static final int MIN_CAPACITY = 100;

    private int[] values;
    private int head;
    private int tail;

    public ArrayQueueImpl() {
        this(MIN_CAPACITY);
    }

    public ArrayQueueImpl(int capacity) {
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
        decreaseValuesCapacityIfNeeded();
        head = getNextPositionOfValuesPointer(head);
        return value;
    }

    private void decreaseValuesCapacityIfNeeded() {
        if (size() <= values.length / 2) {
            int newCapacity = values.length * 2 / 3;
            if (newCapacity >= MIN_CAPACITY) {
                changeValuesCapacity(newCapacity);
            }
        }
    }

    @Override
    public boolean offer(Integer element) {
        Objects.requireNonNull(element);
        increaseValuesCapacityIfNeeded();
        values[tail] = element;
        tail = getNextPositionOfValuesPointer(tail);
        return true;
    }

    private void increaseValuesCapacityIfNeeded() {
        if (size() == values.length - 1) {
            int newCapacity = values.length * 2;
            changeValuesCapacity(newCapacity);
        }
    }

    private void changeValuesCapacity(int newCapacity) {
        int newTail = 0;
        int[] newValues = new int[newCapacity];
        for (int element : this) {
            newValues[newTail++] = element;
        }
        head = 0;
        tail = newTail;
        values = newValues;
    }

    @Override
    public void clear() {
        head = 0;
        tail = 0;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new ArrayQueueIterator();
    }

    private int getNextPositionOfValuesPointer(int currentPosition) {
        return (currentPosition + 1) % values.length;
    }

    private class ArrayQueueIterator implements Iterator<Integer> {

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
    }
}