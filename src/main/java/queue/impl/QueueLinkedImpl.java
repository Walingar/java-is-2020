package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public class QueueLinkedImpl extends AbstractQueue implements Queue {

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

    private static class QueueLinkedIterator implements Iterator {

        Node current;

        QueueLinkedIterator(Node head) {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current.getNext() != null;
        }

        @Override
        public Object next() {
            current = current.getNext();
            return current;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    QueueLinkedImpl() {
        head = null;
        size = 0;
    }

    @Override
    public Iterator iterator() {
        return new QueueLinkedIterator(head);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Object o) {
        Node newNode = new Node(null, (Integer) o);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public Object poll() {
        if (size == 0) {
            return null;
        }
        Object value = head.value;
        head = head.getNext();
        size--;
        return value;
    }

    @Override
    public Object peek() {
        if (size == 0) {
            return null;
        }
        return head.getValue();
    }
}