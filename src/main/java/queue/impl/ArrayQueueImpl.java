package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ArrayQueueImpl extends BaseQueue {
    private final int limit = 10;
    private final int scaleFactor = 2;
    private int head = 0;
    private int cap = limit;
    private int indexCircularArray = 0;
    private Integer[] elements = new Integer[cap];

    private void ensureCapacity(int bias) {
        if (bias == 0){
            cap *= scaleFactor;
        }
        else {
            cap /= scaleFactor;
        }
        int firstArraySegment = size - head;
        Integer[] newElements = new Integer[cap];
        System.arraycopy(elements, head, newElements, 0, firstArraySegment + bias);
        System.arraycopy(elements, 0, newElements, firstArraySegment + bias, head - bias);
        elements = newElements;
        indexCircularArray = size;
        head = 0;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new ArrayQueueIterator();
    }

    @Override
    public boolean add(Integer integer) {
        assert integer != null;
        if (size == cap){
            ensureCapacity(0);
        }
        elements[indexCircularArray] = integer;
        indexCircularArray = (indexCircularArray + 1) % cap;
        size++;
        return true;
    }

    @Override
    public boolean offer(Integer integer) {
        if (size < cap) {
            add(integer);
            return true;
        }
        return false;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        size--;
        int value = elements[head];
        elements[head] = null;
        head = (head + 1) % cap;
        if (size == cap / (scaleFactor * scaleFactor) && size >= limit){
            ensureCapacity(1);
        }
        return value;
    }

    @Override
    public Integer peek() {
        return elements[head];
    }

    private class ArrayQueueIterator implements Iterator<Integer> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size && elements[currentIndex] != null;
        }

        @Override
        public Integer next() {
            return elements[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}