package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public final class IntLinkedQueue extends AbstractQueue<Integer> implements Queue<Integer> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Integer next() {
                int ret = current.value;
                current = current.next;
                return ret;
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(final Integer integer) {
        if (head == null) {
            head = tail = new Node(integer);
        } else if (head == tail) {
            tail = new Node(integer);
            head.link(tail);
        } else {
            Node node = new Node(integer);
            tail.link(node);
            tail = node;
        }
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (head == null) {
            return null;
        }
        int ret = head.value;
        size--;
        if (tail == head) {
            head = tail = null;
        } else {
            head = head.next;
        }
        return ret;
    }

    @Override
    public Integer peek() {
        if (head == null) {
            return null;
        }
        return head.value;
    }

    private static final class Node {
        private final int value;
        private Node next;

        public Node(final int value) {
            this.value = value;
        }

        private void link(final Node next) {
            this.next = next;
        }
    }
}
