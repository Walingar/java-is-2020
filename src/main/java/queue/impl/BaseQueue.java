package queue.impl;

import java.util.*;

public abstract class BaseQueue extends AbstractQueue<Integer> {
    protected int size;

    protected BaseQueue() {
        size = 0;
    }

    public int size() {
        return size;
    }
}