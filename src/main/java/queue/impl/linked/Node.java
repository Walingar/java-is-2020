package queue.impl.linked;

public class Node {

  private Integer item;
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