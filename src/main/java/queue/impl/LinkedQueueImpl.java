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
        return new MyIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(T value) {
        var newNode = new Node<>(value);

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

        Node(T value) {
            this.value = value;
        }
    }

    private class MyIterator implements Iterator<T> {
        private Node<T> currentNode = head;

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
