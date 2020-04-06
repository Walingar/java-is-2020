package queue.impl;

import java.util.*;

public class LinkedQueueImpl<T> extends AbstractQueue<T> {

    private int size = 0;
    private Element<T> head = null;
    private Element<T> tail = null;

    @Override
    public Iterator<T> iterator() {
        return new LinkedQueueIterator<T>(head);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(T t) {
        if (size == 0) {
            head = new Element<>(t);
            tail = head;
        } else {
            tail.next = new Element<>(t);
            tail = tail.next;
        }
        size++;
        return true;
    }

    @Override
    public T poll() {
        if (size == 0) {
            return null;
        }
        size--;
        var oldHead = head.value;
        if (head.next == null) {
            tail = null;
            head = null;
        } else {
            head = head.next;
        }
        return oldHead;
    }

    @Override
    public T peek() {
        return head == null ? null : head.value;
    }

    private static class Element<T> {
        private final T value;
        private Element<T> next;

        Element(T value) {
            this.value = value;
            this.next = null;
        }
    }

    private static class LinkedQueueIterator<T> implements Iterator<T> {
        Element<T> current;

        LinkedQueueIterator(Element<T> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var value = current.value;
            current = current.next;
            return value;
        }
    }
}
