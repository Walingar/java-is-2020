package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;

public class QueueLinkedImpl extends AbstractQueue<Integer> {

    private int size = 0;
    private Item head = null;
    private Item tail = null;

    private static class LinkedQueueIterator implements Iterator<Item> {
        Item current;

        LinkedQueueIterator(Item head) {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current.getNext() != null;
        }

        @Override
        public Item next() {
            return current.getNext();
        }
    }

    @Override
    public Iterator iterator() {
        return new LinkedQueueIterator(head);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer Item) {
        Item newItem = new Item(null, Item);
        if (head == null) {
            head = newItem;
            tail = head;
        } else {
            tail.setNext(newItem);
            tail = newItem;
        }
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        Integer value = head.getValue();
        head = head.getNext();
        size--;
        return value;
    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return head.getValue();
    }

    private static class Item {
        private Integer value;
        private Item nextItem;

        Item(Item next, int value) {
            this.nextItem = next;
            this.value = value;
        }

        Integer getValue() {
            return value;
        }

        Item getNext() {
            return nextItem;
        }

        void setNext(Item next) {
            this.nextItem = next;
        }
    }
}