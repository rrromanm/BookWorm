package sep.model;

import sep.client.Client;
import sep.client.ClientInterface;
import sep.model.validators.*;
import sep.shared.ConnectorInterface;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The ModelManager class implements the Model interface and serves as a manager for interacting with the underlying data model
 * via a remote client interface. It provides methods for accessing and manipulating various entities such as books, events, and patrons.
 * Additionally, it handles property change events and acts as a bridge between the client and the underlying data source.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */

public class ModelManager extends UnicastRemoteObject implements Model , PropertyChangeListener {
    private final ClientInterface client;
    private String error;
    private final PropertyChangeSupport support;

    /**
     * Constructs a ModelManager object with the specified connector interface to the underlying data source.
     *
     * @param library The connector interface providing access to the underlying data source
     * @throws IOException  If an I/O error occurs
     * @throws SQLException If a SQL error occurs
     */
    public ModelManager(ConnectorInterface library) throws IOException, SQLException {
        this.client = new Client(library);
        this.error = "";
        this.client.addPropertyChangeListener(this);
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * Retrieves all books from the data source.
     *
     * @return An ArrayList containing all books
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public ArrayList<Book> getAllBooks() throws RemoteException {
        return client.getAllBooks();
    }

    /**
     * Retrieves all books borrowed by the specified patron.
     *
     * @param patron The patron whose borrowed books are to be retrieved
     * @return An ArrayList containing all borrowed books of the specified patron
     * @throws RemoteException If a remote communication error occurs
     */
    @Override public ArrayList<Book> getBorrowedBooks(Patron patron)
        throws RemoteException
    {
        return client.getBorrowedBooks(patron);
    }

    /**
     * Retrieves history of books by the specified patron.
     *
     * @param patron The patron whose history of books are to be retrieved
     * @return An ArrayList containing all books of the specified patron
     * @throws RemoteException If a remote communication error occurs
     */
    @Override public ArrayList<Book> getHistoryOfBooks(Patron patron)
        throws RemoteException
    {
        return client.getHistoryOfBooks(patron);
    }

    /**
     * Retrieves books wishlist by the specified patron.
     *
     * @param patron The patron whose wishlist of books are to be retrieved
     * @return An ArrayList containing all wishlisted books of the specified patron
     * @throws RemoteException If a remote communication error occurs
     */
    @Override public ArrayList<Book> getWishlistedBooks(Patron patron)
        throws RemoteException
    {
        return client.getWishlistedBooks(patron);
    }

    /**
     * Retrieves all donated books.
     *
     * @return An ArrayList containing all donated books
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public ArrayList<Book> getDonatedBooks() throws RemoteException {
        return client.getDonatedBooks();
    }

    /**
     * Retrieves all events.
     *
     * @return An ArrayList containing all events
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public ArrayList<Event> getAllEvents() throws RemoteException {
        return client.getAllEvents();
    }

    /**
     * Retrieves amount of book read by the specified patron.
     *
     * @param patron The patron whose amount of read books are to be retrieved
     * @return An integer of all read book by specified patron
     * @throws RemoteException If a remote communication error occurs
     */
    @Override public int getAmountOfReadBooks(Patron patron)
        throws RemoteException
    {
        return client.getAmountOfReadBooks(patron);
    }

    /**
     * Retrieves amount of borrowed books by the specified patron.
     *
     * @param patron The patron whose amount of borrowed books are to be retrieved
     * @return An integer of all borrowed books by specified patron
     * @throws RemoteException If a remote communication error occurs
     */
    @Override public int getAmountOfBorrowedBooks(Patron patron)
        throws RemoteException
    {
        return client.getAmountOfBorrowedBooks(patron);
    }


    /**
     * Adds a property change listener to this model.
     *
     * @param listener The property change listener to be added
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener from this model.
     *
     * @param listener The property change listener to be removed
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }

    /**
     * Creates a new patron with the provided information.
     *
     * @param username     The username of the patron
     * @param password     The password of the patron
     * @param first_name   The first name of the patron
     * @param last_name    The last name of the patron
     * @param email        The email address of the patron
     * @param phone_number The phone number of the patron
     * @param fees         The fees associated with the patron's account
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException
    {
        try
        {

            UsernameValidator.validate(username);
            EmailValidator.validate(email);
            PasswordValidator.validate(password);
            PhoneValidator.validate(phone_number);
            NameValidator.validate(first_name);
            NameValidator.validate(last_name);

            this.client.createPatron(username,  password,  first_name,  last_name,  email,  phone_number,  fees);

        }
        catch (Exception e)
        {
            error = e.getMessage();
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Creates a new event with the provided information.
     *
     * @param title     The username of the patron
     * @param description     The password of the patron
     * @param date   The first name of the patron
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void createEvent(String title, String description, String date) throws RemoteException {
        try
        {
            this.client.createEvent(title, description, date);

        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Deletes the event with the specified ID.
     *
     * @param id The ID of the event to be deleted
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void deleteEvent(int id) throws RemoteException {
        try
        {
            this.client.deleteEvent(id);

        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the event with the specified ID with the provided details.
     *
     * @param id          The ID of the event to be updated
     * @param title       The new title for the event
     * @param description The new description for the event
     * @param eventDate   The new date for the event
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void updateEvent(int id, String title, String description, String eventDate) throws RemoteException {
        try{
            this.client.updateEvent( id,  title,  description,  eventDate);
        } catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Deletes the patron with the specified ID.
     *
     * @param id The ID of the patron to be deleted
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void deletePatron(int id) throws RemoteException {
        try
        {
            this.client.deletePatron(id);

        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
    }


    /**
     * Filters books based on the provided genre, state, and search criteria.
     *
     * @param genre  The genre to filter by
     * @param state  The state to filter by
     * @param search The search criteria to filter by
     * @return An ArrayList containing the filtered books
     * @throws RemoteException If a remote communication error occurs
     */
    public ArrayList<Book> filter(String genre,String state, String search) throws RemoteException {
        return client.filter(genre,state,search);
    }

    /**
     * Allows a patron to borrow a book.
     *
     * @param book   The book to be borrowed
     * @param patron The patron borrowing the book
     * @throws IOException  If an I/O error occurs
     * @throws SQLException If a SQL error occurs
     */
    @Override
    public void borrowBooks(Book book, Patron patron)
        throws IOException, SQLException {
        client.borrowBooks(book,patron);
    }

    /**
     * Adds a book to the wishlist of a patron.
     *
     * @param book   The book to be added to the wishlist
     * @param patron The patron whose wishlist the book will be added to
     * @throws IOException  If an I/O error occurs
     * @throws SQLException If a SQL error occurs
     */
    @Override public void wishlistBook(Book book, Patron patron)
        throws IOException, SQLException
    {
        client.wishlistBook(book,patron);
    }

    /**
     * Extends the borrowing period for a book by a patron.
     *
     * @param book   The book for which the borrowing period will be extended
     * @param patron The patron who is extending the borrowing period
     * @throws RemoteException If a remote communication error occurs
     * @throws SQLException    If a SQL error occurs
     */
    @Override
    public void extendBook(Book book, Patron patron) throws RemoteException, SQLException {
        client.extendBook(book, patron);
    }

    /**
     * Checks if a book is wishlisted by a patron.
     *
     * @param book   The book to check for being wishlisted
     * @param patron The patron whose wishlist is being checked
     * @return true if the book is wishlisted by the patron, false otherwise
     * @throws RemoteException If a remote communication error occurs
     * @throws SQLException    If a SQL error occurs
     */
    @Override public boolean isWishlisted(Book book, Patron patron)
        throws RemoteException, SQLException
    {
        return client.isWishlisted(book,patron);
    }

    /**
     * Returns a borrowed book to the database, marking it as returned by the patron.
     *
     * @param book   The book to be returned to the database
     * @param patron The patron who is returning the book
     * @throws IOException  If an I/O error occurs
     * @throws SQLException If a SQL error occurs
     */
    @Override
    public void returnBookToDatabase(Book book, Patron patron)
        throws IOException, SQLException {
        client.returnBookToDatabase(book,patron);
    }

    /**
     * Donates a book to the library.
     *
     * @param title       The title of the book to be donated
     * @param author      The author of the book to be donated
     * @param isbn        The ISBN of the book to be donated
     * @param year        The publication year of the book to be donated
     * @param publisher   The publisher of the book to be donated
     * @param pageCount   The page count of the book to be donated
     * @param genre       The genre of the book to be donated
     * @param patron      The patron who is donating the book
     * @throws SQLException If a SQL error occurs
     * @throws IOException  If an I/O error occurs
     */
    @Override
    public void donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron)
        throws SQLException, IOException
    {
        client.donateBook(title, author, isbn, year, publisher, pageCount, genre, patron);
    }


    /**
     * Removes a book from the wishlist of a patron.
     *
     * @param book   The book to be removed from the wishlist
     * @param patron The patron whose wishlist the book will be removed from
     * @throws IOException  If an I/O error occurs
     * @throws SQLException If a SQL error occurs
     */
    @Override public void deleteFromWishlist(Book book, Patron patron)
        throws IOException, SQLException
    {
        client.deleteFromWishlist(book,patron);
    }

    /**
     * Retrieves a list of books that are ending soon from a patron's wishlist.
     *
     * @param patron The patron whose wishlist is being checked for ending books
     * @return An ArrayList containing titles of books that are ending soon from the patron's wishlist
     * @throws RemoteException If a remote communication error occurs
     */
    @Override public ArrayList<String> getEndingBooks(Patron patron)
        throws RemoteException
    {
         return client.getEndingBooks(patron);
    }

    /**
     * Approves a donated book and adds it to the library collection.
     *
     * @param id         The ID of the donated book to be approved
     * @param title      The title of the donated book
     * @param author     The author of the donated book
     * @param isbn       The ISBN of the donated book
     * @param year       The publication year of the donated book
     * @param publisher  The publisher of the donated book
     * @param pageCount  The page count of the donated book
     * @param genreId    The genre ID of the donated book
     * @throws SQLException    If a SQL error occurs
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void approveDonatedBook(int id, String title, String author, long isbn, int year, String publisher, int pageCount, String genreId) throws SQLException, RemoteException {
        client.approveDonatedBook(id, title, author, isbn, year, publisher, pageCount, genreId);
    }

    /**
     * Rejects a donated book and removes it from the library's consideration.
     *
     * @param bookId The ID of the donated book to be rejected
     * @throws SQLException    If a SQL error occurs
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void rejectDonatedBook(int bookId) throws SQLException, RemoteException {
        client.rejectDonatedBook(bookId);
    }

    /**
     * Allows a patron to log in with the provided username and password.
     *
     * @param username The username of the patron
     * @param password The password of the patron
     * @return The logged-in patron if successful, null otherwise
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public Patron login(String username, String password) throws RemoteException {
        try{

            return this.client.login(username, password);

        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }

    }

    /**
     * Allows an administrator to log in with the provided username and password.
     *
     * @param username The username of the administrator
     * @param password The password of the administrator
     * @return true if login is successful, false otherwise
     * @throws RemoteException If a remote communication error occurs
     */
    public boolean loginAsAdmin(String username, String password) throws RemoteException{
        try{
            return this.client.loginAsAdmin(username, password);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }

    }

    /**
     * Updates the username of a user identified by the given userID.
     *
     * @param userID      The ID of the user whose username will be updated
     * @param newUsername The new username to be set for the user
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void updateUsername(int userID, String newUsername) throws RemoteException {
        try{
            client.updateUsername(userID, newUsername);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the email of a user identified by the given userID.
     *
     * @param userID   The ID of the user whose email will be updated
     * @param newEmail The new email to be set for the user
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void updateEmail(int userID, String newEmail) throws RemoteException {
        try{
            client.updateEmail(userID, newEmail);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the phone number of a user identified by the given userID.
     *
     * @param userID         The ID of the user whose phone number will be updated
     * @param newPhoneNumber The new phone number to be set for the user
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void updatePhoneNumber(int userID, String newPhoneNumber) throws RemoteException {
        try{
            client.updatePhoneNumber(userID, newPhoneNumber);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the first name of a user identified by the given userID.
     *
     * @param userID      The ID of the user whose first name will be updated
     * @param newFirstName The new first name to be set for the user
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void updateFirstName(int userID, String newFirstName) throws RemoteException {
        try{
            client.updateFirstName(userID, newFirstName);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the last name of a user identified by the given userID.
     *
     * @param userID     The ID of the user whose last name will be updated
     * @param newLastName The new last name to be set for the user
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void updateLastName(int userID, String newLastName) throws RemoteException {
        try{
            client.updateLastName(userID, newLastName);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the password of a user identified by the given userID.
     *
     * @param userID      The ID of the user whose password will be updated
     * @param newPassword The new password to be set for the user
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void updatePassword(int userID, String newPassword) throws RemoteException {
        try{
            client.updatePassword(userID, newPassword);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the fees associated with a user identified by the given userID.
     *
     * @param userID  The ID of the user whose fees will be updated
     * @param newFees The new fees to be set for the user
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void updateFees(int userID, int newFees) throws RemoteException {
        try{
            client.updateFees(userID, newFees);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Deletes a book from the library's collection.
     *
     * @param bookID    The ID of the book to be deleted
     * @param title     The title of the book to be deleted
     * @param author    The author of the book to be deleted
     * @param year      The publication year of the book to be deleted
     * @param publisher The publisher of the book to be deleted
     * @param isbn      The ISBN of the book to be deleted
     * @param pageCount The page count of the book to be deleted
     * @param genre     The genre of the book to be deleted
     * @throws SQLException If a SQL error occurs
     */
    @Override
    public void deleteBook(int bookID,String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException {
        try{
            client.deleteBook(bookID,title, author, year, publisher, isbn, pageCount, genre);
        } catch(Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Creates a new book in the library's collection.
     *
     * @param title      The title of the book to be created
     * @param author     The author of the book to be created
     * @param year       The publication year of the book to be created
     * @param publisher  The publisher of the book to be created
     * @param isbn       The ISBN of the book to be created
     * @param pageCount  The page count of the book to be created
     * @param genre      The genre of the book to be created
     * @throws SQLException    If a SQL error occurs
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void createBook(String title, String author,String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        try{
            client.createBook(title, author, year, publisher, isbn, pageCount, genre);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the information of an existing book in the library's collection.
     *
     * @param bookID     The ID of the book to be updated
     * @param title      The new title of the book
     * @param author     The new author of the book
     * @param year       The new publication year of the book
     * @param publisher  The new publisher of the book
     * @param isbn       The new ISBN of the book
     * @param pageCount  The new page count of the book
     * @param genre      The new genre of the book
     * @throws SQLException    If a SQL error occurs
     * @throws RemoteException If a remote communication error occurs
     */
    @Override
    public void updateBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        try{
            client.updateBook(bookID, title, author, year, publisher, isbn, pageCount, genre);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }


    /**
     * Notifies listeners of a property change event.
     *
     * @param evt The property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt);
    }
}
