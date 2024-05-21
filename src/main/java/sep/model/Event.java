package sep.model;

import java.io.Serializable;

public class Event implements Serializable {
    private String title;
    private String description;
    private String eventDate;

    public Event(String title, String description, String eventDate) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
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
