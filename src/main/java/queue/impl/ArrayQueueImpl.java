package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ArrayQueueImpl extends AbstractQueue<Integer> {

    private static final int INITIAL_CAPACITY = 11;
    private static final int CAPACITY_RATE = 2;

    private int head;
    private int tail;
    private Integer[] elements;

    public ArrayQueueImpl() {
        this(INITIAL_CAPACITY);
    }

    public ArrayQueueImpl(int capacity) {
        this.elements = new Integer[capacity];
    }

    @Override
    public Iterator<Integer> iterator() {
        return new ArrayQueueIterator();
    }

    @Override
    public int size() {
        return tail - head;
    }

    @Override
    public boolean offer(Integer value) {
        if (tail >= elements.length) {
            try {
                grow();
            } catch (ArithmeticException ex) {
                throw ex;
            }
        }

        elements[tail++] = value;
        return true;
    }

    @Override
    public Integer poll() {
        int capacity = elements.length;
        if (capacity == 0) {
            return null;
        }

        if (head > capacity - capacity / CAPACITY_RATE) {
            shrink(capacity);
        }

        var result = elements[head];
        head++;
        return result;
    }

    @Override
    public Integer peek() {
        if (size() == 0) {
            return null;
        }

        return elements[head];
    }

    private void grow() {
        int oldCapacity = this.elements.length;
        int newCapacity = oldCapacity + (oldCapacity / 2);
        this.elements = Arrays.copyOf(elements, newCapacity);
    }

    private void shrink(int oldCapacity) {
        int newCapacity = oldCapacity / CAPACITY_RATE;
        var newLength = oldCapacity - newCapacity;
        var a = elements.length;
        elements = Arrays.copyOfRange(elements, newLength, oldCapacity);
        head = head - newLength;
        tail = tail - newLength;
    }

    private Integer getByIndex(int index) {
        return elements[index];
    }

    private class ArrayQueueIterator implements Iterator<Integer> {

        int pointer = head;

        @Override
        public boolean hasNext() {
            return pointer != tail;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var result = getByIndex(pointer);
            pointer++;
            return result;
        }
    }
}


