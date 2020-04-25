package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Objects;

public class LinkedQueue extends AbstractQueue<Integer> {

    private Node head;
    private Node tail;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public Integer peek() {
        return head == null ? null : head.value;
    }

    @Override
    public Integer poll() {
        if (head == null) {
            return null;
        }
        int value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    @Override
    public boolean offer(Integer element) {
        Objects.requireNonNull(element);
        Node node = new Node(element);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new LinkedQueueIterator();
    }

    private static class Node {
        private final int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    private class LinkedQueueIterator implements Iterator<Integer> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            if (current == null) {
                return null;
            }
            int value = current.value;
            current = current.next;
            return value;
        }
    }
}
