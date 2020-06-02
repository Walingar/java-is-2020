package queue.impl;

import java.util.Queue;

public class LinkedQueueFactory {
    public static Queue<Integer> getInstance() {
        return new LinkedQueueImpl();
    }
}