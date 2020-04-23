package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueueImpl extends AbstractQueue<Integer> {
    private Element head = null;
    private Element tail = null;
    private int size = 0;

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new QueueIterator(head);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer value) {
        var newElement = new Element(value, null);

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

    private static class Element {
        private final Integer value;
        private Element next;

        Element(Integer value, Element next) {
            this.value = value;
            this.next = next;
        }
    }

    private static class QueueIterator implements Iterator<Integer> {
        private Element currentElement;

        QueueIterator(Element head) {
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
