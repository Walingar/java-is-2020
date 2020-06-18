package queue.impl;


import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ArrayQueue implements Queue<Integer> {
    private final static int INITIAL_ARRAY_CAPACITY = 8;
    private int size;
    private int[] numbers;

    ArrayQueue() {
        numbers = new int[INITIAL_ARRAY_CAPACITY];
        size = 0;
    };

    /*implementing Queue interface to pass tests*/
    @Override
    public boolean add(Integer integer) {

        if (size == numbers.length) {
            ensureCapacity();
        }

        this.numbers[size] = integer;
        this.size++;

        return true;
    }

    @Override
    public boolean offer(Integer integer) {
        if (size == numbers.length) {
            return false;
        }

        this.numbers[size] = integer;
        this.size++;

        return true;
    }

    @Override
    public Integer remove() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        } else {
            int numberToReturn = numbers[0];
            for (int i = 0; i < size - 1; ++i) {
                numbers[i] = numbers[i + 1];
            }

            size--;

            return numberToReturn;
        }
    }

    @Override
    public Integer poll() {
        if (this.size == 0) {
            return null;
        } else {
            int numberToReturn = numbers[0];
            for (int i = 0; i < size - 1; ++i) {
                numbers[i] = numbers[i + 1];
            }

            size--;

            return numberToReturn;
        }
    }

    @Override
    public Integer element() {
        if (size() != 0) {
            return numbers[0];
        }

        throw new NoSuchElementException();
    }

    @Override
    public Integer peek() {
        return (size() != 0) ? numbers[0] : null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /*default realisation*/
    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return null;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] ts) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Integer> collection) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {}

    private void ensureCapacity() {
        int newArrayCapacity = 2 * this.size;

        int[] extendedNumbers = new int[newArrayCapacity];

        for (int i = 0; i < this.size; ++i){
            extendedNumbers[i] = numbers[i];
        };

        this.numbers = extendedNumbers;
    }

}




