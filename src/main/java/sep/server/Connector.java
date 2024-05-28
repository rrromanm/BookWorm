package sep.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import sep.jdbc.AdminDatabaseImplementation;
import sep.jdbc.BookDatabaseImplementation;
import sep.jdbc.PatronDatabaseImplementation;
import sep.model.Book;
import sep.model.Event;
import sep.model.Patron;
import sep.shared.ConnectorInterface;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * The Connector class implements the ConnectorInterface, providing methods for interacting with the application's database and performing various operations related to books, events, and patrons.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class Connector implements ConnectorInterface {
    private BookDatabaseImplementation bookDatabase;
    private PatronDatabaseImplementation patronDatabase;
    private AdminDatabaseImplementation adminDatabase;
    private final RemotePropertyChangeSupport support;

    /**
     * Constructs a Connector object and initializes database implementations and support for remote property change listeners.
     */
    public Connector() {
        try
        {
            this.bookDatabase = BookDatabaseImplementation.getInstance();
            this.patronDatabase = PatronDatabaseImplementation.getInstance();
            this.adminDatabase = AdminDatabaseImplementation.getInstance();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        this.support = new RemotePropertyChangeSupport<>();
    }

    /**
     * Retrieves all books from the database.
     *
     * @return An ArrayList of Book objects representing all books in the database.
     * @throws RuntimeException If an SQL exception occurs during database access.
     */
    @Override
    public synchronized ArrayList<Book> getAllBooks() {
        try {
            return this.bookDatabase.readBooks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the borrowed books of a specific patron from the database.
     *
     * @param patron The patron whose borrowed books are to be retrieved.
     * @return An ArrayList of Book objects representing the borrowed books of the specified patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RuntimeException If an SQL exception occurs during database access.
     */
    @Override
    public ArrayList<Book> getBorrowedBooks(Patron patron) throws RemoteException {
        try {
            return this.bookDatabase.readBorrowedBook(patron);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the history of borrowed books of a specific patron from the database.
     *
     * @param patron The patron whose borrowed book history is to be retrieved.
     * @return An ArrayList of Book objects representing the history of borrowed books of the specified patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RuntimeException If an SQL exception occurs during database access.
     */
    @Override
    public ArrayList<Book> getHistoryOfBooks(Patron patron) throws RemoteException {
        try {
            return this.bookDatabase.readHistoryOfBooks(patron);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Retrieves the wishlisted books of a specific patron from the database.
     *
     * @param patron The patron whose wishlisted books are to be retrieved.
     * @return An ArrayList of Book objects representing the wishlisted books of the specified patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RuntimeException If an SQL exception occurs during database access.
     */
    @Override
    public ArrayList<Book> getWishlistedBooks(Patron patron) throws RemoteException {
        try {
            return this.bookDatabase.readWishlistedBooks(patron);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all donated books from the database.
     *
     * @return An ArrayList of Book objects representing all donated books in the database.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RuntimeException If an SQL exception occurs during database access.
     */
    @Override
    public ArrayList<Book> getDonatedBooks() throws RemoteException {
        try {
            return this.bookDatabase.readDonatedBooks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the amount of books read by a specific patron from the database.
     *
     * @param patron The patron whose amount of read books is to be retrieved.
     * @return The number of books read by the specified patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RuntimeException If an SQL exception occurs during database access.
     */
    @Override
    public int getAmountOfReadBooks(Patron patron) throws RemoteException {
        try {
            return this.bookDatabase.readAmountOfBooksRead(patron);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Retrieves the amount of books borrowed by a specific patron from the database.
     *
     * @param patron The patron whose amount of borrowed books is to be retrieved.
     * @return The number of books borrowed by the specified patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RuntimeException If an SQL exception occurs during database access.
     */
    @Override
    public int getAmountOfBorrowedBooks(Patron patron) throws RemoteException {
        try {
            return this.bookDatabase.readAmountOfBorrowedBooks(patron);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new patron with the specified details in the database.
     *
     * @param username The username of the new patron.
     * @param password The password of the new patron.
     * @param first_name The first name of the new patron.
     * @param last_name The last name of the new patron.
     * @param email The email address of the new patron.
     * @param phone_number The phone number of the new patron.
     * @param fees The fees associated with the new patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RuntimeException If an SQL exception occurs during database access.
     */
    @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException {
        try {
            this.patronDatabase.createPatron(username, password, first_name, last_name, email, phone_number);
            this.support.firePropertyChange("createPatron", false, true);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Creates a new event with the specified details in the database.
     *
     * @param title The title of the new event.
     * @param description The description of the new event.
     * @param date The date of the new event.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RemoteException If an SQL exception occurs during database access.
     */
    @Override
    public void createEvent(String title, String description, String date) throws RemoteException {
        try {
            this.adminDatabase.createEvent(title, description, date);
            this.support.firePropertyChange("CreateEvent", false, true);
        } catch (SQLException e) {
            throw new RemoteException("Failed to create event: " + e.getMessage());
        }
    }

    /**
     * Deletes the event with the specified ID from the database.
     *
     * @param id The ID of the event to be deleted.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RemoteException If an SQL exception occurs during database access.
     */
    @Override
    public void deleteEvent(int id) throws RemoteException {
        try {
            this.adminDatabase.deleteEvent(id);
            this.support.firePropertyChange("DeleteEvent", false, true);
        } catch (SQLException e) {
            throw new RemoteException("Failed to delete event: " + e.getMessage());
        }
    }

    /**
     * Deletes the patron with the specified ID from the database.
     *
     * @param id The ID of the patron to be deleted.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RemoteException If an SQL exception occurs during database access.
     */
    @Override
    public void deletePatron(int id) throws RemoteException {
        try {
            this.adminDatabase.deletePatron(id);
        } catch (SQLException e) {
            throw new RemoteException("Failed to delete patron: " + e.getMessage());
        }
    }

    /**
     * Updates the event with the specified ID in the database.
     *
     * @param id The ID of the event to be updated.
     * @param title The new title of the event.
     * @param description The new description of the event.
     * @param eventDate The new date of the event.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws SQLException If an SQL exception occurs during database access.
     */
    @Override
    public void updateEvent(int id, String title, String description, String eventDate) throws SQLException, RemoteException {
        try {
            this.adminDatabase.updateEvent(id, title, description, eventDate);
            this.support.firePropertyChange("UpdateEvent", false, true);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Creates a new book with the specified details in the database.
     *
     * @param title The title of the new book.
     * @param author The author of the new book.
     * @param year The publication year of the new book.
     * @param publisher The publisher of the new book.
     * @param isbn The ISBN of the new book.
     * @param pageCount The page count of the new book.
     * @param genre The genre of the new book.
     * @throws SQLException If an SQL exception occurs during database access.
     * @throws RemoteException If a remote exception occurs during the process.
     */
    @Override
    public void createBook(String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        try {
            this.bookDatabase.createBook(title, author, year, publisher, isbn, pageCount, genre);
            this.support.firePropertyChange("createBook", false, true);
        } catch (SQLException | RemoteException e) {
            throw new SQLException(e);
        }
    }


    /**
     * Updates the details of a book in the database.
     *
     * @param bookID The ID of the book to be updated.
     * @param title The new title of the book.
     * @param author The new author of the book.
     * @param year The new publication year of the book.
     * @param publisher The new publisher of the book.
     * @param isbn The new ISBN of the book.
     * @param pageCount The new page count of the book.
     * @param genre The new genre of the book.
     * @throws SQLException If an SQL exception occurs during database access.
     * @throws RemoteException If a remote exception occurs during the process.
     */
    @Override
    public void updateBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        try {
            this.bookDatabase.updateBook(bookID, title, author, year, publisher, isbn, pageCount, genre);
            this.support.firePropertyChange("updateBook", false, true);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Retrieves a list of books from the database based on the provided filter criteria.
     *
     * @param genre The genre to filter by.
     * @param state The state to filter by.
     * @param search The search string to filter by.
     * @return An ArrayList of Book objects that match the filter criteria.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws RuntimeException If an SQL exception occurs during database access.
     */
    @Override
    public synchronized ArrayList<Book> filter(String genre, String state, String search) {
        try {
            return this.bookDatabase.filter(genre, state, search);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Authenticates a patron with the provided username and password.
     *
     * @param username The username of the patron.
     * @param password The password of the patron.
     * @return The authenticated patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws IllegalArgumentException If the account does not exist.
     */
    @Override
    public Patron login(String username, String password) throws RemoteException {
        try {
            support.firePropertyChange("login", false, true);
            return patronDatabase.login(username, password);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Account doesn't exist.");
        }
    }

    /**
     * Authenticates an administrator with the provided username and password.
     *
     * @param username The username of the administrator.
     * @param password The password of the administrator.
     * @return True if authentication is successful, false otherwise.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws IllegalArgumentException If the account does not exist.
     */
    @Override
    public boolean loginAsAdmin(String username, String password) throws RemoteException {
        try {
            return patronDatabase.loginAsAdmin(username, password);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Account doesn't exist.");
        }
    }


    /**
     * Updates the username of a patron in the database.
     *
     * @param userID The ID of the patron whose username will be updated.
     * @param newUsername The new username for the patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws IllegalArgumentException If the operation fails due to an invalid argument.
     */
    @Override
    public void updateUsername(int userID, String newUsername) throws RemoteException {
        try {
            patronDatabase.updateUsername(userID, newUsername);
            this.support.firePropertyChange("updatePatron", false, true);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Updates the email address of a patron in the database.
     *
     * @param userID The ID of the patron whose email will be updated.
     * @param newEmail The new email address for the patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws IllegalArgumentException If the operation fails due to an invalid argument.
     */
    @Override
    public void updateEmail(int userID, String newEmail) throws RemoteException {
        try {
            patronDatabase.updateEmail(userID, newEmail);
            this.support.firePropertyChange("updatePatron", false, true);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Updates the phone number of a patron in the database.
     *
     * @param userID The ID of the patron whose phone number will be updated.
     * @param newPhoneNumber The new phone number for the patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws IllegalArgumentException If the operation fails due to an invalid argument.
     */
    @Override
    public void updatePhoneNumber(int userID, String newPhoneNumber) throws RemoteException {
        try {
            patronDatabase.updatePhone(userID, newPhoneNumber);
            this.support.firePropertyChange("updatePatron", false, true);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Updates the first name of a patron in the database.
     *
     * @param userID The ID of the patron whose first name will be updated.
     * @param newFirstName The new first name for the patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws IllegalArgumentException If the operation fails due to an invalid argument.
     */
    @Override
    public void updateFirstName(int userID, String newFirstName) throws RemoteException {
        try {
            patronDatabase.updateFirstName(userID, newFirstName);
            this.support.firePropertyChange("updatePatron", false, true);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    /**
     * Updates the last name of a patron in the database.
     *
     * @param userID The ID of the patron whose last name will be updated.
     * @param newLastName The new last name for the patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws IllegalArgumentException If the operation fails due to an invalid argument.
     */
    @Override
    public void updateLastName(int userID, String newLastName) throws RemoteException {
        try {
            patronDatabase.updateLastName(userID, newLastName);
            this.support.firePropertyChange("updatePatron", false, true);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Updates the password of a patron in the database.
     *
     * @param userID The ID of the patron whose password will be updated.
     * @param newPassword The new password for the patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws IllegalArgumentException If the operation fails due to an invalid argument.
     */
    @Override
    public void updatePassword(int userID, String newPassword) throws RemoteException {
        try {
            patronDatabase.updatePassword(userID, newPassword);
            this.support.firePropertyChange("updatePatron", false, true);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Updates the fees of a patron in the database.
     *
     * @param userID The ID of the patron whose fees will be updated.
     * @param newFees The new fees for the patron.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws IllegalArgumentException If the operation fails due to an invalid argument.
     */
    @Override
    public void updateFees(int userID, int newFees) throws RemoteException {
        try {
            patronDatabase.updateFees(userID, newFees);
            this.support.firePropertyChange("updatePatron", false, true);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Borrows a book for a patron.
     *
     * @param book The book to be borrowed.
     * @param patron The patron who borrows the book.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws SQLException If an SQL exception occurs.
     */
    @Override
    public void borrowBooks(Book book, Patron patron) throws RemoteException, SQLException {
        bookDatabase.borrowBook(book, patron);
        this.support.firePropertyChange("BorrowBook", null, book);
    }

    /**
     * Adds a book to a patron's wishlist.
     *
     * @param book The book to be added to the wishlist.
     * @param patron The patron who adds the book to the wishlist.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws SQLException If an SQL exception occurs.
     */
    @Override
    public void wishlistBook(Book book, Patron patron) throws RemoteException, SQLException {
        bookDatabase.wishlistBook(book, patron);
        this.support.firePropertyChange("Wishlist", false, true);
    }


    /**
     * Extends the borrowing period of a book for a patron.
     *
     * @param book The book to be extended.
     * @param patron The patron who extends the book.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws SQLException If an SQL exception occurs.
     */
    @Override
    public void extendBook(Book book, Patron patron) throws RemoteException, SQLException {
        bookDatabase.extendBook(book, patron);
        this.support.firePropertyChange("ExtendBook", false, true);
    }

    /**
     * Checks if a book is wishlisted by a patron.
     *
     * @param book The book to check.
     * @param patron The patron to check for.
     * @return {@code true} if the book is wishlisted by the patron, {@code false} otherwise.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws SQLException If an SQL exception occurs.
     */
    @Override
    public boolean isWishlisted(Book book, Patron patron) throws RemoteException, SQLException {
        return bookDatabase.isWishlisted(book, patron);
    }

    /**
     * Returns a borrowed book to the database.
     *
     * @param book The book to be returned.
     * @param patron The patron who returns the book.
     * @throws SQLException If an SQL exception occurs.
     * @throws RemoteException If a remote exception occurs during the process.
     */
    @Override
    public void returnBookToDatabase(Book book, Patron patron) throws SQLException, RemoteException {
        bookDatabase.returnBookToDatabase(book, patron);
        this.support.firePropertyChange("ReturnBook", null, book);
    }

    /**
     * Deletes a book from the database.
     *
     * @param bookID The ID of the book to be deleted.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param year The year of publication of the book.
     * @param publisher The publisher of the book.
     * @param isbn The ISBN of the book.
     * @param pageCount The page count of the book.
     * @param genre The genre of the book.
     * @throws SQLException If an SQL exception occurs.
     */
    @Override
    public void deleteBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException {
        try {
            bookDatabase.deleteBook(bookID, title, author, year, publisher, isbn, pageCount, genre);
            this.support.firePropertyChange("removeBook", false, true);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Deletes a book from a patron's wishlist.
     *
     * @param book The book to be deleted from the wishlist.
     * @param patron The patron whose wishlist will be updated.
     * @throws RemoteException If a remote exception occurs during the process.
     * @throws SQLException If an SQL exception occurs.
     */
    @Override
    public void deleteFromWishlist(Book book, Patron patron) throws RemoteException, SQLException {
        bookDatabase.deleteFromWishlist(book, patron);
    }


    /**
     * Donates a book to the library.
     *
     * @param title The title of the book.
     * @param author The author of the book.
     * @param isbn The ISBN of the book.
     * @param year The year of publication of the book.
     * @param publisher The publisher of the book.
     * @param pageCount The page count of the book.
     * @param genre The genre of the book.
     * @param patron The patron who donates the book.
     * @return The donated book.
     * @throws SQLException If an SQL exception occurs.
     * @throws RemoteException If a remote exception occurs during the process.
     */
    @Override
    public Book donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron) throws SQLException, RemoteException {
        this.support.firePropertyChange("BookDonate", false, true);
        return bookDatabase.donateBook(title, author, isbn, year, publisher, pageCount, genre, patron);
    }

    /**
     * Retrieves all events from the database.
     *
     * @return The list of all events.
     * @throws RemoteException If a remote exception occurs during the process.
     */
    @Override
    public ArrayList<Event> getAllEvents() throws RemoteException {
        try {
            return this.adminDatabase.getAllEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the list of books ending soon for a patron.
     *
     * @param patron The patron to retrieve the list for.
     * @return The list of books ending soon.
     * @throws RemoteException If a remote exception occurs during the process.
     */
    @Override
    public ArrayList<String> getEndingBooks(Patron patron) throws RemoteException {
        try {
            return this.bookDatabase.checkEndingBooks(patron);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Approves a donated book for inclusion in the library.
     *
     * @param id The ID of the donated book to approve.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param isbn The ISBN of the book.
     * @param year The year of publication of the book.
     * @param publisher The publisher of the book.
     * @param pageCount The page count of the book.
     * @param genreId The genre ID of the book.
     * @throws SQLException If an SQL exception occurs.
     * @throws RemoteException If a remote exception occurs during the process.
     */
    public void approveDonatedBook(int id, String title, String author, long isbn, int year, String publisher, int pageCount, String genreId) throws SQLException, RemoteException {
        bookDatabase.approveDonatedBook(id, title, author, isbn, year, publisher, pageCount, genreId);
        this.support.firePropertyChange("DonatedBookApproved", false, true);
    }


    /**
     * Rejects a donated book.
     *
     * @param bookId The ID of the donated book to reject.
     * @throws SQLException If an SQL exception occurs.
     * @throws RemoteException If a remote exception occurs during the process.
     */
    @Override
    public void rejectDonatedBook(int bookId) throws SQLException, RemoteException {
        bookDatabase.rejectDonatedBook(bookId);
        this.support.firePropertyChange("DonatedBookRejected", false,true);
    }

    /**
     * Adds a remote property change listener.
     *
     * @param listener The listener to add.
     * @throws RemoteException If a remote exception occurs during the process.
     */
    @Override
    public void addRemotePropertyChangeListener(RemotePropertyChangeListener listener) throws RemoteException {
        this.support.addPropertyChangeListener(listener);
    }

}
