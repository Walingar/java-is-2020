package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LinkedQueueImpl extends AbstractQueue<Integer> {
    private Node head;
    private Node tail;
    private int size;

    public LinkedQueueImpl() {
        this.head = null;
        this.size = 0;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new QueueIterator(head);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        if (size == 0) {
            head = new Node(integer);
            tail = head;
        } else {
            tail.next = new Node(integer);
            tail = tail.next;
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
        Integer head = this.head.value;
        if (this.head.next == null) {
            tail = null;
            this.head = null;
        } else {
            this.head = this.head.next;
        }
        return head;
    }

    @Override
    public Integer peek() {
        return head == null ? null : head.value;
    }

    private static class Node {
        private Node next;
        private Integer value;

        Node(Integer value) {
            this.next = null;
            this.value = value;
        }
    }
    private static class QueueIterator implements Iterator<Integer> {
        Node elem;

        QueueIterator(Node elem) {
            this.elem = elem;
        }

        @Override
        public boolean hasNext() {
            return elem.next != null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Integer value = elem.value;
            elem = elem.next;
            return value;
        }
    }

}
