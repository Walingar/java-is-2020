package queue.impl;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class LinkedQueueImpl extends AbstractQueue<Integer> {

  private Node head;
  private Node tail;
  private int size;

  @NotNull
  @Override
  public Iterator<Integer> iterator() {
    return new LinkedQueueIterator(head);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean offer(Integer item) {
    if (isEmpty()) {
      head = new Node(null, item, null);
      tail = head;
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
      head = null;
      tail = null;
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

  private class LinkedQueueIterator implements Iterator<Integer> {

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

        if (current.equals(tail)) {
          tail = current.getPrev();
        }
      }

      if (hasNext()) {
        current.getNext().setPrev(current.getPrev());

        if (current.equals(head)) {
          head = current.getNext();
        }
      }

      size -= 1;
      current.setHasRemoved();
    }
  }

  private static class Node {

    private final Integer item;
    private Node next;
    private Node prev;
    private boolean hasRemoved = false;

    Node(Node prev, Integer element, Node next) {
      this.item = element;
      this.next = next;
      this.prev = prev;
    }

    public void setHasRemoved() {
      hasRemoved = true;
    }

    public Integer getItem() {
      return item;
    }

    public boolean isHasRemoved() {
      return hasRemoved;
    }

    public Node getNext() {
      return next;
    }

    public void setNext(Node next) {
      this.next = next;
    }

    public Node getPrev() {
      return prev;
    }

    public void setPrev(Node prev) {
      this.prev = prev;
    }
  }
}