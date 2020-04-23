package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayQueueImpl extends AbstractQueue<Integer> {

    private static final int START_CAPACITY = 16;
    private static final double FACTOR = 2;
    private static final int LIMIT = 256;

    private Object[] queue = new Object[START_CAPACITY];
    private int capacity = START_CAPACITY;
    private int size = 0;
    private int head = 0;
    private int tail = 0;

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new QueueIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        checkCapacity();
        queue[tail] = integer;
        tail = incCursor(tail);
        ++size;
        return true;
    }

    @Override
    public Integer poll() {
        if (isEmpty()) {
            return null;
        }
        reduceCapacityIfNeeded();
        var returnValue = peek();
        queue[head] = null;
        head = incCursor(head);
        --size;
        return returnValue;
    }

    @Override
    public Integer peek() {
        return getValue(head);
    }

    private void checkCapacity() {
        if (capacity < size + 2) {
            var newCapacity = (int) (capacity * FACTOR);
            updateCapacity(newCapacity);
        }
    }

    private void reduceCapacityIfNeeded() {
        if (size < capacity / (FACTOR * 2)) {
            var newCapacity = (int) (capacity / FACTOR);
            if (newCapacity < LIMIT) {
                return;
            }
            updateCapacity(newCapacity);
        }
    }

    private void updateCapacity(int newCapacity) {
        queue = toArray(new Object[newCapacity]);
        capacity = newCapacity;
        head = 0;
        tail = size;
    }

    private int incCursor(int cursor) {
        return (cursor + 1) % capacity;
    }

    private Integer getValue(int index) {
        return (Integer) queue[index];
    }

    private class QueueIterator implements Iterator<Integer> {
        int currentCursor = head;

        @Override
        public boolean hasNext() {
            return currentCursor != tail;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var returnValue = getValue(currentCursor);
            currentCursor = incCursor(currentCursor);
            return returnValue;
        }
    }
}
