package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public class ArrayQueueFactory {
    public static Queue<Integer> getInstance() {
        var queueInts = new AbstractQueue<Integer>() {

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
                    System.arraycopy(mas, 1, mas, 0, sizeMas - 1);
                    sizeMas--;
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
        };
        return queueInts;
    }
}