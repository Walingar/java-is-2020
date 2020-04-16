package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public class LinkedQueueFactory {
    public static Queue<Integer> getInstance() {
        var linkedQueueInt = new AbstractQueue<Integer>() {

            private Link first = null;
            private Link last = null;

            @Override
            public boolean offer(Integer integer) {
                if (first == null) {
                    first = new Link(integer);
                    last = first;
                } else {
                    last.next = new Link(integer);
                    last = last.next;
                }
                return true;
            }

            @Override
            public Integer poll() {
                if (first != null) {
                    int item = first.item;
                    first = first.next;
                    if (first == null) {
                        last = null;
                    }
                    return item;
                }
                return null;
            }

            @Override
            public Integer peek() {
                if (first != null) {
                    return first.item;
                }
                return null;
            }

            @Override
            public Iterator<Integer> iterator() {
                var iterator = new Iterator<Integer>() {
                    Link current = first;

                    @Override
                    public boolean hasNext() {
                        if (current != null) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public Integer next() {
                        Integer result = current.item;
                        current = current.next;
                        return result;
                    }
                };
                return iterator;
            }

            @Override
            public int size() {
                if (first != null) {
                    int count = 1;
                    Link current = first;
                    while ((current = current.next) != null) {
                        count++;
                    }
                    return count;
                }
                return 0;
            }
        };
        return linkedQueueInt;
    }
}