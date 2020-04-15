package queue.factory;

import java.util.Queue;
import queue.impl.ArrayQueueImpl;

public class ArrayQueueFactory {
    public static Queue<Integer> getInstance() {
        return new ArrayQueueImpl();
    }
}