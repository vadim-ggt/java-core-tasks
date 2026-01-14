package com.innowise.customlinkedlist;

import java.util.NoSuchElementException;

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
        Node<T> newNode = new Node<>(value);
        if(isEmpty()) {
            head  = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }


    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if(isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }


    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> tNode;
        if(index < size / 2) {
            tNode = head;
            for(int i = 0; i < index; i++) {
                tNode = tNode.next;
            }
        } else {
            tNode = tail;
            for(int i = size - 1; i > index; i--) {
                tNode = tNode.prev;
            }
        }
        return tNode;
    }


    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0){
            addFirst(value);
        } else if(index == size ) {
            addLast(value);
        } else {
            Node<T> newNode = new Node<>(value);
            Node<T> current = getNode(index);

            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }


    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        return head.value;
    }


    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        return tail.value;
    }


    public T get(int index) {
        return getNode(index).value;
    }


    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        T value = head.value;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return value;
    }


    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        T value = tail.value;
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return value;
    }


    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            return removeFirst();
        } else if(index == size - 1) {
            return removeLast();
        } else {
            Node<T> tNode = getNode(index);
            tNode.prev.next = tNode.next;
            tNode.next.prev = tNode.prev;
            size--;
            return tNode.value;
        }
    }
}
