package queue.impl.linked;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueueIterator implements Iterator<Integer> {

  private Node current;

  public LinkedQueueIterator(Node head) {
    current = head;
  }

  @Override
  public boolean hasNext() {
    return current.getNext() != null;
  }

  public boolean hasPrev() {
    return current.getPrev() != null;
  }

  @Override
  public Integer next() {
    if (hasNext()) {
      current = current.getNext();
      return current.getItem();
    }

    throw new NoSuchElementException();
  }

  @Override
  public void remove() {
    if (current.isHasRemoved()) {
      throw new IllegalStateException();
    }

    if (hasPrev()) {
      current.getPrev().setNext(current.getNext());
    }

    if (hasNext()) {
      current.getNext().setPrev(current.getPrev());
    }

    current.setHasRemoved();
  }
}