package sep.jdbc;

import sep.model.Book;

import java.sql.*;

public class BookDatabaseImplementation {
    private static BookDatabaseImplementation instance;

    private BookDatabaseImplementation() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized BookDatabaseImplementation getInstance() throws SQLException {
        if (instance == null) {
            instance = new BookDatabaseImplementation();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=jdbc", "postgres", "via");
    }

    public Book createBook(String title, String author,int year, String publisher, long isbn, int pageCount, String genre) throws SQLException {
        try (Connection connection = getConnection();){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO books(title, isbn, year, publisher, authors, page_count, genre_id, borrower, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setInt(3, year);
            statement.setString(4, publisher);
            statement.setLong(5, isbn);
            statement.setInt(6, pageCount);
            statement.setString(7, genre);
            statement.setString(8, null);
            statement.setString(9, "available");
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()) {
                return new Book(resultSet.getInt(1), title, author, year, publisher, isbn, pageCount, genre);
            }
            else {
                throw new SQLException();
            }
        }
    }


}
