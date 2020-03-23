package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedQueueImpl<T> extends AbstractQueue<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>(head);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(T value) {
        var newNode = new Node<>(value, tail, null);

        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;

        ++size;
        return true;
    }

    @Override
    public T poll() {
        if (head == null) {
            return null;
        }
        var returnValue = head.value;
        var next = head.next;

        if (next == null) {
            tail = null;
        } else {
            next.previous = null;
        }
        head = next;

        --size;
        return returnValue;
    }

    @Override
    public T peek() {
        return head == null ? null : head.value;
    }

    private static class Node<T> {
        private final T value;
        private Node<T> next;
        private Node<T> previous;

        Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private static class MyIterator<T> implements Iterator<T> {
        private Node<T> currentNode;

        MyIterator(Node<T> head) {
            currentNode = head;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var returnValue = currentNode.value;
            currentNode = currentNode.next;
            return returnValue;
        }
    }
}
