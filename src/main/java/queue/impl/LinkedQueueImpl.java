package queue.impl;

import java.util.*;

public class LinkedQueueImpl extends AbstractQueue<Integer> {
    private static final class Node {
        public Node next;
        public Integer value;

        Node(Integer e) {
            this.next = null;
            this.value = e;
        }
    }

    private Node push_node;
    private Node pull_node;
    private int length = 0;

    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private Node current = pull_node;

            public boolean hasNext() {
                return current != null;
            }

            public Integer next() {
                if (current == null) {
                    throw new NoSuchElementException("No such element");
                }

                Integer result = current.value;
                current = current.next;
                return result;
            }
        };
    }

    public int size() {
        return length;
    }

    public boolean add(Integer e) {
        return offer(e);
    }

    public boolean offer(Integer e) {
        Node new_node = new Node(e);

        if (pull_node == null) {
            push_node = new_node;
            pull_node = new_node;
        } else {
            push_node.next = new_node;
            push_node = push_node.next;
        }

        length++;
        return true;
    }

    public Integer remove() {
        if (pull_node == null) {
            throw new NoSuchElementException("Queue is empty");
        }

        Integer e = pull_node.value;
        pull_node = pull_node.next;
        length--;
        return e;
    }

    public Integer poll() {
        try {
            return remove();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public Integer element() {
        if (pull_node == null) {
            throw new NoSuchElementException("Queue is empty");
        }

        return pull_node.value;
    }

    public Integer peek() {
        try {
            return element();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}