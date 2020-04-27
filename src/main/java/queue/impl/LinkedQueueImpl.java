package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class LinkedQueueImpl<T> extends AbstractQueue<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public Iterator<T> iterator() {
        return new LinkedQueueIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(T t) {
        if (head == null) {
            head = wrap(t);
            tail = head;
        } else {
            tail.nextNode = wrap(t);
            tail = tail.nextNode;
        }

        size++;
        return true;
    }

    @Override
    public T poll() {
        if (size == 0) {
            return null;
        }

        var result = head.value;
        head = head.nextNode;
        size--;
        return result;
    }

    @Override
    public T peek() {
        if (size == 0) {
            return null;
        }

        return head.value;
    }

    private Node<T> wrap(T value) {
        return new Node<>(value);
    }

    private class LinkedQueueIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }

    private static class Node<T> {
        private final T value;

        public Node<T> nextNode;

        Node(T value) {
            this.value = value;
        }
    }
}
