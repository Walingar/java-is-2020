package queue.impl;


import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueueImpl extends AbstractQueue<Integer> {

    private Point<Integer> head = null;
    private Point<Integer> tail = null;
    private int size = 0;

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new QueueIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        var newPoint = new Point<>(integer);

        if (tail == null) {
            head = newPoint;
        } else {
            tail.next = newPoint;
        }
        tail = newPoint;

        ++size;
        return true;
    }

    @Override
    public Integer poll() {
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
    public Integer peek() {
        if (head == null) {
            return null;
        } else {
            return head.value;
        }
    }


    private class QueueIterator implements Iterator<Integer> {
        private Point<Integer> currentPoint = head;

        @Override
        public boolean hasNext() {
            return currentPoint != null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var returnValue = currentPoint.value;
            currentPoint = currentPoint.next;
            return returnValue;
        }
    }

    private static class Point<Integer> {
        private final Integer value;
        private Point<Integer> next;

        Point(Integer value) {
            this.value = value;
        }
    }
}
