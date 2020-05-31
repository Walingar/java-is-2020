package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedQueueImpl extends AbstractQueue<Integer> {

    private QueueElement head = null;
    private QueueElement tail = null;
    private int size = 0;

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
        var newElement = new QueueElement(value, null);

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

    private static class QueueElement {
        private final Integer value;
        private QueueElement next;

        QueueElement(Integer value, QueueElement next) {
            this.value = value;
            this.next = next;
        }
    }

    private static class QueueIterator implements Iterator<Integer> {

        private QueueElement currentQueueElement;

        QueueIterator(QueueElement head) {
            currentQueueElement = head;
        }

        @Override
        public boolean hasNext() {
            return currentQueueElement != null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var value = currentQueueElement.value;
            currentQueueElement = currentQueueElement.next;
            return value;
        }
    }
}