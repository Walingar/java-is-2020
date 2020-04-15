package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

public class ArrayQueueImpl extends AbstractQueue<Integer> {

  private static final int INITIAL_CAPACITY = 1000;

  private int[] items;
  private int head;
  private int tail;
  private int size;

  public ArrayQueueImpl() {
    this(INITIAL_CAPACITY, 0);
  }

  public ArrayQueueImpl(int capacity, int actualSize) {
    items = new int[capacity];
    size = actualSize;
  }

  @NotNull
  @Override
  public Iterator<Integer> iterator() {
    return new ArrayIterator();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean offer(Integer integer) {
    if (isFull()) {
      resize();
    }

    items[tail] = integer;
    tail = (tail + 1) % items.length;
    size += 1;

    return true;
  }

  @Override
  public Integer poll() {
    if (isEmpty()) {
      return null;
    }

    int item = items[head];
    head = (head + 1) % items.length;
    size -= 1;

    return item;
  }

  @Override
  public Integer peek() {
    if (isEmpty()) {
      return null;
    }

    return items[head];
  }

  private boolean isFull() {
    return size == items.length;
  }

  private void resize() {
    ArrayQueueImpl newQueue = new ArrayQueueImpl(items.length * 2, 0);

    while (!isEmpty()) {
      newQueue.add(poll());
    }

    head = newQueue.head;
    tail = newQueue.tail;
    items = newQueue.items;
    size = newQueue.size;
  }

  private class ArrayIterator implements Iterator<Integer> {

    private int current = head;

    @Override
    public boolean hasNext() {
      return tail != current;
    }

    @Override
    public Integer next() {
      if (hasNext()) {
        int next = items[current];
        current = (current + 1) % items.length;
        return next;
      }

      throw new NoSuchElementException();
    }
  }
}