package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class QueueLinkedImpl implements Queue {

    private List<Integer> queue;

    QueueLinkedImpl() {
        queue = new LinkedListIntegerImpl();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @NotNull
    @Override
    public Iterator iterator() {
        return queue.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @NotNull
    @Override
    public Object[] toArray(@NotNull Object[] objects) {
        return queue.toArray(objects);
    }

    @Override
    public boolean add(Object o) {
        return queue.add((Integer) o);
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean addAll(@NotNull Collection collection) {
        return queue.addAll(collection);
    }

    @Override
    public void clear() {
        queue.clear();
        ;
    }

    @Override
    public boolean retainAll(@NotNull Collection collection) {
        return queue.retainAll(collection);
    }

    @Override
    public boolean removeAll(@NotNull Collection collection) {
        return queue.removeAll(collection);
    }

    @Override
    public boolean containsAll(@NotNull Collection collection) {
        return queue.containsAll(collection);
    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Object remove() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        Integer e = queue.get(0);
        queue.remove(0);
        return e;
    }

    @Override
    public Object poll() {
        if (queue.isEmpty()) {
            return null;
        }
        Integer e = queue.get(0);
        queue.remove(0);
        return e;
    }

    @Override
    public Object element() {
        if (queue.isEmpty()) {
            throw new NoSuchElementException();
        }
        return queue.get(0);
    }

    @Override
    public Object peek() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.get(0);
    }
}