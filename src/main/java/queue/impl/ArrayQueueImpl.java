package queue.impl;

import java.util.*;

public class ArrayQueueImpl<T> extends AbstractQueue<T> {

    private final static int MIN_CAPACITY = 16;
    private final static int MULTIPLIER = 2;

    private int size = 0;
    private int head = 0;
    private int capacity = MIN_CAPACITY;
    private Object[] queue = new Object[MIN_CAPACITY];

    @Override
    public Iterator<T> iterator() {
        return new ArrayQueueIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(T t) {
        if (size == capacity) {
            updateArray(true);
        }
        queue[getIndex(head + size)] = t;
        size++;
        return true;
    }

    @Override
    public T poll() {
        if (size == 0) {
            return null;
        }

        size--;
        var value = queue[head];
        queue[head] = null;
        head = getIndex(head + 1);

        if (size == capacity / MULTIPLIER && size >= MIN_CAPACITY) {
            updateArray(false);
        }

        return (T) value;
    }

    @Override
    public T peek() {
        return size == 0 ? null : (T) queue[head];
    }

    private int getIndex(int x) {
        return x % capacity;
    }

    private class ArrayQueueIterator implements Iterator<T> {
        int current = head;

        @Override
        public boolean hasNext() {
            return getIndex(current + 1) < getIndex(head + size);
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var value = (T) queue[current];
            current = getIndex(current + 1);
            return value;
        }
    }

    private void updateArray(boolean enlarge) {
        var newCapacity = enlarge ? capacity * MULTIPLIER : capacity / MULTIPLIER;
        Object[] newQueue = new Object[newCapacity];
        var firstPartSize = size - head + (enlarge ? 0 : 1);
        var secondPartSize = size - firstPartSize;
        System.arraycopy(queue, head, newQueue, 0, firstPartSize);
        System.arraycopy(queue, 0, newQueue, firstPartSize, secondPartSize);
        head = 0;
        capacity = newCapacity;
        queue = newQueue;
    }
}
