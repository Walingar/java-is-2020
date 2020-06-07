package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public class QueueLinkedImpl extends AbstractQueue<Integer> implements Queue<Integer> {
    private int size;
    private Node head;
    private Node tail;

    QueueLinkedImpl() {
        head = null;
        size = 0;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new QueueLinkedIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer element) {
        Node node = new Node(null, element);
        if (head == null) {
            head = node;
            tail = head;
        } else {
            tail.setNext(node);
            tail = node;
        }
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        Integer value = head.value;
        head = head.getNext();
        size--;
        return value;
    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return head.getValue();
    }

    private static class Node {
        private Node next;
        private Integer value;

        Node(Node next, Integer value) {
            this.next = next;
            this.value = value;
        }

        Integer getValue() {
            return value;
        }

        Node getNext() {
            return next;
        }

        void setNext(Node next) {
            this.next = next;
        }
    }

    private class QueueLinkedIterator implements Iterator<Integer> {
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