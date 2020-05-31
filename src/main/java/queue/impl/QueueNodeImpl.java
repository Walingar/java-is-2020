package queue.impl;

public class QueueNodeImpl {
    private Integer value;

    private QueueNodeImpl next;
    private QueueNodeImpl previous;

    public QueueNodeImpl(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public QueueNodeImpl getNext() {
        return next;
    }

    public QueueNodeImpl getPrevious() {
        return previous;
    }

    public void setNext(QueueNodeImpl next) {
        this.next = next;
    }

    public void setPrevious(QueueNodeImpl previous) {
        this.previous = previous;
    }
}
