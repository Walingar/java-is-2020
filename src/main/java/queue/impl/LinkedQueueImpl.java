package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueueImpl<T> extends AbstractQueue<T> {

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    private Node<T> box(T t) {
        return new Node<>(t);
    }

    @Override
    @NotNull
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
            tail = box(t);
            head = tail;
        } else {
            tail.next = box(t);
            tail = tail.next;
        }
        size++;
        return true;
    }

    @Override
    public T poll() {
        if (size == 0) {
            return null;
        }
        var value = head.value;
        head = head.next;
        size--;
        return value;
    }

    @Override
    public T peek() {
        if (size == 0) {
            return null;
        }
        return head.value;
    }

    private static class Node<T> {
        private final T value;

        public Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    private class LinkedQueueIterator implements Iterator<T> {
        private Node<T> iteratorHead = head;

        @Override
        public boolean hasNext() {
            return iteratorHead != null;
        }

        @Override
        public T next() {
            if (iteratorHead == null) {
                throw new NoSuchElementException("");
            }
            var value = iteratorHead.value;
            iteratorHead = iteratorHead.next;
            return value;
        }
    }

}
