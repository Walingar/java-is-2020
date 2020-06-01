package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;

public class ArrayQueueImpl extends AbstractQueue<Integer> {
    private Integer[] elements;

    private int size;
    private int headIndex;
    private int tailIndex;
    private int capacity;

    private static final int MIN_CAPACITY = 100;
    private static final int CAPACITY_MULTIPLIER = 2;


    public ArrayQueueImpl() {
        elements = new Integer[MIN_CAPACITY];
        size = 0;
        headIndex = 0;
        tailIndex = 0;
        capacity = MIN_CAPACITY;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new ArrayQueueIterator();
    }

    private class ArrayQueueIterator implements Iterator<Integer> {
        private int currentIndex = headIndex;

        @Override
        public boolean hasNext() {
            return currentIndex != tailIndex + 1;
        }

        @Override
        public Integer next() {
            if (currentIndex > tailIndex) {
                return null;
            }
            Integer value = elements[currentIndex];
            currentIndex += 1;
            return value;
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        if (size != 0) {
            tailIndex += 1;
        }
        size += 1;
        elements[tailIndex] = integer;
        updateQueueCapacity();
        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        } else {
            var element = elements[headIndex];
            size -= 1;
            if (headIndex != tailIndex) {
                headIndex += 1;
            }
            updateQueueCapacity();
            return element;
        }
    }

    private void updateQueueCapacity() {
        if (capacity < size + 2) {
            capacity = capacity * CAPACITY_MULTIPLIER;
            moveElementsToNewArray();
        } else if (size < capacity / (CAPACITY_MULTIPLIER * 2) && capacity > MIN_CAPACITY) {
            capacity = capacity / CAPACITY_MULTIPLIER;
            moveElementsToNewArray();
        } else if (capacity < tailIndex + 2) {
            System.arraycopy(elements, headIndex, elements, 0, size);
            headIndex = 0;
            tailIndex = size - 1;
        }
    }

    private void moveElementsToNewArray() {
        var newElements = new Integer[capacity];
        System.arraycopy(elements, headIndex, newElements, 0, size);
        elements = newElements;
        headIndex = 0;
        tailIndex = size - 1;
    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        } else {
            return elements[headIndex];
        }
    }
}
