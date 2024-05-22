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

public class Connector implements ConnectorInterface {
    private BookDatabaseImplementation bookDatabase;
    private PatronDatabaseImplementation patronDatabase;
    private AdminDatabaseImplementation adminDatabase;
    private final RemotePropertyChangeSupport support;

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

    @Override public synchronized ArrayList<Book> getAllBooks() {
        try
        {
            return this.bookDatabase.readBooks();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override public ArrayList<Book> getBorrowedBooks(Patron patron)
        throws RemoteException
    {
        try
        {
            return this.bookDatabase.readBorrowedBook(patron);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override public ArrayList<Book> getHistoryOfBooks(Patron patron)
        throws RemoteException
    {
        try
        {
            return this.bookDatabase.readHistoryOfBooks(patron);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override public ArrayList<Book> getWishlistedBooks(Patron patron)
        throws RemoteException
    {
        try
        {
            return this.bookDatabase.readWishlistedBooks(patron);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Book> getDonatedBooks() throws RemoteException {
        {
            try {
                return this.bookDatabase.readDonatedBooks();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override public int getAmountOfReadBooks(Patron patron)
        throws RemoteException
    {
        try
        {
            return this.bookDatabase.readAmountOfBooksRead(patron);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override public int getAmountOfBorrowedBooks(Patron patron)
        throws RemoteException
    {
        try
        {
            return this.bookDatabase.readAmountOfBorrowedBooks(patron);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException
    {
        try
        {
                this.patronDatabase.createPatron(username, password, first_name,
                    last_name, email, phone_number);
                this.support.firePropertyChange("createPatron", false, true);

        }
        catch (SQLException ignored)
        {
            throw new RuntimeException(ignored);
        }
    }

    @Override
    public void createEvent(String title, String description, String date) throws RemoteException{
        try
        {
            this.adminDatabase.createEvent(title, description, date);

        }
        catch (SQLException e)
        {
            throw new RemoteException("Failed to create event: " + e.getMessage());
        }
    }
    @Override
    public void deleteEvent(Event event) throws RemoteException {
        try {
            this.adminDatabase.deleteEvent(event);
        } catch (SQLException e) {
            throw new RemoteException("Failed to delete event: " + e.getMessage());
        }
    }

    @Override
    public void deletePatron(int id) throws RemoteException {
        try {
            this.adminDatabase.deletePatron(id);
        } catch (SQLException e) {
            throw new RemoteException("Failed to delete patron: " + e.getMessage());
        }
    }

    @Override
    public void createBook(String title, String author, int year, String publisher, long isbn, int pageCount, String genre) throws SQLException {
        try{
            this.bookDatabase.createBook(title, author, year, publisher, isbn, pageCount, genre);
        }catch (SQLException e){
            throw new SQLException(e);
        }
    }

    @Override public synchronized  ArrayList<Book> filter(String genre,String state, String search){
        try {
            return this.bookDatabase.filter(genre, state,search);
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }



    @Override
    public Patron login(String username, String password) throws RemoteException {
        try{
            return patronDatabase.login(username, password);
        }catch(SQLException e){
            throw new IllegalArgumentException("Account doesn't exist.");
        }
    }

    @Override
    public boolean loginAsAdmin(String username, String password) throws RemoteException {
        try{
            return patronDatabase.loginAsAdmin(username, password);

        }catch(SQLException e){
            throw new IllegalArgumentException("Account doesn't exist.");
        }
    }

    @Override
    public void updateUsername(int userID, String newUsername) throws RemoteException {
        try{
            patronDatabase.updateUsername(userID, newUsername);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateEmail(int userID, String newEmail) throws RemoteException {
        try{
            patronDatabase.updateEmail(userID, newEmail);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updatePhoneNumber(int userID, String newPhoneNumber) throws RemoteException {
        try{
            patronDatabase.updatePhone(userID, newPhoneNumber);
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateFirstName(int userID, String newFirstName) throws RemoteException {
        try{
            patronDatabase.updateFirstName(userID, newFirstName);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateLastName(int userID, String newLastName) throws RemoteException {
        try{
            patronDatabase.updateLastName(userID, newLastName);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updatePassword(int userID, String newPassword) throws RemoteException {
        try {
            patronDatabase.updatePassword(userID, newPassword);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateFees(int useriD, int newFees) throws RemoteException {
        try{
            patronDatabase.updateFees(useriD, newFees);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void borrowBooks(Book book, Patron parton) throws RemoteException, SQLException {
        bookDatabase.borrowBook(book,parton);
        this.support.firePropertyChange("BorrowBook", null,book);
    }

    @Override public void wishlistBook(Book book, Patron patron)
        throws RemoteException, SQLException
    {
        bookDatabase.wishlistBook(book,patron);
        this.support.firePropertyChange("Wishlist",false,true);
    }

    @Override public boolean isWishlisted(Book book, Patron patron)
        throws RemoteException, SQLException
    {
        return bookDatabase.isWishlisted(book,patron);
    }

    @Override public void returnBookToDatabase(Book book, Patron patron) throws SQLException, RemoteException {
        bookDatabase.returnBookToDatabase(book,patron);
        this.support.firePropertyChange("ReturnBook", null, book);
    }

    @Override
    public void deleteBook(int bookID,String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException {
        try{
            bookDatabase.deleteBook(bookID,title, author, year, publisher, isbn, pageCount, genre);
            this.support.firePropertyChange("DeleteBook", false, true);
        } catch(Exception e){
            throw new IllegalArgumentException(e);
        }
    }


    @Override public void deleteFromWishlist(Book book, Patron patron)
        throws RemoteException, SQLException
    {
        bookDatabase.deleteFromWishlist(book, patron);
    }

    @Override
    public Book donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron) throws SQLException, RemoteException {
        this.support.firePropertyChange("BookDonate", false, true);
        return bookDatabase.donateBook(title, author, isbn, year, publisher, pageCount, genre, patron);
    }

    @Override
    public ArrayList<Event> getAllEvents() throws RemoteException {
        try {
            return this.adminDatabase.getAllEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public ArrayList<String> getEndingBooks(Patron patron)
        throws RemoteException
    {
        try {
            return this.bookDatabase.checkEndingBooks(patron);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void approveDonatedBook(int id, String title, String author, long isbn, int year, String publisher, int pageCount, String genreId) throws SQLException, RemoteException {
        bookDatabase.approveDonatedBook(id, title, author, isbn, year, publisher, pageCount, genreId);
        this.support.firePropertyChange("DonatedBookApproved", false,true);
    }

    @Override
    public void rejectDonatedBook(int bookId) throws SQLException, RemoteException {
        bookDatabase.rejectDonatedBook(bookId);
        this.support.firePropertyChange("DonatedBookRejected", false,true);
    }

    @Override
    public void addRemotePropertyChangeListener(RemotePropertyChangeListener listener) throws RemoteException {
        this.support.addPropertyChangeListener(listener);
    }
}
