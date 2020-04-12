package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public class LinkedQueue  extends AbstractQueue<Integer> implements Queue<Integer>, Iterable<Integer> {

    private Node putNode;

    private Node pullNode;

    private int size = 0;

    public LinkedQueue() {
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private Node current = pullNode;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Integer next() {
                int result = current.getValue();
                current = current.getNext();
                return result;
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        Node nextNode = new Node(integer);
        if(putNode != null) {
            putNode.setNext(nextNode);
        }
        if (pullNode == null) {
            pullNode = nextNode;
        }
        putNode = nextNode;
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (pullNode == null) {
            return null;
        }
        int result = pullNode.getValue();
        pullNode = pullNode.getNext();
        size--;
        return result;
    }

    @Override
    public Integer peek() {
        return pullNode != null ? pullNode.getValue() : null;
    }

    private static final class Node {

        private Node next;

        private final int value;

        public Node(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public int getValue() {
            return value;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
