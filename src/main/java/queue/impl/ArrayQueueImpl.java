package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayQueueImpl extends AbstractQueue<Integer> {
    private static final double FACTOR = 2;
    private static final int MIN_CAPACITY = 32;

    private Integer[] elements = new Integer[MIN_CAPACITY];
    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private int capacity = MIN_CAPACITY;

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new ArrayIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        increaseCapacity();
        elements[tail] = integer;
        tail = increasePointer(tail);
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (isEmpty()) {
            return null;
        }
        reduceCapacity();
        var returnValue = peek();
        elements[head] = null;
        head = increasePointer(head);
        size--;
        return returnValue;
    }

    @Override
    public Integer peek() {
        return getElement(head);
    }

    private void increaseCapacity() {
        if (capacity < size + 2) {
            var newCapacity = (int) (capacity * FACTOR);
            applyCapacity(newCapacity);
        }
    }

    private void reduceCapacity() {
        if (size < capacity / (FACTOR * 2)) {
            var newCapacity = (int) (capacity / FACTOR);
            if (newCapacity < MIN_CAPACITY) {
                return;
            }
            applyCapacity(newCapacity);
        }
    }

    private void applyCapacity(int newCapacity) {
        elements = toArray(new Integer[newCapacity]);
        tail = size;
        head = 0;
        capacity = newCapacity;
    }


    private Integer getElement(int index) {
        return elements[index];
    }

    private int increasePointer(int pointer) {
        return (pointer + 1) % capacity;
    }

    private class ArrayIterator implements Iterator<Integer> {
        int currentPointer = head;

        @Override
        public boolean hasNext() {
            return currentPointer != tail;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var returnValue = getElement(currentPointer);
            currentPointer = increasePointer(currentPointer);
            return returnValue;
        }
    }
}
