package queue.impl;

import java.util.*;

import static java.lang.Math.min;

public class QueueArrayImpl extends AbstractQueue<Integer> implements Queue<Integer> {
    private Integer[] queue;
    private int capacity;
    private final int capacityMultiplier;
    private int size;
    private int begin;
    private int end;


    QueueArrayImpl() {
        capacity = 1;
        capacityMultiplier = 2;
        queue = new Integer[capacity];
        size = 0;
        begin = 0;
        end = 0;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new QueueArrayIterator(begin);
    }

    @Override
    public int size() {
        return size;
    }

    private void rearrangeQueue() {
        if (begin == 0) {
            return;
        }
        setRange(queue, queue, begin, size);
        begin = 0;
        end = size;
    }

    private boolean isIndexInside(int index) {
        return begin <= index && index < end;
    }

    private int getElementAt(int index) {
        if (isIndexInside(index)) {
            return queue[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private void setRange(Integer[] from, Integer[] to, int begin, int size) {
        System.arraycopy(from, begin, to, 0, size);
    }

    private void reallocateMemory(int newCapacity) {
        rearrangeQueue();
        Integer[] temp = new Integer[size];
        setRange(queue, temp, 0, size);
        capacity = newCapacity;
        queue = new Integer[capacity];
        setRange(temp, queue, 0, size);
    }

    @Override
    public boolean offer(Integer o) {
        if (end == capacity) {
            reallocateMemory(capacity * capacityMultiplier);
        }
        queue[end] = o;
        size++;
        end++;
        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        size--;
        int returnValue = queue[begin];
        begin++;
        int minimalCapacityUpdateSize = capacity / capacityMultiplier / capacityMultiplier;
        if (size < minimalCapacityUpdateSize && capacity % capacityMultiplier == 0) {
            reallocateMemory(minimalCapacityUpdateSize);
        }
        return returnValue;
    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return queue[begin];
    }

    private class QueueArrayIterator implements Iterator<Integer> {

        private int currentElementIndex;

        QueueArrayIterator(int begin) {
            currentElementIndex = begin - 1;
        }

        @Override
        public boolean hasNext() {
            return isIndexInside(currentElementIndex + 1);
        }

        @Override
        public Integer next() {
            currentElementIndex++;
            return getElementAt(currentElementIndex);
        }
    }

}
