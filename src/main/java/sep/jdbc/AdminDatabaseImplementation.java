package sep.jdbc;

import sep.model.Event;

import java.sql.*;
import java.util.ArrayList;

/**
 * The AdminDatabaseImplementation class provides an implementation of the AdminDatabaseInterface
 * for interacting with the database related to administrative operations.
 * It handles operations for events and patrons.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminDatabaseImplementation implements AdminDatabaseInterface
{
    private static AdminDatabaseImplementation instance;
    private AdminDatabaseImplementation() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    /**
     * Retrieves the singleton instance of AdminDatabaseImplementation.
     *
     * @return The AdminDatabaseImplementation instance
     * @throws SQLException If a database access error occurs
     */
    public static synchronized AdminDatabaseImplementation getInstance() throws SQLException {
        if (instance == null) {
            instance = new AdminDatabaseImplementation();
        }
        return instance;
    }

    /**
     * Establishes a connection to the database.
     *
     * @return A Connection object representing the database connection
     * @throws SQLException If a database access error occurs
     */

    private Connection getConnection() throws SQLException {


        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=book_worm_db", "postgres", "via");



    }

    /**
     * Retrieves all events from the database.
     *
     * @return An ArrayList containing all events
     * @throws SQLException If a database access error occurs
     */
    public ArrayList<Event> getAllEvents() throws SQLException {
        ArrayList<Event> events = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM book_worm_db.events");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String date = resultSet.getString("eventDate");
                Event event = new Event(id, title, description, date);
                events.add(event);
            }
        }
        return events;
    }

    /**
     * Creates a new event in the database.
     *
     * @param title       The title of the event
     * @param description The description of the event
     * @param eventdate   The date of the event
     * @return The newly created event
     * @throws SQLException If a database access error occurs
     */
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

    /**
     * Deletes an event from the database based on its ID.
     *
     * @param id The ID of the event to delete
     * @throws SQLException If a database access error occurs
     */
    @Override
    public void deleteEvent(int id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM book_worm_db.events WHERE id = ?");
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No event found with the specified title, description, and date.");
            }
        }
    }


    /**
     * Deletes a patron from the database based on its ID.
     *
     * @param id The ID of the patron to delete
     * @throws SQLException If a database access error occurs
     */
    //Wipes out data about Patron from dbs completely
    @Override
    public void deletePatron(int id) throws SQLException {
        try (Connection connection = getConnection()) {
            boolean isCurrentlyBorrowing = false;
            String checkCurrentBorrowingQuery = "SELECT COUNT(*) FROM books WHERE borrower = ?";
            try (PreparedStatement checkCurrentBorrowingStatement = connection.prepareStatement(checkCurrentBorrowingQuery)) {
                checkCurrentBorrowingStatement.setInt(1, id);
                try (ResultSet resultSet = checkCurrentBorrowingStatement.executeQuery()) {
                    if (resultSet.next()) {
                        isCurrentlyBorrowing = resultSet.getInt(1) > 0;
                    }
                }
            }

            if (isCurrentlyBorrowing) {
                throw new SQLException("Cannot delete patron with currently borrowed books.");
            }

            boolean hasDonatedBooks = false;
            String checkDonatedBooksQuery = "SELECT COUNT(*) FROM books_donate WHERE donated_by = ?";
            try (PreparedStatement checkDonatedBooksStatement = connection.prepareStatement(checkDonatedBooksQuery)) {
                checkDonatedBooksStatement.setInt(1, id);
                try (ResultSet resultSet = checkDonatedBooksStatement.executeQuery()) {
                    if (resultSet.next()) {
                        hasDonatedBooks = resultSet.getInt(1) > 0;
                    }
                }
            }

            if (hasDonatedBooks) {
                String deleteBooksDonateQuery = "DELETE FROM books_donate WHERE donated_by = ?";
                try (PreparedStatement booksDonateStatement = connection.prepareStatement(deleteBooksDonateQuery)) {
                    booksDonateStatement.setInt(1, id);
                    booksDonateStatement.executeUpdate();
                }
            }

            String deleteWishlistQuery = "DELETE FROM wishlist WHERE profile_id = ?";
            try (PreparedStatement wishlistStatement = connection.prepareStatement(deleteWishlistQuery)) {
                wishlistStatement.setInt(1, id);
                wishlistStatement.executeUpdate();
            }

            String deleteBorrowedBooksQuery = "DELETE FROM borrowed_books WHERE profile_id = ?";
            try (PreparedStatement borrowedBooksStatement = connection.prepareStatement(deleteBorrowedBooksQuery)) {
                borrowedBooksStatement.setInt(1, id);
                borrowedBooksStatement.executeUpdate();
            }

            String deletePatronQuery = "DELETE FROM patron WHERE id = ?";
            try (PreparedStatement patronStatement = connection.prepareStatement(deletePatronQuery)) {
                patronStatement.setInt(1, id);
                int rowsAffected = patronStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("No patron found with the specified id.");
                }
            }
        }
    }

    /**
     * Updates an existing event in the database.
     *
     * @param id          The ID of the event to update
     * @param title       The new title of the event
     * @param description The new description of the event
     * @param eventDate   The new date of the event
     * @throws SQLException If a database access error occurs
     */
    @Override
    public void updateEvent(int id, String title, String description, String eventDate) throws SQLException {
        try (Connection connection = getConnection()) {
            String sqlQuery = "UPDATE book_worm_db.events " +
                    "SET title = ?, description = ?, eventdate = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, eventDate);
            statement.setInt(4, id);
            statement.executeUpdate();
        }
    }



}