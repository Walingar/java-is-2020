package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;

public class ArrayQueueImpl extends AbstractQueue<Integer> {

    int[] array = new int[20];
    int sizeArray = 0;
    int head = 0;

    @Override
    public boolean offer(Integer integer) {
        if (array.length == sizeArray + head) {
            int[] newQueue = new int[array.length * 2];
            System.arraycopy(array, head, newQueue, 0, sizeArray);
            array = newQueue;
            head = 0;
        }
        array[head + sizeArray] = integer;
        sizeArray++;
        return true;
    }

    @Override
    public Integer poll() {
        if (sizeArray > 0) {
            int first = array[head++];
            if (array.length - array.length / 3 >= --sizeArray) {
                int[] newMas = new int[array.length - array.length / 4];
                System.arraycopy(array, head, newMas, 0, sizeArray);
                array = newMas;
                head = 0;
            }
            return first;
        }
        return null;
    }

    @Override
    public Integer peek() {
        return sizeArray > 0 ? array[head] : null;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new ArrayIterator();
    }

    @Override
    public int size() {
        return sizeArray;
    }

    private class ArrayIterator implements Iterator<Integer> {

        int currentIndex = head;

        @Override
        public boolean hasNext() {
            return currentIndex < sizeArray + head;
        }

        @Override
        public Integer next() {
            return array[currentIndex++];
        }
    }
}
