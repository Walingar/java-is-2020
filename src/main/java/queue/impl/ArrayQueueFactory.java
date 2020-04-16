package queue.impl;

import java.util.Queue;

public class ArrayQueueFactory {
    public static Queue<Integer> getInstance() {
        return new ArrayQueueImpl<Integer>();
    }
}