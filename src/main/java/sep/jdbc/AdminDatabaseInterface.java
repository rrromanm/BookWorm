package sep.jdbc;

import sep.model.Event;

import java.sql.SQLException;
import java.util.List;

public interface AdminDatabaseInterface
{
    List<Event> getAllEvents() throws SQLException;
    Event createEvent(String title, String description, String eventDate) throws SQLException;

    void deleteEvent(int id) throws SQLException;
    void deletePatron(int id) throws SQLException;
}
