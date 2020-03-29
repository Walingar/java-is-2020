package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LinkedListIntegerImpl extends AbstractList implements List {

    private static class Node {
        private Node next;
        private Node previous;
        private Integer value;
        private int index;

        Node(Node next, Node previous, Integer value) {
            this.next = next;
            this.previous = previous;
            this.value = value;
            if (previous != null) {
                index = previous.getIndex() + 1;
            } else if (next != null) {
                index = next.getIndex() - 1;
            } else {
                index = 0;
            }
        }

        Node(Node next, Node previous, Integer value, int index) {
            this.next = next;
            this.previous = previous;
            this.value = value;
            this.index = index;
        }

        Integer getValue() {
            return value;
        }

        void setValue(Integer value) {
            this.value = value;
        }

        Node getNext() {
            return next;
        }

        void setNext(Node next) {
            this.next = next;
        }

        Node getPrevious() {
            return previous;
        }

        void setPrevious(Node previous) {
            this.previous = previous;
        }

        int getIndex() {
            return index;
        }

        void setIndex(int index) {
            this.index = index;
        }

    }

    private static class LinkedListIterator implements ListIterator {

        private Node currentNode;

        LinkedListIterator(Node start) {
            currentNode = start;
        }

        @Override
        public boolean hasNext() {
            return currentNode.next != null;
        }

        @Override
        public Object next() {
            currentNode = currentNode.next;
            return currentNode;
        }

        @Override
        public boolean hasPrevious() {
            return currentNode.getPrevious() != null;
        }

        @Override
        public Object previous() {
            currentNode = currentNode.getPrevious();
            return currentNode;
        }

        @Override
        public int nextIndex() {
            return currentNode.getIndex() + 1;
        }

        @Override
        public int previousIndex() {
            return currentNode.getIndex() - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Object o) {
            throw new UnsupportedOperationException();
        }

    }

    private Node head;
    private Node tail;
    private int size;

    LinkedListIntegerImpl() {
        super();
        clear();
    }

    private boolean checkEquals(Object a, Object b) {
        return a == null && b == null || b.equals(a);
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node getNode(int index) {
        Node needNode = head;
        for (int i = 0; i < index; ++i) {
            needNode = needNode.next;
        }
        return needNode;
    }

    private void updateIndexes(Node from, Node to, int startIndex) {
        Node cur = from;
        while (cur != to) {
            if (cur == null) {
                throw new NullPointerException();
            }
            cur.setIndex(startIndex);
            cur = cur.getNext();
            startIndex++;
        }
    }

    @Override
    public Object get(int i) {
        checkBounds(i);
        return getNode(i).getValue();
    }

    @Override
    public Object set(int i, Object o) {
        checkBounds(i);
        Node node = getNode(i);
        Integer previousValue = node.getValue();
        node.setValue((Integer) o);
        return previousValue;
    }

    @Override
    public void add(int index, Object o) {
        if (size == 0) {
            add(o);
            return;
        }
        checkBounds(index);
        Node cur = head;
        Node newNode = new Node(null, null, (Integer) o, index);
        for (int i = 0; i < index; i++) {
            cur = cur.getNext();
        }
        newNode.setPrevious(cur.getPrevious());
        newNode.setNext(cur);
        cur.setPrevious(newNode);
        size++;
        updateIndexes(cur, tail, index + 1);
    }

    private void removeNode(Node toRemove, int nodeIndex) {
        if (size == 1) {
            clear();
            return;
        }
        size--;
        if (toRemove == head) {
            head = head.getNext();
            updateIndexes(head, tail, nodeIndex);
            return;
        }
        if (toRemove == tail) {
            tail = tail.getPrevious();
            return;
        }
        updateIndexes(toRemove.getNext(), tail, nodeIndex);
        toRemove.getPrevious().setNext(toRemove.getNext());
        toRemove.getNext().setPrevious(toRemove.getPrevious());
    }

    @Override
    public Object remove(int i) {
        checkBounds(i);
        Node toRemove = getNode(i);
        removeNode(toRemove, i);
        return toRemove.getValue();
    }

    @NotNull
    @Override
    public ListIterator listIterator() {
        return new LinkedListIterator(head);
    }

    @NotNull
    @Override
    public ListIterator listIterator(int i) {
        checkBounds(i);
        return new LinkedListIterator(getNode(i));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @NotNull
    @Override
    public Iterator iterator() {
        return new LinkedListIterator(head);
    }

    @Override
    public boolean add(Object o) {
        Node newNode = new Node(null, null, (Integer) o);
        if (size == 0) {
            head = newNode;
            tail = head;
        } else {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        boolean removed = false;
        Node cur = head;
        int index = 0;
        while (cur != null && !checkEquals(cur.getValue(), o)) {
            cur = cur.getNext();
            index++;
        }
        if (cur != null) {
            removeNode(cur, index);
            removed = true;
        }
        return removed;
    }

    @Override
    public boolean retainAll(@NotNull Collection collection) {
        Set<Integer> toRemove = new HashSet<>();
        Node cur = head;
        while (cur != null) {
            if (!collection.contains(cur.getValue())) {
                toRemove.add(cur.getValue());
            }
        }
        return removeAll(toRemove);
    }

    @Override
    public boolean removeAll(@NotNull Collection collection) {
        boolean modified = false;
        for (var e : collection) {
            while (contains(e)) {
                modified = true;
                remove(e);
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

}
