package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;

public class LinkedQueueImpl extends AbstractQueue<Integer> {
    private QueueNodeImpl firstElement;
    private QueueNodeImpl lastElement;

    private int currentSize;

    public LinkedQueueImpl() {
        firstElement = null;
        lastElement = null;
        currentSize = 0;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private QueueNodeImpl currentNode = firstElement;

            @Override
            public boolean hasNext() {
                return currentNode != lastElement;
            }

            @Override
            public Integer next() {
                Integer valueToReturn = currentNode.getValue();
                currentNode = currentNode.getNext();

                return valueToReturn;
            }
        };
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean offer(Integer integer) {
        QueueNodeImpl newNode = new QueueNodeImpl(integer);
        if (firstElement == null) {
            firstElement = newNode;
            lastElement = newNode;
            lastElement.setPrevious(firstElement);
        } else {
            newNode.setPrevious(lastElement);

            lastElement.setNext(newNode);
            lastElement = newNode;
        }

        currentSize++;

        return true;
    }

    @Override
    public Integer poll() {
        if (currentSize == 0)
            return null;

        Integer valueToReturn = firstElement.getValue();
        firstElement = firstElement.getNext();

        currentSize--;

        return valueToReturn;
    }

    @Override
    public Integer peek() {
        if (currentSize == 0)
            return null;

        return firstElement.getValue();
    }

    private class QueueNodeImpl {
        private Integer value;

        private QueueNodeImpl next;
        private QueueNodeImpl previous;

        public QueueNodeImpl(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public QueueNodeImpl getNext() {
            return next;
        }

        public QueueNodeImpl getPrevious() {
            return previous;
        }

        public void setNext(QueueNodeImpl next) {
            this.next = next;
        }

        public void setPrevious(QueueNodeImpl previous) {
            this.previous = previous;
        }
    }
}
