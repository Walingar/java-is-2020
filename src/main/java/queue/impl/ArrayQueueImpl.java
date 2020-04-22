package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;

public class ArrayQueueImpl extends AbstractQueue<Integer> {
    private int[] mas = new int[5];
    private int size = 0;

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex + 1 < size;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    currentIndex++;
                    return mas[currentIndex];
                } else {
                    return null;
                }
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(Integer integer) {
        if (size == mas.length) {
            int[] newMas = new int[size * 2];
            System.arraycopy(mas, 0, newMas, 0, mas.length);
            mas = newMas;
        }
        mas[size] = integer;
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (size > 0) {
            Integer polledElement = mas[0];
            size--;
            if (size < mas.length / 2) {
                int[] newMas = new int[size + 1];
                System.arraycopy(mas, 1, newMas, 0, size);
                mas = newMas;
            } else {
                System.arraycopy(mas, 1, mas, 0, size);
            }
            return polledElement;
        } else {
            System.out.println("Nothing to poll! There are no elements in the queue");
            return null;
        }
    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        } else {
            return mas[0];
        }
    }
}
