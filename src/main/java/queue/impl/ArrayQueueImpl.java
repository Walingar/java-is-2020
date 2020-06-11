package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Objects;

public class ArrayQueueImpl extends AbstractQueue<Integer> {

    private int[] values;
    private int head;
    private int tail;

    public ArrayQueueImpl() {
        this(100);
    }

    public ArrayQueueImpl(int size) {
        values = new int[size];
    }

    @Override
    public int size() {
        return head > tail ? values.length - head + tail : tail - head;
    }

    @Override
    public boolean offer(Integer integer) {
        Objects.requireNonNull(integer);
        if (isFull()) {
            if (!extendValues()) {
                return false;
            }
        }
        values[tail] = integer;
        tail = (tail + 1) % values.length;
        return true;
    }

    @Override
    public Integer poll() {
        if (isEmpty()) {
            return null;
        } else {
            var element = values[head];
            head = (head + 1) % values.length;
            return element;
        }
    }

    @Override
    public Integer peek() {
        return !isEmpty() ? values[head] : null;
    }

    @Override
    public void clear() {
        tail = 0;
        head = 0;
    }

    private boolean isFull() {
        return size() == values.length - 1;
    }

    private boolean extendValues() {
        ArrayQueueImpl newQueue;
        try {
            int newSize = Math.multiplyExact(values.length, 2);
            newQueue = new ArrayQueueImpl(newSize);
        } catch (ArithmeticException e) {
            return false;
        }
        Integer value;
        while ((value = poll()) != null) {
            newQueue.add(value);
        }
        head = newQueue.head;
        tail = newQueue.tail;
        values = newQueue.values;
        return true;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Integer> {

        private int current = head;

        @Override
        public boolean hasNext() {
            return tail != current;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                return null;
            }
            var next = values[current];
            current = (current + 1) % values.length;
            return next;
        }
    }
}