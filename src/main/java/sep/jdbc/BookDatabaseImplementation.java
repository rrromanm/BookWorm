package sep.jdbc;

import sep.model.*;

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
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=book_work_db", "postgres", "via");
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

    public ArrayList<Book> readBooks() throws SQLException {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement1 = connection.prepareStatement(
                "SELECT\n" +
                        "    books.id,\n" +
                        "    books.title,\n" +
                        "    books.authors,\n" +
                        "    books.year,\n" +
                        "    books.publisher,\n" +
                        "    books.isbn,\n" +
                        "    books.page_count,\n" +
                        "    books.state,\n" +
                        "    g.genre AS genre_name,\n" +
                        "    p.id as borrower_id,\n" +
                        "    p.username as username,\n" +
                        "    p.first_name as firstname,\n" +
                        "    p.last_name as lastname,\n" +
                        "    p.password as password,\n" +
                        "    p.email as email,\n" +
                        "    p.phone_number as pNo,\n" +
                        "    p.fees as fees\n" +
                        "FROM\n" +
                        "    book_worm_db.books\n" +
                        "JOIN\n" +
                        "    book_worm_db.genre g ON books.genre_id = g.id\n" +
                        "LEFT JOIN\n" +
                        "    book_worm_db.patron p on books.borrower = p.id;");

        ResultSet resultSet = statement1.executeQuery();
        ArrayList<Book> books = new ArrayList<>();
        while (resultSet.next())
        {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String author = resultSet.getString("authors");
            int year = resultSet.getInt("year");
            String publisher = resultSet.getString("publisher");
            long isbn = resultSet.getLong("isbn");
            int pageCount = resultSet.getInt("page_count");
            String state = resultSet.getString("state");
            String genre = resultSet.getString("genre_name");

            int borrowerId = resultSet.getInt("borrower_id");
            String username = resultSet.getString("username");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            String phoneNumber = resultSet.getString("phone_number");
            int fee = resultSet.getInt("fees");

            if (state.equals("Available")){
                State state1 = new Available();
            }

            if (username == null) {
                Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                books.add(book);
            } else {
                Patron patron = new Patron(borrowerId, firstname, lastname, username, password, email, phoneNumber, fee);
                Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                book.setBorrower(patron);
            }
        }
        return books;
        }
    }
}
