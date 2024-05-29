package sep.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    private Event event;

    @BeforeEach
    void setUp()
    {
        event = new Event(1, "Test", "Test event", "18-08-2024");
    }

    @Test
    void getIdTest() {
        assertEquals(event.getId(), 1);
    }

    @Test
    void getTitleTest() {
        assertEquals(event.getTitle(), "Test");
    }

    @Test
    void getDescriptionTest() {
        assertEquals(event.getDescription(), "Test event");
    }

    @Test
    void getDateTest() {
        assertEquals(event.getEventDate(), "18-08-2024");
    }
}