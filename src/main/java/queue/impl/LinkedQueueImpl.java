package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;

public class LinkedQueueImpl extends AbstractQueue<Integer> {

    private Node head;
    private Node tail;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        var element = tail;
        tail = new Node(integer);
        if (size() == 0) {
            head = tail;
        } else {
            element.next = tail;
        }
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        size--;
        var element = head;
        head = head.next;
        return element.value;
    }

    @Override
    public Integer peek() {
        return head == null ? null : head.value;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new QueueIterator();
    }

    private static class Node {

        private final int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    private class QueueIterator implements Iterator<Integer> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                return null;
            }
            var next = current;
            current = current.next;
            return next.value;
        }
    }
}