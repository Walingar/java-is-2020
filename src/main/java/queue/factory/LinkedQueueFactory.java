package queue.factory;

import java.util.Queue;
import queue.impl.linked.LinkedQueueImpl;

public class LinkedQueueFactory {
    public static Queue<Integer> getInstance() {
        return new LinkedQueueImpl();
    }
}