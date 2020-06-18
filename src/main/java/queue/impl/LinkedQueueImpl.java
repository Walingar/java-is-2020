package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LinkedQueueImpl extends BaseQueue {
    private Node head;
    private Node last;

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new LinkedQueueIterator();
    }

    @Override
    public boolean add(Integer newValue) {
        Node New = new Node(newValue, null);
        if (this.size == 0) {
            head = new Node(newValue, null);
            last = head;
        } else {
            this.last.setNext(New);
            this.last = New;
        }
        size++;
        return false;
    }

    @Override
    public boolean offer(Integer integer) {
        this.add(integer);
        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        int res = (int) head.getValue();
        head = head.getNext();
        size--;
        return res;
    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return (int) head.getValue();
    }

    public static class Node {
        private final Integer value;
        private Node next;

        public Node(Integer value, Node next) {
            assert value != null;
            this.value = value;
            this.next = next;
        }

        public Node getNext() {
            return this.next;
        }

        public Object getValue() {
            return this.value;
        }

        public void setNext(Node x) {
            this.next = x;
        }
    }

    private static class LinkedQueueIterator implements Iterator<Integer> {
        private Node index;

        @Override
        public boolean hasNext() {
            return index.getNext() != null;
        }

        @Override
        public Integer next() {
            Object value = index.getValue();
            index = index.getNext();
            return (int) value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}