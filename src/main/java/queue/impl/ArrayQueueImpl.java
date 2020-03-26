package queue.impl;

import java.util.AbstractQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayQueueImpl<T> extends AbstractQueue<T> {

    private static final int INITIAL_CAPACITY = 8;
    private static final double GROWTH_FACTOR = 2;
    private static final int SHRINK_LIMIT = 256;

    private Object[] data = new Object[INITIAL_CAPACITY];
    private int capacity = INITIAL_CAPACITY;
    private int size = 0;
    private int head = 0;
    private int tail = 0;

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(T t) {
        ensureCapacity();
        data[tail] = t;
        tail = incPointer(tail);
        ++size;
        return true;
    }

    private void ensureCapacity() {
        if (capacity < size + 2) {
            var newCapacity = (int) (capacity * GROWTH_FACTOR);
            data = toArray(new Object[newCapacity]);
            capacity = newCapacity;
            head = 0;
            tail = size;
        }
    }

    private void shrinkIfNeeded() {
        if (size < capacity / (GROWTH_FACTOR * 2)) {
            var newCapacity = (int) (capacity / GROWTH_FACTOR);
            if (newCapacity < SHRINK_LIMIT) {
                return;
            }
            data = toArray(new Object[newCapacity]);
            capacity = newCapacity;
            head = 0;
            tail = size;
        }
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        shrinkIfNeeded();
        var returnValue = peek();
        data[head] = null;
        head = incPointer(head);
        --size;
        return returnValue;
    }

    @Override
    public T peek() {
        return getCasted(head);
    }

    @SuppressWarnings("unchecked")
    private T getCasted(int index) {
        return (T) data[index];
    }

    private int incPointer(int pointer) {
        return (pointer + 1) % capacity;
    }

    private class MyIterator implements Iterator<T> {
        int currentPointer = head;

        @Override
        public boolean hasNext() {
            return currentPointer != tail;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var returnValue = getCasted(currentPointer);
            currentPointer = incPointer(currentPointer);
            return returnValue;
        }
    }
}
