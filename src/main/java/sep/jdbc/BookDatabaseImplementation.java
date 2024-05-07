package sep.jdbc;

import sep.model.Available;
import sep.model.Book;
import sep.model.Patron;
import sep.model.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Book> filterByGenre(int genreID) throws SQLException {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE genre_id = ?");
            statement.setInt(1, genreID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String publisher = resultSet.getString("publisher");
                long isbn = resultSet.getLong("isbn");
                int pageCount = resultSet.getInt("page_count");
                String genre = resultSet.getString("genre");
                Patron borrower = (Patron) resultSet.getObject("borrower"); //TODO: needs to be figured out
                State state = (State) resultSet.getObject("state"); //TODO: needs to be figured out
                Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                book.setBorrower(borrower);
                book.setState(state);
                books.add(book);
            }
            return books;
        }
    }

    public List<Book> filterByState(State state) throws SQLException {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE state = ?");
            statement.setObject(1, state);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String publisher = resultSet.getString("publisher");
                long isbn = resultSet.getLong("isbn");
                int pageCount = resultSet.getInt("page_count");
                String genre = resultSet.getString("genre");
                Patron borrower = (Patron) resultSet.getObject("borrower"); //TODO: needs to be figured out
                Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                book.setBorrower(borrower);
                book.setState(state);
                books.add(book);
            }
            return books;
        }
    }
}
