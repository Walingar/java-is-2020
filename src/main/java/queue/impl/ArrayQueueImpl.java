package queue.impl;

import java.util.*;

public class ArrayQueueImpl extends AbstractQueue<Integer> {
    private static final int INITIAL_ARRAY_SIZE = 16;
    private int pushIdx = 0;
    private int popIdx = 0;
    private Integer[] array;

    public ArrayQueueImpl() {
        array = new Integer[INITIAL_ARRAY_SIZE];
    }

    private class ArrayQueueIterator implements Iterator<Integer> {
        private int idx = pushIdx;

        public boolean hasNext() {
            return idx != pushIdx;
        }

        public Integer next() {
            if (idx == pushIdx) {
                throw new NoSuchElementException("No such element");
            }

            return array[idx++];
        }
    }

    public Iterator<Integer> iterator() {
        return new ArrayQueueIterator();
    }

    public int size() {
        return pushIdx - popIdx;
    }

    public boolean add(Integer e) {
        if (pushIdx == array.length) {
            if (popIdx > 0) {
                System.arraycopy(array, popIdx, array, 0, array.length - popIdx);
                pushIdx -= popIdx;
                popIdx = 0;
            } else {
                array = Arrays.copyOf(array, 2 * array.length);
            }
        }

        array[pushIdx++] = e;
        return true;
    }

    public boolean offer(Integer e) {
        return add(e);
    }

    public Integer remove() {
        if (size() == 0) {
            throw new NoSuchElementException("Queue is empty");
        }

        return array[popIdx++];
    }

    public Integer poll() {
        try {
            return remove();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public Integer element() {
        if (size() == 0) {
            throw new NoSuchElementException("Queue is empty");
        }

        return array[popIdx];
    }

    public Integer peek() {
        try {
            return element();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
