package com.jhuoose.todoose.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemTests {
    @Test
    void testGettersAndSetters() {
        // TODO: Fix this compilation error. Similar to how you did it in Assignment 4.
        var item = new Item(5, "This is a test");
        assertEquals(5, item.getIdentifier());
        assertEquals("This is a test", item.getDescription());
        assertTrue(item.isCompleted());
    }

    // TODO: Write a test for when ‘.isCompleted()’ is false. Not just another assertion on the test above, but a different test altogether.
}
