package com.innowise.customlinkedlist;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    @Test
    void newListIsEmpty() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        assertEquals(0, list.size(), "List is empty");
        assertTrue(list.isEmpty(), "List is empty");
    }

    @Test
    void addFirstAndGetFirst() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addFirst(555);
        assertEquals(555, list.getFirst());
        assertEquals(1, list.size());

        list.addFirst(667);
        assertEquals(667, list.getFirst());
        assertEquals(2, list.size());

        list.addFirst(1);
        assertEquals(1, list.getFirst());
        assertEquals(3, list.size());
    }

    @Test
    void addLastAndGetLast() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(999);
        assertEquals(999, list.getLast());
        assertEquals(1, list.size());

        list.addLast(20);
        assertEquals(20, list.getLast());
        assertEquals(2, list.size());
    }

    @Test
    void addByIndex() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addFirst(100);
        list.add(1, 200);
        list.add(1, 300);

        assertEquals(100, list.get(0));
        assertEquals(300, list.get(1));
        assertEquals(200, list.get(2));
        assertEquals(3, list.size());
    }

    @Test
    void removeFirst() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(100);
        list.addLast(2000);
        list.addLast(300);

        assertEquals(100, list.removeFirst());
        assertEquals(2, list.size());
        assertEquals(2000, list.getFirst());

        // проверка ветки head == tail
        list = new MyLinkedList<>();
        list.addFirst(10);
        assertEquals(10, list.removeFirst());
        assertTrue(list.isEmpty());
    }

    @Test
    void removeLast() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(111);
        list.addLast(222);
        list.addLast(3333);

        assertEquals(3333, list.removeLast());
        assertEquals(2, list.size());
        assertEquals(222, list.getLast());


        list = new MyLinkedList<>();
        list.addLast(10);
        assertEquals(10, list.removeLast());
        assertTrue(list.isEmpty());
    }

    @Test
    void removeByIndex() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        assertEquals(20, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(10, list.getFirst());
        assertEquals(30, list.getLast());

        assertEquals(10, list.remove(0));
        assertEquals(30, list.remove(0));
        assertTrue(list.isEmpty());
    }

    @Test
    void getThrowsOnEmpty() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    void removeThrowsOnEmpty() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        assertThrows(NoSuchElementException.class, list::removeFirst);
        assertThrows(NoSuchElementException.class, list::removeLast);
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }

    @Test
    void addByIndexThrows() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 10));
    }

    @Test
    void getNodeBranchCoverage() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(1, list.get(0));
        assertEquals(3, list.get(2));
    }

    @Test
    void getFirstLastThrowsOnEmpty() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        assertThrows(NoSuchElementException.class, list::getFirst);
        assertThrows(NoSuchElementException.class, list::getLast);
    }


    @Test
    void removeMiddleIndex() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        int removed = list.remove(1);
        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }


    @Test
    void addMiddleIndex() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(10);
        list.addLast(30);
        list.add(1, 20);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

}
