package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;

public class ArrayQueue extends AbstractQueue<Integer> implements Queue<Integer> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int QUEUE_INCREASE_NUMBER = 10;
    private static final int QUEUE_DECREASE_NUMBER = 10;

    private int putIndex = 0;

    private int pullIndex = 0;

    private Integer[] elements;

    public ArrayQueue() {
        elements = new Integer[DEFAULT_CAPACITY];
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return Arrays.stream(elements).iterator();
    }

    @Override
    public int size() {
        return putIndex - pullIndex;
    }

    @Override
    public boolean offer(Integer integer) {
        if (putIndex == elements.length) {
            Integer[] temp = new Integer[putIndex - pullIndex + QUEUE_INCREASE_NUMBER];
            System.arraycopy(elements, pullIndex, temp, 0, putIndex - pullIndex);
            elements = temp;
            putIndex -= pullIndex;
            pullIndex = 0;
        }
        elements[putIndex++] = integer;
        return true;
    }

    @Override
    public Integer poll() {
        if(putIndex == pullIndex) {
            return null;
        }
        if (putIndex > QUEUE_DECREASE_NUMBER) {
            Integer[] temp = new Integer[putIndex - pullIndex];
            System.arraycopy(elements, pullIndex, temp, 0, putIndex - pullIndex);
            elements = temp;
            putIndex -= pullIndex;
            pullIndex = 0;
        }
        Integer result = elements[pullIndex];
        elements[pullIndex] = null;
        pullIndex++;
        return result;
    }

    @Override
    public Integer peek() {
        return elements[pullIndex];
    }

}
