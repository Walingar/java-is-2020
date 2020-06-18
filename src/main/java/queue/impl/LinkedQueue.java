package queue.impl;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class LinkedQueue implements Queue<Integer> {
    private static class Node{
        private int data;
        private Node nextNode;

        public Node(int data){
            this.data = data;
            nextNode = null;
        }

        public void setNextNode(Node nextNode){
            this.nextNode = nextNode;
        }

        public int getData(){
            return this.data;
        }
    }

    Node initialNode;
    Node lastNode;
    int size;

    public LinkedQueue(){
        size = 0;
        initialNode = null;
        lastNode = null;
    }

    @Override
    public boolean add(Integer integer) {
        Node node = new Node(integer);

        if (size == 0){
            initialNode = node;
        } else {
            lastNode.setNextNode(node);
        }

        lastNode = node;
        size++;

        return true;
    }

    @Override
    public boolean offer(Integer integer) {
        Node node = new Node(integer);

        if (size == 0){
            initialNode = node;
        } else {
            lastNode.setNextNode(node);
        }

        lastNode = node;
        size++;

        return true;
    }

    @Override
    public Integer remove() {
        if (size != 0) {
            int numberToReturn = initialNode.getData();

            initialNode = initialNode.nextNode;
            size--;

            return numberToReturn;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Integer poll() {
        if (size != 0) {
            int numberToReturn = initialNode.getData();

            initialNode = initialNode.nextNode;
            size--;

            return numberToReturn;
        } else {
            return null;
        }
    }

    @Override
    public Integer element() {
        if (size != 0) {
            return initialNode.getData();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Integer peek() {
        return (size != 0) ? initialNode.getData() : null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /*default realisation*/
    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return null;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] ts) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Integer> collection) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }
}

