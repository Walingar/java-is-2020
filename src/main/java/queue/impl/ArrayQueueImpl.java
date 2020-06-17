package queue.impl;

import java.util.AbstractQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayQueueImpl<T> extends AbstractQueue<T> {

    private static final double CAPACITY_FACTOR = 2;
    private static final int MINIMAL_CAPACITY = 128;

    private Object[] queueElements = new Object[MINIMAL_CAPACITY];
    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private int capacity = MINIMAL_CAPACITY;

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(T t) {
        increaseCapacity();
        queueElements[tail] = t;
        tail = increasePointer(tail);
        size++;
        return true;
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        checkShrink();
        var returnValue = peek();
        queueElements[head] = null;
        head = increasePointer(head);
        size--;
        return returnValue;
    }

    @Override
    public T peek() {
        return getElement(head);
    }

    private void increaseCapacity() {
        if (capacity < size + 2) {
            var newCapacity = (int) (capacity * CAPACITY_FACTOR);
            applyCapacity(newCapacity);
        }
    }

    private void checkShrink() {
        if (size < capacity / CAPACITY_FACTOR) {
            var newCapacity = (int) (capacity / Math.pow(CAPACITY_FACTOR, 2));
            if (newCapacity < MINIMAL_CAPACITY) {
                return;
            }
            applyCapacity(newCapacity);
        }
    }

    private void applyCapacity(int newCapacity) {
        queueElements = toArray(new Object[newCapacity]);
        tail = size;
        head = 0;
        capacity = newCapacity;
    }

    @SuppressWarnings("unchecked")
    private T getElement(int index) {
        return (T) queueElements[index];
    }

    private int increasePointer(int pointer) {
        return (pointer + 1) % capacity;
    }

    private class ArrayIterator implements Iterator<T> {
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
            var returnValue = getElement(currentPointer);
            currentPointer = increasePointer(currentPointer);
            return returnValue;
        }
    }
}