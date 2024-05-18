package sep.jdbc;

import sep.model.Event;

import java.sql.Date;
import java.sql.SQLException;

public interface AdminDatabaseInterface
{

    Event createEvent(String title, String description, String date) throws SQLException;

    void editEvent(String title, String description, Date date) throws SQLException;

}
