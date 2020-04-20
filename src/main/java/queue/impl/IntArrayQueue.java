package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public final class IntArrayQueue extends AbstractQueue<Integer> implements Queue<Integer> {
    private static final int DEFAULT_INITIAL_CAPACITY = 4;
    private static final double CAPACITY_FACTOR = 1.5;
    private int[] ring;
    private int head = 0;
    private int tail = 0;
    private int size;

    public IntArrayQueue() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public IntArrayQueue(final int capacity) {
        this.ring = new int[capacity];
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int position = head;

            @Override
            public boolean hasNext() {
                return position != tail;
            }

            @Override
            public Integer next() {
                int ret = ring[position];
                position = (position + 1) % ring.length;
                return ret;
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(final Integer integer) {
        if (size == ring.length) {
            int[] newRing = new int[(int) (ring.length * CAPACITY_FACTOR)];
            if (head < tail) {
                System.arraycopy(ring, head, newRing, 0, size);
            } else {
                System.arraycopy(ring, head, newRing, 0, ring.length - head);
                System.arraycopy(ring, 0, newRing, ring.length - head, tail);
            }
            head = 0;
            tail = ring.length;
            ring = newRing;
        }
        ring[tail] = integer;
        tail = (tail + 1) % ring.length;
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        int ret = ring[head];
        head = (head + 1) % ring.length;
        size--;
        return ret;
    }

    @Override
    public void clear() {
        head = tail = 0;
    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return ring[head];
    }
}
