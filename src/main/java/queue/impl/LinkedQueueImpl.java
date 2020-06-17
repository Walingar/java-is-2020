package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;

public class LinkedQueueImpl extends AbstractQueue<Integer> {
    private MyNode head;
    private MyNode tail;
    private int size = 0;

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new MyIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        if (size == 0) {
            head = new MyNode(integer);
            tail = head;
        } else {
            MyNode newNode = new MyNode(integer);
            tail.prevNode = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        Integer result = head.value;
        head = head.prevNode;
        size--;
        return result;
    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        } else {
            return head.value;
        }
    }

    public static class MyNode {
        private final int value;
        private MyNode prevNode;

        MyNode(int value) {
            this.value = value;
            prevNode = null;
        }
    }

    private class MyIterator implements Iterator<Integer> {
        MyNode currentNode = head;

        @Override
        public boolean hasNext() {
            return currentNode.prevNode != null;
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                currentNode = currentNode.prevNode;
                return currentNode.value;
            } else {
                return null;
            }
        }
    }
}
