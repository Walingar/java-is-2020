package queue.impl;

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

    @Override
    public Iterator<Integer> iterator() {
        return null;
    }

    private static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }
}
