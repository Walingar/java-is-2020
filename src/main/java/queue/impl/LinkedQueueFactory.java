package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class LinkedQueueFactory {
    public static Queue<Integer> getInstance() {
        return new QueueLinkedImpl();
    }

    public static class QueueLinkedImpl implements Queue{

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NotNull
        @Override
        public Iterator iterator() {
            return null;
        }

        @NotNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NotNull
        @Override
        public Object[] toArray(@NotNull Object[] objects) {
            return new Object[0];
        }

        @Override
        public boolean add(Object o) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean addAll(@NotNull Collection collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean retainAll(@NotNull Collection collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NotNull Collection collection) {
            return false;
        }

        @Override
        public boolean containsAll(@NotNull Collection collection) {
            return false;
        }

        @Override
        public boolean offer(Object o) {
            return false;
        }

        @Override
        public Object remove() {
            return null;
        }

        @Override
        public Object poll() {
            return null;
        }

        @Override
        public Object element() {
            return null;
        }

        @Override
        public Object peek() {
            return null;
        }
    }
}