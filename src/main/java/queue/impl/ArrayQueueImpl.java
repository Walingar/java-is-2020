package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ArrayQueueImpl extends AbstractQueue<Integer> {
    public Integer[] elements;

    public int firstElementIndex;
    public int lastElementIndex;

    public int capacity;

    private static final int DEFAULT_CAPACITY = 4;
    private static final int CAPACITY_MULTIPLIER = 2;

    public ArrayQueueImpl() {
        elements = new Integer[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;

        firstElementIndex = -1;
        lastElementIndex = -1;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int currentPosition = firstElementIndex;

            @Override
            public boolean hasNext() {
                return !(currentPosition == lastElementIndex);
            }

            public Integer next() {
                Integer elementToReturn = elements[currentPosition];

                currentPosition++;
                if (currentPosition != lastElementIndex && currentPosition >= capacity) {
                    currentPosition = 0;
                }

                return elementToReturn;
            }
        };
    }

    @Override
    public int size() {
        if (firstElementIndex == -1) {
            return 0;
        }

        if (lastElementIndex >= firstElementIndex) {
            return lastElementIndex - firstElementIndex + 1;
        }

        return firstElementIndex - lastElementIndex + 1;
    }


    @Override
    public boolean offer(Integer integer) {
        boolean exceedsLinearCapacity = (lastElementIndex + 1) >= capacity;
        boolean exceedsCyclicCapacity = (lastElementIndex < firstElementIndex) &&
                ((lastElementIndex + 1) >= firstElementIndex);

        if (exceedsLinearCapacity || exceedsCyclicCapacity) {
            int extendedCapacity = capacity * CAPACITY_MULTIPLIER;

            if (exceedsLinearCapacity) {
                ChangeCapacityLinear(extendedCapacity);
            } else {
                ChangeCapacityCyclic(extendedCapacity);
            }
        }

        if (size() == 0) {
            firstElementIndex = 0;
        }

        lastElementIndex++;

        elements[lastElementIndex] = integer;

        return true;
    }

    @Override
    public Integer poll() {
        if (firstElementIndex == -1) {
            return null;
        }

        Integer elementToReturn = elements[firstElementIndex];

        if (lastElementIndex < firstElementIndex) {
            firstElementIndex++;

            if (firstElementIndex >= capacity) {
                firstElementIndex = 0;
            }
        } else {
            firstElementIndex++;
            if (firstElementIndex > lastElementIndex) {
                firstElementIndex = -1;
                lastElementIndex = -1;
            }
        }

        int size = size();
        if ((size != 0) && (capacity / size > CAPACITY_MULTIPLIER)) {
            Shrink();
        }

        return elementToReturn;
    }

    @Override
    public Integer peek() {
        if (firstElementIndex == -1) {
            return null;
        }

        return elements[firstElementIndex];
    }

    private void Shrink() {
        boolean isLinear = (lastElementIndex > firstElementIndex);
        boolean isCyclic = (lastElementIndex < firstElementIndex);

        int shrinkedCapacity = capacity / CAPACITY_MULTIPLIER;

        if (isLinear) {
            ChangeCapacityLinear(shrinkedCapacity);
        } else if (isCyclic) {
            ChangeCapacityCyclic(shrinkedCapacity);
        }
    }

    private void ChangeCapacityCyclic(int newCapacity) {
        capacity = newCapacity;

        Integer[] newArray = new Integer[capacity];

        if (firstElementIndex + 1 - lastElementIndex >= 0) {
            System.arraycopy(elements, lastElementIndex, newArray, 0, firstElementIndex + 1 - lastElementIndex);
        }

        firstElementIndex = firstElementIndex - lastElementIndex;
        lastElementIndex = 0;

        //Reverse array for linear state
        Integer temporary;
        for (int index = 0; index <= firstElementIndex; index++) {
            temporary = newArray[firstElementIndex - index];
            newArray[firstElementIndex - index] = newArray[index];
            newArray[index] = temporary;
        }

        lastElementIndex = firstElementIndex;
        firstElementIndex = 0;

        elements = newArray;
    }

    private void ChangeCapacityLinear(int newCapacity) {
        capacity = newCapacity;

        Integer[] newArray = new Integer[capacity];

        if (lastElementIndex + 1 - firstElementIndex >= 0) {
            System.arraycopy(elements, firstElementIndex, newArray, 0, lastElementIndex + 1 - firstElementIndex);
        }

        lastElementIndex = lastElementIndex - firstElementIndex;
        firstElementIndex = 0;

        elements = newArray;
    }
}
