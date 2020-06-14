package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;

public class LinkedQueueImpl extends AbstractQueue<Integer> {
    private Link first = null;
    private Link last = null;

    @Override
    public boolean offer(Integer integer) {
        if (first == null) {
            first = new Link(integer);
            last = first;
        } else {
            last.next = new Link(integer);
            last = last.next;
        }
        return true;
    }

    @Override
    public Integer poll() {
        if (first != null) {
            int item = first.item;
            first = first.next;
            if (first == null) {
                last = null;
            }
            return item;
        }
        return null;
    }

    @Override
    public Integer peek() {
        if (first != null) {
            return first.item;
        }
        return null;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new LinkedIterator();
    }

    @Override
    public int size() {
        if (first != null) {
            int count = 1;
            Link current = first;
            while ((current = current.next) != null) {
                count++;
            }
            return count;
        }
        return 0;
    }

    private static class Link {
        private final int item;
        private Link next = null;

        Link(int item) {
            this.item = item;
        }
    }

    private class LinkedIterator implements Iterator<Integer> {
        Link current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            Integer result = current.item;
            current = current.next;
            return result;
        }
    }
}
