package com.innowise.customlinkedlist;

public class MyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T value) {
        //TODO
    }

    public void addLast(T value) {
        //TODO
    }

    public void add(int index, T value) {
        //TODO
    }

    public T getFirst() {
        //TODO
        return null;
    }

    public T getLast() {
        //TODO
        return null;
    }

    public T get(int index) {
        //TODO
        return null;
    }

    public T removeFirst() {
        //TODO
        return null;
    }

    public T removeLast() {
        //TODO
        return null;
    }

    public T remove(int index) {
        //TODO
        return null;
    }
}
