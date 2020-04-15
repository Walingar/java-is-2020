package queue.impl.linked;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class LinkedQueueImpl extends AbstractQueue<Integer> {

  private Node head, tail;
  private int size;

  @NotNull
  @Override
  public Iterator<Integer> iterator() {
    return new LinkedQueueIterator(head);
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public boolean offer(Integer item) {
    if (isEmpty()) {
      head = tail = new Node(null, item, null);
      size += 1;
      return true;
    }

    tail.setNext(new Node(tail, item, null));
    tail = tail.getNext();
    size += 1;
    return true;
  }

  @Override
  public Integer poll() {
    if (isEmpty()) {
      return null;
    }

    Integer item = head.getItem();

    if (Objects.isNull(head.getNext())) {
      head = tail = null;
      size = 0;
      return item;
    }

    head.getNext().setPrev(null);
    head = head.getNext();
    size -= 1;
    return item;
  }

  @Override
  public Integer peek() {
    if (isEmpty()) {
      return null;
    }

    return head.getItem();
  }
}