package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;

public class ArrayQueueImpl extends AbstractQueue<Integer> {

    int[] mas = new int[20];
    int sizeMas = 0;

    @Override
    public boolean offer(Integer integer) {
        if (mas.length == sizeMas) {
            int[] newMas = new int[mas.length * 2];
            System.arraycopy(mas, 0, newMas, 0, sizeMas);
            mas = newMas;
        }
        mas[sizeMas++] = integer;
        return true;
    }

    @Override
    public Integer poll() {
        if (sizeMas > 0) {
            int temp = mas[0];
            if (mas.length - mas.length/3 >= --sizeMas) {
                int[] newMas = new int[mas.length-mas.length/4];
                System.arraycopy(mas, 1, newMas, 0, sizeMas);
                mas = newMas;
            } else {
                System.arraycopy(mas, 1, mas, 0, sizeMas);
            }
            return temp;
        }
        return null;
    }

    @Override
    public Integer peek() {
        if (sizeMas > 0) {
            return mas[0];
        } else {
            return null;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        Iterator<Integer> iterator = new Iterator<>() {

            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < sizeMas;
            }

            @Override
            public Integer next() {
                return mas[currentIndex++];
            }
        };
        return iterator;
    }

    @Override
    public int size() {
        return sizeMas;
    }
}
