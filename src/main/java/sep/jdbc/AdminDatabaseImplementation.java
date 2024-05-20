package sep.jdbc;

import sep.model.Event;

import java.sql.*;
import java.util.ArrayList;

public class AdminDatabaseImplementation implements AdminDatabaseInterface
{
    private static AdminDatabaseImplementation instance;
    private AdminDatabaseImplementation() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized AdminDatabaseImplementation getInstance() throws SQLException {
        if (instance == null) {
            instance = new AdminDatabaseImplementation();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=book_work_db", "postgres", "VIAVIA"); //TODO: YOU NEED TO CHANGE THIS PASSWORD ON WHO IS WORKING ON CODE RN

    }

    public ArrayList<Event> getAllEvents() throws SQLException {
        ArrayList<Event> events = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM book_worm_db.events");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int eventID = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String date = resultSet.getString("eventDate");
                Event event = new Event(eventID, title, description, date);
                events.add(event);
            }
        }
        return events;
    }

    @Override
    public Event createEvent(String title, String description, String eventdate) throws SQLException {
        try (Connection connection = getConnection();){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO book_worm_db.events(title, description, eventdate) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, eventdate);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()) {
                return new Event(resultSet.getInt(1), title, description, eventdate);
            }
            else {
                throw new SQLException();
            }
        }
    }
}
