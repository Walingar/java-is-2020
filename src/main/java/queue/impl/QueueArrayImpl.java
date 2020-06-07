package queue.impl;

import org.jetbrains.annotations.NotNull;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public class QueueArrayImpl extends AbstractQueue<Integer> implements Queue<Integer> {
    private Integer[] queue;
    private int capacity;
    private int size;
    private int begin;
    private int end;
    private final int coefficient;

    QueueArrayImpl() {
        capacity = 1;
        coefficient = 2;
        queue = new Integer[capacity];
        size = 0;
        begin = 0;
        end = 0;
    }

    @NotNull
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

    private void reallocate(int newCapacity) {
        rearrangeQueue();
        Integer[] temp = new Integer[size];
        setRange(queue, temp, 0, size);
        capacity = newCapacity;
        queue = new Integer[capacity];
        setRange(temp, queue, 0, size);
    }

    @Override
    public boolean offer(Integer element) {
        if (end == capacity) {
            reallocate(capacity * coefficient);
        }
        queue[end] = element;
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
        int minUpdateSize = capacity / 2 * coefficient;
        if (size < minUpdateSize && capacity % coefficient == 0) {
            reallocate(minUpdateSize);
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