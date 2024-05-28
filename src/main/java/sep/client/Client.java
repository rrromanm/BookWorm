package sep.client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import sep.file.FileLog;
import sep.model.Book;
import sep.model.Patron;
import sep.model.UserSession;
import sep.model.Event;
import sep.shared.ConnectorInterface;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The Client class represents a client-side application that interacts with the library system.
 * It implements the ClientInterface and Serializable interfaces and extends UnicastRemoteObject.
 * It also implements RemotePropertyChangeListener for listening to remote property change events.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class Client extends UnicastRemoteObject implements RemotePropertyChangeListener,ClientInterface, Serializable {
    private final ConnectorInterface library;
    private final PropertyChangeSupport support;
    private File file;
    private FileLog fileLog;

    /**
     * Constructor for the Client class.
     *
     * @param library The ConnectorInterface representing the library server.
     * @throws RemoteException If a remote communication-related exception occurs.
     * @throws SQLException    If a database access error occurs.
     * @throws IOException     If an I/O error occurs.
     */
    public Client(ConnectorInterface library) throws RemoteException, SQLException, IOException {
        this.library = library;
        this.support = new PropertyChangeSupport(this);
        this.library.addRemotePropertyChangeListener(this);
        this.file =  new File("src/main/java/sep/file/LibraryLog");
        this.fileLog = FileLog.getInstance(file);
    }

    /**
     * Retrieves all books from the library.
     *
     * @return ArrayList of Book objects.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public ArrayList<Book> getAllBooks() throws RemoteException {
        return library.getAllBooks();
    }

    /**
     * Retrieves all events from the library.
     *
     * @return ArrayList of Event objects.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public ArrayList<Event> getAllEvents() throws RemoteException {
        return library.getAllEvents();
    }

    /**
     * Retrieves the list of books borrowed by the specified patron.
     *
     * @param patron The Patron object representing the patron.
     * @return ArrayList of Book objects representing the borrowed books.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public ArrayList<Book> getBorrowedBooks(Patron patron) throws RemoteException {
        return library.getBorrowedBooks(patron);
    }

    /**
     * Retrieves the history of books borrowed by the specified patron.
     *
     * @param patron The Patron object representing the patron.
     * @return ArrayList of Book objects representing the history of borrowed books.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public ArrayList<Book> getHistoryOfBooks(Patron patron) throws RemoteException {
        return library.getHistoryOfBooks(patron);
    }

    /**
     * Retrieves the list of books wishlisted by the specified patron.
     *
     * @param patron The Patron object representing the patron.
     * @return ArrayList of Book objects representing the wishlisted books.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public ArrayList<Book> getWishlistedBooks(Patron patron) throws RemoteException {
        return library.getWishlistedBooks(patron);
    }

    /**
     * Retrieves the list of donated books.
     *
     * @return ArrayList of Book objects representing the donated books.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public ArrayList<Book> getDonatedBooks() throws RemoteException {
        return library.getDonatedBooks();
    }

    /**
     * Retrieves the amount of books read by the specified patron.
     *
     * @param patron The Patron object representing the patron.
     * @return The number of books read by the patron.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public int getAmountOfReadBooks(Patron patron) throws RemoteException {
        return library.getAmountOfReadBooks(patron);
    }


    /**
     * Retrieves the amount of books borrowed by the specified patron.
     *
     * @param patron The Patron object representing the patron.
     * @return The number of books borrowed by the patron.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public int getAmountOfBorrowedBooks(Patron patron) throws RemoteException {
        return library.getAmountOfBorrowedBooks(patron);
    }

    /**
     * Filters books based on the provided genre, state, and search criteria.
     *
     * @param genre  The genre of the books to filter.
     * @param state  The state of the books to filter.
     * @param search The search criteria to filter the books.
     * @return ArrayList of Book objects representing the filtered books.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public ArrayList<Book> filter(String genre, String state, String search) throws RemoteException {
        return library.filter(genre, state, search);
    }

    /**
     * Borrows a book for the specified patron.
     *
     * @param book   The Book object representing the book to borrow.
     * @param patron The Patron object representing the patron borrowing the book.
     * @throws IOException  If an I/O error occurs.
     * @throws SQLException If an SQL exception occurs.
     */
    @Override
    public void borrowBooks(Book book, Patron patron) throws IOException, SQLException {
        library.borrowBooks(book, patron);
        fileLog.log(patron.getUsername() + " has borrowed a \"" + book.getTitle() + "\"");
    }

    /**
     * Adds a book to the wishlist of the specified patron.
     *
     * @param book   The Book object representing the book to add to the wishlist.
     * @param patron The Patron object representing the patron adding the book to the wishlist.
     * @throws IOException  If an I/O error occurs.
     * @throws SQLException If an SQL exception occurs.
     */
    @Override
    public void wishlistBook(Book book, Patron patron) throws IOException, SQLException {
        library.wishlistBook(book, patron);
        fileLog.log(patron.getUsername() + " has added a \"" + book.getTitle() + "\" to the wishlist");
    }

    /**
     * Extends the borrowing period for the specified book by the specified patron.
     *
     * @param book   The Book object representing the book to extend the borrowing period for.
     * @param patron The Patron object representing the patron extending the borrowing period.
     * @throws RemoteException If a remote communication-related exception occurs.
     * @throws SQLException    If an SQL exception occurs.
     */
    @Override
    public void extendBook(Book book, Patron patron) throws RemoteException, SQLException {
        library.extendBook(book, patron);
        try {
            fileLog.log(patron.getUsername() + " has extended a \"" + book.getTitle() + "\"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Checks if the specified book is wishlisted by the given patron.
     *
     * @param book   The Book object to check for wishlisting.
     * @param patron The Patron object representing the patron.
     * @return True if the book is wishlisted by the patron, false otherwise.
     * @throws RemoteException If a remote communication-related exception occurs.
     * @throws SQLException    If an SQL exception occurs.
     */
    @Override
    public boolean isWishlisted(Book book, Patron patron) throws RemoteException, SQLException {
        return library.isWishlisted(book, patron);
    }

    /**
     * Returns a borrowed book to the library database.
     *
     * @param book   The Book object representing the book to return.
     * @param patron The Patron object representing the patron returning the book.
     * @throws SQLException If an SQL exception occurs.
     * @throws IOException  If an I/O error occurs.
     */
    @Override
    public void returnBookToDatabase(Book book, Patron patron) throws SQLException, IOException {
        library.returnBookToDatabase(book, patron);
        fileLog.log(patron.getUsername() + " has returned a \"" + book.getTitle() + "\"");
    }

    /**
     * Deletes a book from the library database.
     *
     * @param bookID     The ID of the book to delete.
     * @param title      The title of the book to delete.
     * @param author     The author of the book to delete.
     * @param year       The publication year of the book to delete.
     * @param publisher  The publisher of the book to delete.
     * @param isbn       The ISBN of the book to delete.
     * @param pageCount  The page count of the book to delete.
     * @param genre      The genre of the book to delete.
     * @throws SQLException If an SQL exception occurs.
     */
    @Override
    public void deleteBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException {
        try {
            library.deleteBook(bookID, title, author, year, publisher, isbn, pageCount, genre);
            fileLog.log(title + " has been deleted from the database by Admin");
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Creates a new book in the library database.
     *
     * @param title      The title of the book to create.
     * @param author     The author of the book to create.
     * @param year       The publication year of the book to create.
     * @param publisher  The publisher of the book to create.
     * @param isbn       The ISBN of the book to create.
     * @param pageCount  The page count of the book to create.
     * @param genre      The genre of the book to create.
     * @throws SQLException    If an SQL exception occurs.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void createBook(String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        try {
            library.createBook(title, author, year, publisher, isbn, pageCount, genre);
            fileLog.log(title + " has been added to the database by Admin");
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Updates information of an existing book in the library database.
     *
     * @param bookID     The ID of the book to update.
     * @param title      The updated title of the book.
     * @param author     The updated author of the book.
     * @param year       The updated publication year of the book.
     * @param publisher  The updated publisher of the book.
     * @param isbn       The updated ISBN of the book.
     * @param pageCount  The updated page count of the book.
     * @param genre      The updated genre of the book.
     * @throws SQLException    If an SQL exception occurs.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void updateBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        try {
            library.updateBook(bookID, title, author, year, publisher, isbn, pageCount, genre);
            fileLog.log(title + " has been updated in the database by Admin");
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    /**
     * Donates a book to the library.
     *
     * @param title      The title of the donated book.
     * @param author     The author of the donated book.
     * @param isbn       The ISBN of the donated book.
     * @param year       The publication year of the donated book.
     * @param publisher  The publisher of the donated book.
     * @param pageCount  The page count of the donated book.
     * @param genre      The genre of the donated book.
     * @param patron     The Patron object representing the patron who donates the book.
     * @throws SQLException    If an SQL exception occurs.
     * @throws RemoteException If a remote communication-related exception occurs.
     * @throws IOException     If an I/O error occurs.
     */
    @Override
    public void donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron) throws SQLException, RemoteException, IOException {
        library.donateBook(title, author, isbn, year, publisher, pageCount, genre, patron);
        fileLog.log(patron.getUsername() + " has donated a \"" + title + "\"");
    }

    /**
     * Deletes a book from the wishlist of the specified patron.
     *
     * @param book   The Book object representing the book to delete from the wishlist.
     * @param patron The Patron object representing the patron.
     * @throws SQLException If an SQL exception occurs.
     * @throws IOException  If an I/O error occurs.
     */
    @Override
    public void deleteFromWishlist(Book book, Patron patron) throws SQLException, IOException {
        library.deleteFromWishlist(book, patron);
        fileLog.log(patron.getUsername() + " has deleted a \"" + book.getTitle() + "\" from the wishlist");
    }

    /**
     * Retrieves a list of ending books for the specified patron.
     *
     * @param patron The Patron object representing the patron.
     * @return ArrayList of String objects representing the ending books.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public ArrayList<String> getEndingBooks(Patron patron) throws RemoteException {
        return library.getEndingBooks(patron);
    }

    /**
     * Approves a donated book in the library.
     *
     * @param id         The ID of the donated book to approve.
     * @param title      The title of the donated book.
     * @param author     The author of the donated book.
     * @param isbn       The ISBN of the donated book.
     * @param year       The publication year of the donated book.
     * @param publisher  The publisher of the donated book.
     * @param pageCount  The page count of the donated book.
     * @param genreId    The genre ID of the donated book.
     * @throws SQLException    If an SQL exception occurs.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void approveDonatedBook(int id, String title, String author, long isbn, int year, String publisher, int pageCount, String genreId) throws SQLException, RemoteException {
        library.approveDonatedBook(id, title, author, isbn, year, publisher, pageCount, genreId);
        try {
            fileLog.log("Donated book: " + title + " has been approved by Admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Rejects a donated book in the library.
     *
     * @param bookId The ID of the donated book to reject.
     * @throws SQLException    If an SQL exception occurs.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void rejectDonatedBook(int bookId) throws SQLException, RemoteException {
        library.rejectDonatedBook(bookId);
        try {
            fileLog.log("Donated book has been rejected by Admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new patron in the library database.
     *
     * @param username     The username of the new patron.
     * @param password     The password of the new patron.
     * @param first_name   The first name of the new patron.
     * @param last_name    The last name of the new patron.
     * @param email        The email of the new patron.
     * @param phone_number The phone number of the new patron.
     * @param fees         The fees of the new patron.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException {
        try {
            library.createPatron(username, password, first_name, last_name, email, phone_number, fees);
            fileLog.log("New user has been created: " + username);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Creates a new event in the library.
     *
     * @param title       The title of the new event.
     * @param description The description of the new event.
     * @param eventDate   The date of the new event.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void createEvent(String title, String description, String eventDate) throws RemoteException {
        try {
            library.createEvent(title, description, eventDate);
            fileLog.log("New event has been created by admin: " + title);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Deletes an event from the library.
     *
     * @param id The ID of the event to delete.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void deleteEvent(int id) throws RemoteException {
        try {
            library.deleteEvent(id);
            fileLog.log("Event has been deleted by admin");
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates an event in the library.
     *
     * @param id          The ID of the event to update.
     * @param title       The updated title of the event.
     * @param description The updated description of the event.
     * @param eventDate   The updated date of the event.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void updateEvent(int id, String title, String description, String eventDate) throws RemoteException {
        try {
            library.updateEvent(id, title, description, eventDate);
            fileLog.log("An event has been updated!");
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Deletes a patron from the library.
     *
     * @param id The ID of the patron to delete.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void deletePatron(int id) throws RemoteException {
        try {
            library.deletePatron(id);
            fileLog.log("Patron got deleted from database!");
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }


    /**
     * Logs in a patron with the specified username and password.
     *
     * @param username The username of the patron.
     * @param password The password of the patron.
     * @return The Patron object representing the logged-in user.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public Patron login(String username, String password) throws IOException {
        Patron userLoggedIn = library.login(username, password);
        UserSession.getInstance().setLoggedInUser(userLoggedIn);
        fileLog.log(userLoggedIn.getUsername() + " has logged in");
        return userLoggedIn;
    }

    /**
     * Logs in an administrator with the specified username and password.
     *
     * @param username The username of the administrator.
     * @param password The password of the administrator.
     * @return True if login is successful, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public boolean loginAsAdmin(String username, String password) throws IOException {
        fileLog.log("Admin has logged in");
        return library.loginAsAdmin(username, password);
    }

    /**
     * Updates the username of a patron with the specified userID.
     *
     * @param userID      The ID of the patron to update.
     * @param newUsername The new username to set.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void updateUsername(int userID, String newUsername) throws RemoteException {
        try {
            library.updateUsername(userID, newUsername);
            fileLog.log(newUsername + " has been updated");
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the email of a patron with the specified userID.
     *
     * @param userID   The ID of the patron to update.
     * @param newEmail The new email to set.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void updateEmail(int userID, String newEmail) throws RemoteException {
        try {
            library.updateEmail(userID, newEmail);
            fileLog.log(newEmail + " has been updated");
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }
    }


    /**
     * Updates the phone number of a patron with the specified userID.
     *
     * @param userID         The ID of the patron to update.
     * @param newPhoneNumber The new phone number to set.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void updatePhoneNumber(int userID, String newPhoneNumber) throws RemoteException {
        try {
            library.updatePhoneNumber(userID, newPhoneNumber);
            fileLog.log(newPhoneNumber + " has been updated");
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the first name of a patron with the specified userID.
     *
     * @param userID      The ID of the patron to update.
     * @param newFirstName The new first name to set.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void updateFirstName(int userID, String newFirstName) throws RemoteException {
        try {
            library.updateFirstName(userID, newFirstName);
            fileLog.log(newFirstName + " has been updated");
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the last name of a patron with the specified userID.
     *
     * @param userID     The ID of the patron to update.
     * @param newLastName The new last name to set.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void updateLastName(int userID, String newLastName) throws RemoteException {
        try {
            library.updateLastName(userID, newLastName);
            fileLog.log(newLastName + " has been updated");
        } catch (RemoteException e) {
            throw new RemoteException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the password of a patron with the specified userID.
     *
     * @param userID      The ID of the patron to update.
     * @param newPassword The new password to set.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void updatePassword(int userID, String newPassword) throws RemoteException {
        try {
            library.updatePassword(userID, newPassword);
        } catch (RemoteException e) {
            throw new RemoteException(e.getMessage());
        }
    }


    /**
     * Updates the fees of a patron with the specified userID.
     *
     * @param userID  The ID of the patron to update.
     * @param newFees The new fees to set.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void updateFees(int userID, int newFees) throws RemoteException {
        try {
            library.updateFees(userID, newFees);
            fileLog.log(newFees + " has been updated by Admin");
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Adds a property change listener to the client.
     *
     * @param listener The listener to be added.
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener from the client.
     *
     * @param listener The listener to be removed.
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }

    /**
     * Handles property change events by firing corresponding property change events on the support object.
     *
     * @param event The property change event.
     * @throws RemoteException If a remote communication-related exception occurs.
     */
    @Override
    public void propertyChange(RemotePropertyChangeEvent event) throws RemoteException {
        Platform.runLater(() -> {
            if (event.getPropertyName().equals("BorrowBook")) {
                this.support.firePropertyChange("BorrowBook", false, true);
            }
            if (event.getPropertyName().equals("ReturnBook")) {
                this.support.firePropertyChange("ReturnBook", false, true);
            }
            if (event.getPropertyName().equals("Wishlist")) {
                this.support.firePropertyChange("Wishlist", false, true);
            }
            if (event.getPropertyName().equals("BookDonate")) {
                this.support.firePropertyChange("BookDonate", false, true);
            }
            if (event.getPropertyName().equals("createPatron")) {
                this.support.firePropertyChange("createPatron", false, true);
            }
            if (event.getPropertyName().equals("DonatedBookApproved")) {
                this.support.firePropertyChange("DonatedBookApproved", false, true);
            }
            if (event.getPropertyName().equals("DonatedBookRejected")) {
                this.support.firePropertyChange("DonatedBookRejected", false, true);
            }
            if (event.getPropertyName().equals("removeBook")) {
                this.support.firePropertyChange("removeBook", false, true);
            }
            if (event.getPropertyName().equals("updateBook")) {
                this.support.firePropertyChange("updateBook", false, true);
            }
            if (event.getPropertyName().equals("createBook")) {
                this.support.firePropertyChange("createBook", false, true);
            }
            if (event.getPropertyName().equals("updatePatron")) {
                this.support.firePropertyChange("updatePatron", false, true);
            }
            if (event.getPropertyName().equals("ExtendBook")) {
                this.support.firePropertyChange("ExtendBook", false, true);
            }
            if (event.getPropertyName().equals("CreateEvent")) {
                this.support.firePropertyChange("CreateEvent", false, true);
            }
            if (event.getPropertyName().equals("UpdateEvent")) {
                this.support.firePropertyChange("UpdateEvent", false, true);
            }
            if (event.getPropertyName().equals("DeleteEvent")) {
                this.support.firePropertyChange("DeleteEvent", false, true);
            }
            if (event.getPropertyName().equals("login")) {
                this.support.firePropertyChange("login", false, true);
            }
        });
    }

}
