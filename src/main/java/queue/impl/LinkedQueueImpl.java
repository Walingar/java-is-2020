package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;

public class LinkedQueueImpl extends AbstractQueue<Integer> {
    private int size;
    private QueueNode head;
    private QueueNode tail;


    @Override
    public Iterator<Integer> iterator() {
        return new LinkedQueueIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        var node = new QueueNode(integer);
        if (head == null) {
            head = node;
        } else if (tail == null) {
            tail = node;
            head.next = tail;
        } else {
            tail.next = node;
            tail = node;
        }
        size += 1;

        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        } else {
            var data = head.getData();
            head = head.next;
            size -= 1;
            return data;
        }
    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        } else {
            return head.getData();
        }
    }

    private class QueueNode {
        private final Integer data;

        public QueueNode next;

        public QueueNode(Integer data) {
            this.data = data;
        }

        public Integer getData() {
            return data;
        }
    }

    private class LinkedQueueIterator implements Iterator<Integer> {
        private QueueNode currentNode = head;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Integer next() {
            if (currentNode == null) {
                return null;
            }
            var data = currentNode.getData();
            currentNode = currentNode.next;
            return data;
        }
    }
}
