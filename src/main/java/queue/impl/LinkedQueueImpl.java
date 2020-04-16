package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueueImpl<Integer> extends AbstractQueue<Integer> {
    private Element<Integer> head = null;
    private Element<Integer> tail = null;
    private int size = 0;

    @Override
    public Iterator<Integer> iterator() {
        return new QueueIterator<>(head);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer value) {
        var newElement = new Element<>(value, null);

        if (tail == null) {
            head = newElement;
        } else {
            tail.next = newElement;
        }
        tail = newElement;

        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (head == null) {
            return null;
        }
        var polledValue = head.value;
        var next = head.next;

        if (next == null) {
            tail = null;
        }
        head = next;

        size--;
        return polledValue;
    }

    @Override
    public Integer peek() {
        return head == null ? null : head.value;
    }

    private static class Element<Integer> {
        private final Integer value;
        private Element<Integer> next;

        Element(Integer value, Element<Integer> next) {
            this.value = value;
            this.next = next;
        }
    }

    private static class QueueIterator<Integer> implements Iterator<Integer> {
        private Element<Integer> currentElement;

        QueueIterator(Element<Integer> head) {
            currentElement = head;
        }

        @Override
        public boolean hasNext() {
            return currentElement != null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var value = currentElement.value;
            currentElement = currentElement.next;
            return value;
        }
    }
}
