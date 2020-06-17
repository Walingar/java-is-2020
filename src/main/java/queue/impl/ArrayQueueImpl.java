package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;

public class ArrayQueueImpl extends AbstractQueue<Integer> {
    private int[] arr = new int[5];
    private int lastIndex = 0;
    private int firstIndex = 0;

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new MyIterator();
    }

    @Override
    public int size() {
        return lastIndex - firstIndex;
    }

    @Override
    public boolean offer(Integer integer) {
        if (lastIndex == arr.length) {
            int[] newMas = new int[lastIndex * 2];
            System.arraycopy(arr, 0, newMas, 0, arr.length);
            arr = newMas;
        }
        arr[lastIndex] = integer;
        lastIndex++;
        return true;
    }

    @Override
    public Integer poll() {
        if (lastIndex - firstIndex > 0) {
            Integer polledElement = arr[firstIndex];
            firstIndex++;
            if (lastIndex < arr.length / 2) {
                int[] newMas = new int[lastIndex + 1];
                System.arraycopy(arr, 0, newMas, 0, lastIndex);
                arr = newMas;
            }
            clearTrash();
            return polledElement;
        } else {
            return null;
        }
    }

    @Override
    public Integer peek() {
        if (lastIndex - firstIndex == 0) {
            return null;
        } else {
            return arr[firstIndex];
        }
    }

    private void clearTrash() {
        if (firstIndex != 0 && firstIndex == lastIndex - firstIndex) {
            int[] newMas = new int[lastIndex - firstIndex];
            System.arraycopy(arr, firstIndex, newMas, 0, lastIndex - firstIndex);
            arr = newMas;
            lastIndex -= firstIndex;
            firstIndex = 0;
        }
    }

    private class MyIterator implements Iterator<Integer> {
        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < lastIndex;
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                currentIndex++;
                return arr[currentIndex];
            } else {
                return null;
            }
        }
    }
}
