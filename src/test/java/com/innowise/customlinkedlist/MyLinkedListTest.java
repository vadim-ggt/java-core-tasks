package com.innowise.customlinkedlist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    @Test
    void newListIsEmpty() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        assertEquals(0, list.size(), "List is empty");
        assertTrue(list.isEmpty(), "List is empty");
    }
}
