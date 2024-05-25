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



        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=book_worm_db", "postgres", "via"); //TODO: YOU NEED TO CHANGE THIS PASSWORD ON WHO IS WORKING ON CODE RN



    }

    public void createBook(String title, String author,String year, String publisher, String isbn, String pageCount, String genre) throws SQLException {
        try (Connection connection = getConnection();){
            int genreId = getGenreId(genre);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO book_worm_db.books(title, isbn, year, publisher, page_count, genre_id, borrower, state, authors) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, title);
            statement.setString(2, isbn);
            statement.setString(3, year);
            statement.setString(4, publisher);
            statement.setString(5, pageCount);
            statement.setInt(6, genreId);
            statement.setNull(7, Types.NULL);
            statement.setString(8, "Available");
            statement.setString(9, author);
            statement.executeUpdate();

        }catch (SQLException e) {
            throw new SQLException("Error adding book: " + e.getMessage());
        }
    }

    @Override
    public void deleteBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException {
        try (Connection connection = getConnection()) {
            int genreId = getGenreId(genre);

            // Delete from wishlist
            String deleteWishlistQuery = "DELETE FROM wishlist WHERE book_id = ?";
            try (PreparedStatement wishlistStatement = connection.prepareStatement(deleteWishlistQuery)) {
                wishlistStatement.setInt(1, bookID);
                wishlistStatement.executeUpdate();
            }

            // Delete from borrowed_books
            String deleteBorrowedBooksQuery = "DELETE FROM borrowed_books WHERE book_id = ?";
            try (PreparedStatement borrowedBooksStatement = connection.prepareStatement(deleteBorrowedBooksQuery)) {
                borrowedBooksStatement.setInt(1, bookID);
                borrowedBooksStatement.executeUpdate();
            }

            // Delete from books
            String deleteBooksQuery = "DELETE FROM books WHERE id = ?";
            try (PreparedStatement booksStatement = connection.prepareStatement(deleteBooksQuery)) {
                booksStatement.setInt(1, bookID);
                booksStatement.executeUpdate();
            }
        }
    }


    @Override
    public void updateBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException {
        try (Connection connection = getConnection()) {
            int genreId = getGenreId(genre);
            String sqlQuery = "UPDATE book_worm_db.books " +
                    "SET title = ?, authors = ?, year = ?, publisher = ?, isbn = ?, page_count = ?, genre_id = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, year);
            statement.setString(4, publisher);
            statement.setString(5, isbn);
            statement.setString(6, pageCount);
            statement.setInt(7, genreId);
            statement.setInt(8, bookID);
            statement.executeUpdate();
        }
    }



    public ArrayList<Book> filter(String state, String genres, String search) throws SQLException {
        try (Connection connection = getConnection()) {
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
                sqlQuery.append(" AND books.state = ?");
            }
            if (!"All".equals(genres)) {
                sqlQuery.append(" AND g.genre = ?");
            }
            if (!search.isEmpty()) {
                sqlQuery.append(" AND LOWER(books.title) LIKE LOWER(?)");
            }

            PreparedStatement statement = connection.prepareStatement(sqlQuery.toString());

            int parameterIndex = 1;
            if (!"All".equals(state)) {
                statement.setString(parameterIndex++, state);
            }
            if (!"All".equals(genres)) {
                statement.setString(parameterIndex++, genres);
            }
            if (!search.isEmpty()) {
                statement.setString(parameterIndex++, "%" + search.toLowerCase() + "%");
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

                State bookState = null;
                if ("Available".equalsIgnoreCase(state2)) {
                    bookState = new Available();
                } else if ("Borrowed".equalsIgnoreCase(state2)) {
                    bookState = new Borrowed();
                }

                Book book = new Book(id, title, author, year, publisher, isbn, pageCount, genre);
                book.setState(bookState);
                if (username != null) {
                    Patron patron = new Patron(borrowerId, firstname, lastname, username, password, email, phoneNumber, fee);
                    book.setBorrower(patron);
                }

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
                    "    g.genre AS genre_name, " +
                        "TO_CHAR(bb.return_date, 'DD/MM/YYYY') as return_date " +
                    "FROM" +
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
                String returnDate = resultSet.getString("return_date");

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
                book.setReturnDate(returnDate);
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
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM book_worm_db.genre WHERE genre = ?")) {
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

    @Override public ArrayList<String> checkEndingBooks(Patron patron) throws SQLException
    {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT b.title " +
                    "FROM book_worm_db.books b " +
                    "LEFT JOIN book_worm_db.borrowed_books bb ON b.id = bb.book_id " +
                    "LEFT JOIN book_worm_db.patron p ON p.id = bb.profile_id " +
                    "WHERE p.id = ? " +
                    "AND bb.return_date IS NOT NULL " +
                    "AND bb.return_date <= CURRENT_DATE + INTERVAL '1 week' " +
                    "AND bb.return_date > CURRENT_DATE;");
            statement.setInt(1, patron.getUserID());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> titles = new ArrayList<>();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                titles.add(title);
            }
            return titles;
        }
    }

    @Override
    public void extendBook(Book book, Patron patron) {
        try (Connection connection = getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(
                     "SELECT id FROM book_worm_db.borrowed_books " +
                             "WHERE book_id = ? AND profile_id = ? AND return_date > CURRENT_DATE;");
             PreparedStatement updateStatement = connection.prepareStatement(
                     "UPDATE book_worm_db.borrowed_books " +
                             "SET return_date = return_date + interval '10 days' " +
                             "WHERE id = ?;")) {

            selectStatement.setInt(1, book.getBookId());
            selectStatement.setInt(2, patron.getUserID());

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int borrowingId = resultSet.getInt("id");

                updateStatement.setInt(1, borrowingId);
                updateStatement.executeUpdate();
            } else {
                throw new RuntimeException("No active borrowing record found for this book and user.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void approveDonatedBook(int id,String title, String author, long isbn, int year, String publisher, int pageCount, String genreId) throws SQLException {
        int genre_id = getGenreId(genreId);

        try (Connection connection = getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(
                     "INSERT INTO book_worm_db.books (title, authors, ISBN, year, publisher, page_count, genre_id, state) VALUES (?, ?, ?, ?, ?, ?, ?, 'Available')",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false);

            insertStatement.setString(1, title);
            insertStatement.setString(2, author);
            insertStatement.setLong(3, isbn);
            insertStatement.setInt(4, year);
            insertStatement.setString(5, publisher);
            insertStatement.setInt(6, pageCount);
            insertStatement.setInt(7, genre_id);
            insertStatement.executeUpdate();

            ResultSet resultSet = insertStatement.getGeneratedKeys();
            int generatedId = -1;
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }

            if (generatedId != -1) {
                try (PreparedStatement deleteStatement = connection.prepareStatement(
                        "DELETE FROM book_worm_db.books_donate WHERE books_donate.id = ?")) {
                    deleteStatement.setInt(1, id);
                    deleteStatement.executeUpdate();

                    connection.commit();
                }
            } else {
                connection.rollback();
                throw new SQLException("Failed to retrieve the generated ID for the inserted book.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rejectDonatedBook(int bookId){
        try (Connection connection = getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(
                        "DELETE FROM book_worm_db.books_donate WHERE books_donate.id = ?")) {
                    deleteStatement.setInt(1, bookId);
                    deleteStatement.executeUpdate();

                } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
