package sep.model;

import java.io.Serializable;

public class Event implements Serializable {
    private int id;
    private String title;
    private String description;
    private String eventDate;

    public Event(int id, String title, String description, String eventDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getEventDate() {
        return eventDate;
    }
}
