package queue.impl;

import java.util.AbstractQueue;
import java.util.*;

public class ArrayQueueImpl extends AbstractQueue<Integer> {
    private Integer[] queue;
    private int capacity;
    private int size;
    private int head;
    private int tail;
    private final int resizeCoefficient;

    ArrayQueueImpl() {
        capacity = 1;
        resizeCoefficient = 2;
        queue = new Integer[capacity];
        size = 0;
        head = 0;
        tail = 0;
    }

    private void resizeQueue() {
        capacity *= resizeCoefficient;
        Integer[] buffer = new Integer[capacity];
        System.arraycopy(queue, 0, buffer, 0, queue.length);
        queue = buffer;
    }

    @Override
    public Iterator iterator() {
        return new ArrayQueueIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer item) {
        if (tail == capacity) {
            resizeQueue();
        }
        queue[tail] = item;
        size++;
        tail++;
        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        size--;
        return queue[head++];

    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return queue[head];
    }
    private class ArrayQueueIterator implements Iterator<Integer> {
        private int currentIndex;

        ArrayQueueIterator() {
            currentIndex = head;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < tail;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentIndex++;
            return queue[currentIndex];
        }
    }
}
