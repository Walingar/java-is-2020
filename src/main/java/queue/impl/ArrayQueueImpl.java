package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;

public class ArrayQueueImpl extends AbstractQueue<Integer> {

    int[] array = new int[20];
    int sizeMas = 0;
    int head = 0;

    @Override
    public boolean offer(Integer integer) {
        if (array.length == sizeMas+head) {
            int[] newQueue = new int[array.length * 2];
            System.arraycopy(array, head, newQueue, 0, sizeMas);
            array = newQueue;
            head = 0;
        }
        array[head+sizeMas] = integer;
        sizeMas++;
        return true;
    }

    @Override
    public Integer poll() {
        if (sizeMas > 0) {
            int temp = array[head++];
            if (array.length - array.length / 3 >= --sizeMas) {
                int[] newMas = new int[array.length - array.length / 4];
                System.arraycopy(array, head, newMas, 0, sizeMas);
                array = newMas;
                head = 0;
            }
            return temp;
        }
        return null;
    }

    @Override
    public Integer peek() {
        return sizeMas > 0 ? array[head] : null;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new ArrayIterator();
    }

    @Override
    public int size() {
        return sizeMas;
    }

    private class ArrayIterator implements Iterator<Integer> {

        int currentIndex = head;

        @Override
        public boolean hasNext() {
            return currentIndex < sizeMas+head;
        }

        @Override
        public Integer next() {
            return array[currentIndex++];
        }
    }
}
