package sep.model;

import java.io.Serializable;

/**
 * Represents an event with an ID, title, description, and date.
 * Implements Serializable interface to allow the object to be serialized.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */

public class Event implements Serializable {
    private int id;
    private String title;
    private String description;
    private String eventDate;

    /**
     * Constructs a new Event with the specified ID, title, description, and date.
     *
     * @param id          the unique identifier for the event
     * @param title       the title of the event
     * @param description a brief description of the event
     * @param eventDate   the date of the event
     */
    public Event(int id, String title, String description, String eventDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
    }

    /**
     * Returns the unique identifier for the event.
     *
     * @return the event ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the title of the event.
     *
     * @return the event title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns a brief description of the event.
     *
     * @return the event description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the date of the event.
     *
     * @return the event date
     */
    public String getEventDate() {
        return eventDate;
    }
}
