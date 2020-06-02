package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayQueueImpl extends AbstractQueue<Integer> {
    private final static int DEFAULT_SIZE = 5;
    private final static int TO_INCREASE_DEFAULT_SIZE = 2;

    private int first = 0;
    private int size = 0;
    private int currentSize = DEFAULT_SIZE;
    private int[] queue = new int[DEFAULT_SIZE];


    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new QueueIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        if (currentSize == size) {
            currentSize = currentSize * TO_INCREASE_DEFAULT_SIZE;
            int firstSize = size - first;
            int secondSize = size - firstSize;
            int[] newQueue = new int[currentSize];
            System.arraycopy(queue, first, newQueue, 0, firstSize);
            System.arraycopy(queue, 0, newQueue, firstSize, secondSize);
            first = 0;
            queue = newQueue;

        }
        queue[(size + first) % currentSize] = integer;
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        size--;
        int newFirst = queue[first];
        queue[first] = 0;
        first = (first + 1) % currentSize;
        if (size >= DEFAULT_SIZE && size == currentSize / TO_INCREASE_DEFAULT_SIZE) {
            currentSize = currentSize / TO_INCREASE_DEFAULT_SIZE;
            int[] newQueue = new int[currentSize];
            int firstSize = size - first + 1;
            int secondSize = size - firstSize;
            System.arraycopy(queue, first, newQueue, 0, firstSize);
            System.arraycopy(queue, 0, newQueue, firstSize, secondSize);
            first = 0;
            queue = newQueue;
        }
        return newFirst;
    }

    @Override
    public Integer peek() {
        return size == 0 ? null : queue[first];
    }

    private class QueueIterator implements Iterator<Integer> {
        int elem = first;

        @Override
        public boolean hasNext() {
            return (elem + 1) % currentSize < (first + size) % currentSize;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int value = queue[elem];
            elem = (elem + 1) % currentSize;
            return value;
        }
    }
}
