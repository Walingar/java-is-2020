package queue.impl;

import java.util.*;

import static java.lang.Math.min;

public class QueueArrayImpl extends AbstractQueue implements Queue {
    private Integer[] queue;
    private int capacity;
    private int capacityMultiplier;
    private int size;
    private int begin;
    private int end;

    private class QueueArrayIterator implements Iterator {

        private int currentElementIndex;

        QueueArrayIterator(int begin) {
            currentElementIndex = begin - 1;
        }

        @Override
        public boolean hasNext() {
            return isIndexInside(currentElementIndex + 1);
        }

        @Override
        public Object next() {
            currentElementIndex++;
            return getElementAt(currentElementIndex);
        }
    }

    QueueArrayImpl() {
        capacity = 1;
        capacityMultiplier = 2;
        queue = new Integer[capacity];
        size = 0;
        begin = 0;
        end = 0;
    }

    @Override
    public Iterator iterator() {
        return new QueueArrayIterator(begin);
    }

    @Override
    public int size() {
        return size;
    }

    private void rearrangeQueue(Set<Integer> unused) {
        if (unused == null) {
            unused = new HashSet<>();
        }
        if (begin == 0 && unused.isEmpty()) {
            return;
        }
        Integer[] newQueue = new Integer[size];
        end = 0;
        for (int i = 0; i < size; i++) {
            if (!unused.contains(begin + i)) {
                newQueue[i] = queue[begin + i];
                end++;
            }
        }
        begin = 0;
        arrayCopy(newQueue, queue);
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

    private void arrayCopy(Object[] from, Object[] to) {
        System.arraycopy(from, 0, to, 0, min(from.length, to.length));
    }

    private void reallocateMemory() {
        rearrangeQueue(null);
        Integer[] temp = new Integer[capacity];
        arrayCopy(queue, temp);
        capacity *= capacityMultiplier;
        queue = new Integer[capacity];
        arrayCopy(temp, queue);
    }

    @Override
    public boolean offer(Object o) {
        if (end == capacity) {
            reallocateMemory();
        }
        queue[end] = (Integer) o;
        size++;
        end++;
        return true;
    }

    @Override
    public Object poll() {
        if (size == 0) {
            return null;
        }
        size--;
        return queue[begin++];
    }

    @Override
    public Object peek() {
        if (size() == 0) {
            return null;
        }
        return queue[begin];
    }
}
