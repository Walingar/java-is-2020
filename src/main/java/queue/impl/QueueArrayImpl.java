package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.*;

import static java.lang.Math.min;

public class QueueArrayImpl implements Queue {
    private Integer[] queue;
    private int capacity;
    private int capacityMultiplier;
    private int size;
    private int begin;
    private int end;

    private class QueueArrayIterator implements Iterator {

        private int currentElementIndex;

        QueueArrayIterator() {
            currentElementIndex = begin - 1;
        }

        @Override
        public boolean hasNext() {
            return isIndexInside(currentElementIndex + 1);
        }

        @Override
        public Object next() {
            currentElementIndex++;
            return getElementAt(currentElementIndex);
        }
    }

    QueueArrayImpl() {
        capacity = 1;
        capacityMultiplier = 2;
        queue = new Integer[capacity];
        size = 0;
        begin = 0;
        end = 0;
    }

    private void rearrangeQueue(Set<Integer> unused) {
        if (unused == null) {
            unused = new HashSet<>();
        }
        if (begin == 0 && unused.isEmpty()) {
            return;
        }
        Integer[] newQueue = new Integer[size];
        end = 0;
        for (int i = 0; i < size; i++) {
            if (!unused.contains(begin + i)) {
                newQueue[i] = queue[begin + i];
                end++;
            }
        }
        begin = 0;
        arrayCopy(newQueue, queue);
    }

    boolean isIndexInside(int index) {
        return begin <= index && index < end;
    }

    int getElementAt(int index) {
        if (isIndexInside(index)) {
            return queue[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private void arrayCopy(Object[] from, Object[] to) {
        for (int i = 0; i < min(from.length, to.length); i++) {
            to[i] = from[i];
        }
    }

    private void reallocateMemory() {
        rearrangeQueue(null);
        Integer[] temp = new Integer[capacity];
        arrayCopy(queue, temp);
        capacity *= capacityMultiplier;
        queue = new Integer[capacity];
        arrayCopy(temp, queue);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = begin; i < end; i++) {
            if (queue[i] == null && o == null || queue[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    @Override
    public Iterator iterator() {
        return new QueueArrayIterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        Integer[] queueArr = new Integer[size];
        rearrangeQueue(null);
        arrayCopy(queue, queueArr);
        return queueArr;
    }

    @NotNull
    @Override
    public Object[] toArray(@NotNull Object[] objects) {
        if (objects.length >= size) {
            arrayCopy(queue, objects);
        }
        return toArray();
    }

    @Override
    public boolean add(Object o) {
        if (end == capacity) {
            reallocateMemory();
        }
        queue[end] = (Integer) o;
        size++;
        end++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Set<Integer> toRemove = new HashSet<>();
        for (int i = begin; i < end; ++i) {
            Integer element = queue[i];
            if (o == null && element == null || element.equals(o)) {
                toRemove.add(i);
                break;
            }
        }
        rearrangeQueue(toRemove);
        return !toRemove.isEmpty();
    }

    @Override
    public boolean addAll(@NotNull Collection collection) {
        collection.forEach(this::add);
        return true;
    }

    @Override
    public void clear() {
        size = 0;
        begin = 0;
        end = 0;
    }

    @Override
    public boolean retainAll(@NotNull Collection collection) {
        Set<Integer> toRemove = new HashSet<>();
        for (int i = begin; i < end; i++) {
            if (!collection.contains(queue[i])) {
                toRemove.add(i);
            }
        }
        rearrangeQueue(toRemove);
        return !toRemove.isEmpty();
    }

    @Override
    public boolean removeAll(@NotNull Collection collection) {
        Set<Integer> toRemove = new HashSet<>();
        for (int i = begin; i < end; i++) {
            if (collection.contains(queue[i])) {
                toRemove.add(i);
            }
        }
        rearrangeQueue(toRemove);
        return !toRemove.isEmpty();
    }

    @Override
    public boolean containsAll(@NotNull Collection collection) {
        for (var element : collection) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean offer(Object o) {
        if (size == capacity) {
            return false;
        }
        add(o);
        return true;
    }

    @Override
    public Object remove() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        size--;
        return queue[begin++];
    }

    @Override
    public Object poll() {
        if (size == 0) {
            return null;
        }
        size--;
        return queue[begin++];
    }

    @Override
    public Object element() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return queue[begin];
    }

    @Override
    public Object peek() {
        if (size() == 0) {
            return null;
        }
        return queue[begin];
    }
}
