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

public class ModelManager extends UnicastRemoteObject implements Model , PropertyChangeListener {
    private final ClientInterface client;
    private String error;
    private final PropertyChangeSupport support;

    public ModelManager(ConnectorInterface library) throws IOException, SQLException {
        this.client = new Client(library);
        this.error = "";
        this.client.addPropertyChangeListener(this);
        this.support = new PropertyChangeSupport(this);
    }
    @Override
    public ArrayList<Book> getAllBooks() throws RemoteException {
        return client.getAllBooks();
    }

    @Override public ArrayList<Book> getBorrowedBooks(Patron patron)
        throws RemoteException
    {
        return client.getBorrowedBooks(patron);
    }

    @Override public ArrayList<Book> getHistoryOfBooks(Patron patron)
        throws RemoteException
    {
        return client.getHistoryOfBooks(patron);
    }

    @Override public ArrayList<Book> getWishlistedBooks(Patron patron)
        throws RemoteException
    {
        return client.getWishlistedBooks(patron);
    }

    @Override
    public ArrayList<Book> getDonatedBooks() throws RemoteException {
        return client.getDonatedBooks();
    }

    @Override
    public ArrayList<Event> getAllEvents() throws RemoteException {
        return client.getAllEvents();
    }

    @Override public int getAmountOfReadBooks(Patron patron)
        throws RemoteException
    {
        return client.getAmountOfReadBooks(patron);
    }

    @Override public int getAmountOfBorrowedBooks(Patron patron)
        throws RemoteException
    {
        return client.getAmountOfBorrowedBooks(patron);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }

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

    @Override
    public void deleteEvent(Event event) throws RemoteException {
        try
        {
            this.client.deleteEvent(event);

        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
    }


    public ArrayList<Book> filter(String genre,String state, String search) throws RemoteException {
        return client.filter(genre,state,search);
    }

    @Override
    public void borrowBooks(Book book, Patron patron)
        throws IOException, SQLException {
        client.borrowBooks(book,patron);
    }

    @Override public void wishlistBook(Book book, Patron patron)
        throws IOException, SQLException
    {
        client.wishlistBook(book,patron);
    }

    @Override public boolean isWishlisted(Book book, Patron patron)
        throws RemoteException, SQLException
    {
        return client.isWishlisted(book,patron);
    }

    @Override
    public void returnBookToDatabase(Book book, Patron patron)
        throws IOException, SQLException {
        client.returnBookToDatabase(book,patron);
    }

    @Override
    public void donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron)
        throws SQLException, IOException
    {
        client.donateBook(title, author, isbn, year, publisher, pageCount, genre, patron);
    }

    @Override public void deleteFromWishlist(Book book, Patron patron)
        throws IOException, SQLException
    {
        client.deleteFromWishlist(book,patron);
    }

    @Override public ArrayList<String> getEndingBooks(Patron patron)
        throws RemoteException
    {
         return client.getEndingBooks(patron);
    }

    @Override
    public void approveDonatedBook(int id, String title, String author, long isbn, int year, String publisher, int pageCount, String genreId) throws SQLException, RemoteException {
        client.approveDonatedBook(id, title, author, isbn, year, publisher, pageCount, genreId);
    }

    @Override
    public void rejectDonatedBook(int bookId) throws SQLException, RemoteException {
        client.rejectDonatedBook(bookId);
    }

    @Override
    public Patron login(String username, String password) throws RemoteException {
        try{

            return this.client.login(username, password);

        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }

    }
    public boolean loginAsAdmin(String username, String password) throws RemoteException{
        try{
            return this.client.loginAsAdmin(username, password);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }

    }

    @Override
    public void updateUsername(int userID, String newUsername) throws RemoteException {
        try{
            client.updateUsername(userID, newUsername);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateEmail(int userID, String newEmail) throws RemoteException {
        try{
            client.updateEmail(userID, newEmail);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updatePhoneNumber(int userID, String newPhoneNumber) throws RemoteException {
        try{
            client.updatePhoneNumber(userID, newPhoneNumber);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateFirstName(int userID, String newFirstName) throws RemoteException {
        try{
            client.updateFirstName(userID, newFirstName);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateLastName(int userID, String newLastName) throws RemoteException {
        try{
            client.updateLastName(userID, newLastName);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updatePassword(int userID, String newPassword) throws RemoteException {
        try{
            client.updatePassword(userID, newPassword);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateFees(int userID, int newFees) throws RemoteException {
        try{
            client.updateFees(userID, newFees);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void deleteBook(int bookID,String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException {
        try{
            client.deleteBook(bookID,title, author, year, publisher, isbn, pageCount, genre);
        } catch(Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void createBook(String title, String author,String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        try{
            client.createBook(title, author, year, publisher, isbn, pageCount, genre);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        try{
            client.updateBook(bookID, title, author, year, publisher, isbn, pageCount, genre);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt);
    }
}
