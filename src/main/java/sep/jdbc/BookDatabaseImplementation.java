package sep.jdbc;

import sep.model.*;

import java.sql.*;
import java.util.ArrayList;

public class BookDatabaseImplementation implements BookDatabaseInterface {
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

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=book_work_db", "postgres", "343460"); //TODO: YOU NEED TO CHANGE THIS PASSWORD ON WHO IS WORKING ON CODE RN

    }

    public Book createBook(String title, String author,int year, String publisher, long isbn, int pageCount, String genre) throws SQLException {
        try (Connection connection = getConnection();){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO books(title, isbn, year, publisher, page_count, genre_id, borrower, state, authors) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
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

    public ArrayList<Book> filter(String state, String genres, String search) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement;
            StringBuilder sqlQuery = new StringBuilder("SELECT\n" +
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
                "    book_worm_db.patron p on books.borrower = p.id\n" +
                "WHERE 1=1");

            if (!"All".equals(state)) {
                sqlQuery.append(" AND state = ?");
            }
            if (!"All".equals(genres)) {
                sqlQuery.append(" AND genre = ?");
            }
            if (!search.isEmpty()) {
                sqlQuery.append(" AND LOWER(title) LIKE LOWER(?)");
            }

            statement = connection.prepareStatement(sqlQuery.toString());

            int parameterIndex = 1;
            if (!"All".equals(state)) {
                statement.setString(parameterIndex++, state);
            }
            if (!"All".equals(genres)) {
                statement.setString(parameterIndex++, genres);
            }
            if (!search.isEmpty()) {
                statement.setString(parameterIndex, "%" + search.toLowerCase() + "%");
            }
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("authors");
                int year = resultSet.getInt("year");
                String publisher = resultSet.getString("publisher");
                long isbn = resultSet.getLong("isbn");
                int pageCount = resultSet.getInt("page_count");
                String state2 = resultSet.getString("state");
                String genre = resultSet.getString("genre_name");

                int borrowerId = resultSet.getInt("borrower_id");
                String username = resultSet.getString("username");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("pNo");
                int fee = resultSet.getInt("fees");

                if (username == null) {
                    State state1 = null;
                    Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                    if (state2.equalsIgnoreCase("Available")){
                        state1 = new Available();
                    }
                    else if(state2.equalsIgnoreCase("Borrowed")){
                        state1 = new Borrowed();
                    }
                    book.setState(state1);
                    books.add(book);
                } else {
                    State state1 = null;
                    Patron patron = new Patron(borrowerId, firstname, lastname, username, password, email, phoneNumber, fee);
                    Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                    if (state.equalsIgnoreCase("Available")){
                        state1 = new Available();
                    }
                    else if(state.equalsIgnoreCase("Borrowed")){
                        state1 = new Borrowed();
                    }
                    book.setState(state1);
                    book.setBorrower(patron);
                    books.add(book);
                }
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
            String phoneNumber = resultSet.getString("pNo");
            int fee = resultSet.getInt("fees");

            if (username == null) {
                State state1 = null;
                Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                if (state.equalsIgnoreCase("Available")){
                    state1 = new Available();
                }
                else if(state.equalsIgnoreCase("Borrowed")){
                    state1 = new Borrowed();
                }
                book.setState(state1);
                books.add(book);
            } else {
                State state1 = null;
                Patron patron = new Patron(borrowerId, firstname, lastname, username, password, email, phoneNumber, fee);
                Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                if (state.equalsIgnoreCase("Available")){
                    state1 = new Available();
                }
                else if(state.equalsIgnoreCase("Borrowed")){
                    state1 = new Borrowed();
                }
                book.setState(state1);
                book.setBorrower(patron);
                books.add(book);
            }
        }
        return books;
        }
    }
    public ArrayList<String> readGenres() throws SQLException{
        try (Connection connection = getConnection()){
            PreparedStatement statement1 = connection.prepareStatement("SELECT genre FROM book_worm_db.genre");
            ResultSet resultSet = statement1.executeQuery();
            ArrayList<String> genres = new ArrayList<>();
            while (resultSet.next()){
                String genre = resultSet.getString("genre");
                genres.add(genre);
            }
            return genres;
        }
    }

    @Override
    public ArrayList<Book> readDonatedBooks() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement1 = connection.prepareStatement(
                    "SELECT\n" +
                            "    books_donate.id,\n" +
                            "    patron.username AS donated_by,\n" +
                            "    books_donate.title,\n" +
                            "    books_donate.author,\n" +
                            "    books_donate.ISBN,\n" +
                            "    books_donate.year,\n" +
                            "    books_donate.publisher,\n" +
                            "    books_donate.page_count,\n" +
                            "    genre.genre AS genre\n" +
                            "FROM\n" +
                            "    book_worm_db.books_donate\n" +
                            "INNER JOIN\n" +
                            "    book_worm_db.patron ON books_donate.donated_by = patron.id\n" +
                            "INNER JOIN\n" +
                            "    book_worm_db.genre ON books_donate.genre_id = genre.id;");

            ResultSet resultSet = statement1.executeQuery();
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

                Book book = new Book(id ,title, author, year, publisher, isbn, pageCount, genre);
                books.add(book);
            }
            return books;
        }
    }

    //TODO: Needs to implement a function that the admin sets how long you can borrow book for + extend

    public void borrowBook(Book book, Patron patron) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO book_worm_db.borrowed_books (book_id, profile_id, borrowed_date, return_date) VALUES (?, ?, CURRENT_DATE, CURRENT_DATE + INTERVAL '1 month')"
            );
            statement.setInt(1, book.getBookId());
            statement.setInt(2, patron.getUserID());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                PreparedStatement updateStatement = connection.prepareStatement(
                        "UPDATE book_worm_db.books SET state = 'Borrowed' , borrower = ?  WHERE id = ? "
                );
                updateStatement.setInt(1, patron.getUserID());
                updateStatement.setInt(2, book.getBookId());
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Book borrowed successfully.");
                } else {
                    System.out.println("Failed to update the state of the book.");
                }
            } else {
                System.out.println("Failed to borrow the book.");
            }
        }
    }

    public void returnBookToDatabase(Book book, Patron patron) throws SQLException {
        try (Connection connection = getConnection()) {
                PreparedStatement returnStatement = connection.prepareStatement(
                        "UPDATE book_worm_db.borrowed_books SET return_date = CURRENT_DATE WHERE book_id = ? AND profile_id = ?"
                );
                returnStatement.setInt(1, book.getBookId());
                returnStatement.setInt(2, patron.getUserID());

                int rowsUpdated = returnStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    PreparedStatement updateStatement = connection.prepareStatement(
                            "UPDATE book_worm_db.books SET state = 'Available', borrower = NULL WHERE id = ?"
                    );
                    updateStatement.setInt(1, book.getBookId());

                    int rowsAffected = updateStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Book returned successfully.");
                    } else {
                        System.out.println("Failed to update the state of the book.");
                    }
                } else {
                    System.out.println("Failed to update the return date of the book.");
                }
            }
        }

    public ArrayList<Book> readBorrowedBook(Patron patron) throws SQLException{
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT " +
                    "    books.id,  " +
                    "    books.title, " +
                    "    books.authors, " +
                    "    books.year, " +
                    "    books.publisher, " +
                    "    books.isbn, " +
                    "    books.page_count,"
                    + " books.state," +
                    "    g.genre AS genre_name " +
                    "FROM " +
                    "    book_worm_db.books " +
                    "JOIN " +
                    "    book_worm_db.genre g ON books.genre_id = g.id " +
                    "LEFT JOIN " +
                    "    book_worm_db.borrowed_books bb ON books.id = bb.book_id " +
                    "LEFT JOIN " +
                    "    book_worm_db.patron p ON p.id = books.borrower " +
                    "WHERE " +
                    "    p.id = ? "
                    + " AND bb.return_date > CURRENT_DATE;");
            statement.setInt(1, patron.getUserID());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("authors");
                int year = resultSet.getInt("year");
                String publisher = resultSet.getString("publisher");
                long isbn = resultSet.getLong("isbn");
                int pageCount = resultSet.getInt("page_count");
                String state = resultSet.getString("state");
                String genre = resultSet.getString("genre_name");
                State state1 = null;
                Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                if (state != null) {
                    if (state.equalsIgnoreCase("Available")){
                        state1 = new Available();
                    } else if(state.equalsIgnoreCase("Borrowed")){
                        state1 = new Borrowed();
                    }
                }

                book.setState(state1);
                book.setBorrower(patron);
                books.add(book);
            }
            return books;
        }
    }
    public ArrayList<Book> readHistoryOfBooks(Patron patron) throws SQLException{
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT " +
                    "    books.id,  " +
                    "    books.title, " +
                    "    books.authors, " +
                    "    books.year, " +
                    "    books.publisher, " +
                    "    books.isbn, " +
                    "    books.page_count,"
                    + " books.state," +
                    "    g.genre AS genre_name " +
                    "FROM " +
                    "    book_worm_db.books " +
                    "JOIN " +
                    "    book_worm_db.genre g ON books.genre_id = g.id " +
                    "LEFT JOIN " +
                    "    book_worm_db.borrowed_books bb ON books.id = bb.book_id " +
                    "WHERE " +
                    "    profile_id = ? "
                    + " AND bb.return_date <= CURRENT_DATE;");
            statement.setInt(1, patron.getUserID());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("authors");
                int year = resultSet.getInt("year");
                String publisher = resultSet.getString("publisher");
                long isbn = resultSet.getLong("isbn");
                int pageCount = resultSet.getInt("page_count");
                String state = resultSet.getString("state");
                String genre = resultSet.getString("genre_name");
                State state1 = null;
                Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                if (state != null) {
                    if (state.equalsIgnoreCase("Available")){
                        state1 = new Available();
                    } else if(state.equalsIgnoreCase("Borrowed")){
                        state1 = new Borrowed();
                    }
                }

                book.setState(state1);
                book.setBorrower(patron);
                books.add(book);
            }
            return books;
        }
    }
    public int readAmountOfBooksRead(Patron patron) throws SQLException{
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) " +
                    "FROM " +
                    "    book_worm_db.books " +
                    "JOIN " +
                    "    book_worm_db.genre g ON books.genre_id = g.id " +
                    "LEFT JOIN " +
                    "    book_worm_db.borrowed_books bb ON books.id = bb.book_id " +
                    "WHERE " +
                    "    profile_id = ? "
                    + " AND bb.return_date <= CURRENT_DATE;");
            statement.setInt(1, patron.getUserID());
            ResultSet resultSet = statement.executeQuery();
            int amount = 0;
            while (resultSet.next()) {
                amount = resultSet.getInt("count");
            }
            return amount;
        }
    }
    public void wishlistBook(Book book, Patron patron) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO book_worm_db.wishlist (profile_id, book_id)\n" +
                    "VALUES (?, ?);"
            );
            statement.setInt(1, patron.getUserID());
            statement.setInt(2, book.getBookId());
            statement.executeUpdate();

        }
    }
    public boolean isWishlisted(Book book,Patron patron) throws SQLException{
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT count(*)\n"
                + "FROM book_worm_db.wishlist\n"
                + "WHERE profile_id = ? AND book_id = ?;");
            statement.setInt(1, patron.getUserID());
            statement.setInt(2, book.getBookId());
            ResultSet resultSet = statement.executeQuery();
            int amount = 0;
            while (resultSet.next()) {
                amount = resultSet.getInt("count");
            }
            if(amount == 1){
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public ArrayList<Book> readWishlistedBooks(Patron patron) throws SQLException{
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT " +
                    "    books.id,  " +
                    "    books.title, " +
                    "    books.authors, " +
                    "    books.year, " +
                    "    books.publisher, " +
                    "    books.isbn, " +
                    "    books.page_count,"
                    + " books.state," +
                    "    g.genre AS genre_name " +
                    "FROM " +
                    "    book_worm_db.books " +
                    "JOIN " +
                    "    book_worm_db.genre g ON books.genre_id = g.id " +
                    "LEFT JOIN " +
                    "    book_worm_db.wishlist bb ON books.id = bb.book_id " +
                    "WHERE " +
                    "    profile_id = ? ;");
            statement.setInt(1, patron.getUserID());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("authors");
                int year = resultSet.getInt("year");
                String publisher = resultSet.getString("publisher");
                long isbn = resultSet.getLong("isbn");
                int pageCount = resultSet.getInt("page_count");
                String state = resultSet.getString("state");
                String genre = resultSet.getString("genre_name");
                State state1 = null;
                Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                if (state != null) {
                    if (state.equalsIgnoreCase("Available")){
                        state1 = new Available();
                    } else if(state.equalsIgnoreCase("Borrowed")){
                        state1 = new Borrowed();
                    }
                }
                book.setState(state1);
                book.setBorrower(patron);
                books.add(book);
            }
            return books;
        }
    }
    public void deleteFromWishlist(Book book,Patron patron) throws SQLException{
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM book_worm_db.wishlist " +
                    "WHERE book_id = ? AND profile_id = ?;"
            );
            statement.setInt(1, book.getBookId());
            statement.setInt(2, patron.getUserID());
            statement.executeUpdate();
        }
    }

    @Override public int readAmountOfBorrowedBooks(Patron patron)
        throws SQLException
    {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "book_worm_db.books b " +
                    "LEFT JOIN " +
                    "book_worm_db.borrowed_books bb ON b.id = bb.book_id " +
                    "LEFT JOIN " +
                    "book_worm_db.patron p ON p.id = b.borrower " +
                    "WHERE " +
                    "p.id = ? " +
                    "AND bb.return_date > CURRENT_DATE;");
            statement.setInt(1, patron.getUserID());
            ResultSet resultSet = statement.executeQuery();
            int amount = 0;
            while (resultSet.next()) {
                amount = resultSet.getInt("count");
            }
            return amount;
        }
    }

    public int getGenreId(String genreName) throws SQLException {
        String query = "SELECT id FROM book_worm_db.genre WHERE genre = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, genreName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                throw new SQLException("Genre not found: " + genreName);
            }
        }
    }

    public Book donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron) throws SQLException {
        int genreId = getGenreId(genre);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO book_worm_db.books_donate(donated_by, title, author, isbn, year, publisher, page_count, genre_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, patron.getUserID());
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setLong(4, isbn);
            statement.setInt(5, year);
            statement.setString(6, publisher);
            statement.setInt(7, pageCount);
            statement.setInt(8, genreId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int bookId = resultSet.getInt(1);
                    return new Book(bookId, title, author, year, publisher, isbn, pageCount, genre);
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw e;
        }
    }
}
