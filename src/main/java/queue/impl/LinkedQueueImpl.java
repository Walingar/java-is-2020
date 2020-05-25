package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedQueueImpl<T> extends AbstractQueue<T> {

    private QueueElement<T> head = null;
    private QueueElement<T> tail = null;
    private int size = 0;

    @Override
    public Iterator<T> iterator() {
        return new LinkedIterator<>(head);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(T value) {
        var newElement = new QueueElement<>(value, null);

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
    public T poll() {
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
    public T peek() {
        return head == null ? null : head.value;
    }

    private static class QueueElement<T> {
        private final T value;
        private QueueElement<T> next;

        QueueElement(T value, QueueElement<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private static class LinkedIterator<T> implements Iterator<T> {
        private QueueElement<T> currentQueueElement;

        LinkedIterator(QueueElement<T> head) {
            currentQueueElement = head;
        }

        @Override
        public boolean hasNext() {
            return currentQueueElement != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var value = currentQueueElement.value;
            currentQueueElement = currentQueueElement.next;
            return value;
        }
    }
}