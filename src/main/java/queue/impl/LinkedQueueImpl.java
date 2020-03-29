package queue.impl;

import java.util.*;

public class LinkedQueueImpl<T> extends AbstractQueue<T> {

    private int size = 0;
    private element<T> head = null;
    private element<T> tail = null;

    @Override
    public Iterator<T> iterator() {
        return new LinkedQueueIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(T t) {
        if (size == 0) {
            head = new element<>(t);
            tail = head;
        } else {
            tail.next = new element<>(t);
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

    private static class element<T> {
        private T value;
        private element<T> next;

        element(T value) {
            this.value = value;
            this.next = null;
        }
    }

    private class LinkedQueueIterator implements Iterator<T> {
        element<T> current = head;

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
