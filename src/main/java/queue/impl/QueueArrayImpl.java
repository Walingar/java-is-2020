package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueArrayImpl extends AbstractQueue<Integer> {
    private Integer[] queue;
    private int capacity;
    private int size;
    private int begin;
    private int end;
    private int coefficient;

    QueueArrayImpl() {
        capacity = 1;
        coefficient = 2;
        queue = new Integer[capacity];
        size = 0;
        begin = 0;
        end = 0;
    }

    private class ArrayQueueIterator implements Iterator {
        private int currentIndex;

        @Override
        public boolean hasNext() {
            return currentIndex < end;
        }

        ArrayQueueIterator() {
            currentIndex = begin;
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

    private void resizeQueue() {
        capacity *= coefficient;
        Integer[] buffer = new Integer[capacity];
        System.arraycopy(queue, 0, buffer, 0, queue.length);
        queue = buffer;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator iterator() {
        return new ArrayQueueIterator();
    }

    @Override
    public boolean offer(Integer item) {
        if (end == capacity) {
            resizeQueue();
        }
        queue[end] = (Integer) item;
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
        return queue[begin++];

    }

    @Override
    public Integer peek() {
        if (size() == 0) {
            return null;
        }
        return queue[begin];
    }
}