package queue.impl;

import java.util.*;

public class ArrayQueueImpl extends AbstractQueue<Integer> {
    private static final int INITIAL_ARRAY_SIZE = 16;
    private int push_idx = 0;
    private Integer[] array;

    public ArrayQueueImpl() {
        array = new Integer[INITIAL_ARRAY_SIZE];
    }

    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int idx = push_idx;

            public boolean hasNext() {
                return idx != push_idx;
            }

            public Integer next() {
                if (idx == push_idx) {
                    throw new NoSuchElementException("No such element");
                }

                return array[idx++];
            }
        };
    }

    public int size() {
        return push_idx;
    }

    public boolean add(Integer e) {
        if (push_idx == array.length) {
            Integer[] new_array = new Integer[2 * array.length];

            System.arraycopy(array, 0, new_array, 0, array.length);
            push_idx = array.length;
            array = new_array;
        }

        array[push_idx++] = e;
        return true;
    }

    public boolean offer(Integer e) {
        return add(e);
    }

    public Integer remove() {
        if (push_idx == 0) {
            throw new NoSuchElementException("Queue is empty");
        }

        Integer e = array[0];

        for (int i = 0; i < push_idx - 1; i++) {
            array[i] = array[i + 1];
        }

        push_idx--;
        return e;
    }

    public Integer poll() {
        try {
            return remove();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public Integer element() {
        if (push_idx == 0) {
            throw new NoSuchElementException("Queue is empty");
        }

        return array[0];
    }

    public Integer peek() {
        try {
            return element();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
