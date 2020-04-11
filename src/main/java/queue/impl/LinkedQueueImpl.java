package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueueImpl<T> extends AbstractQueue<T> {

    private int size = 0;
    private Container<T> head = null;
    private Container<T> tail = null;

    private Container<T> box(T t) {
        return new Container<>(t);
    }

    private void bind(Container<T> fst, Container<T> snd) {
        fst.next = snd;
        snd.prev = fst;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Container<T> iteratorHead = head;

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
        };
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
            bind(tail, box(t));
            tail = tail.next;
        }
        size++;
        return true;
    }

    @Override
    public T poll() {
        if (size == 0)
            return null;
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

    private static class Container<T> {
        private final T value;

        public Container<T> next;
        public Container<T> prev;

        Container(T value) {
            this.value = value;
        }
    }

}
