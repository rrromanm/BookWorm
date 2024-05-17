package sep.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import sep.client.ClientImplementation;
import sep.client.ClientInterface;
import sep.model.validators.*;
import sep.shared.LibraryInterface;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelManager extends UnicastRemoteObject implements Model , PropertyChangeListener {
    private final ClientInterface client;
    private String error;
    private final PropertyChangeSupport support;

    public ModelManager(LibraryInterface library) throws RemoteException {
        this.client = new ClientImplementation(library);
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

    @Override public int getAmountOfReadBooks(Patron patron)
        throws RemoteException
    {
        return client.getAmountOfReadBooks(patron);
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


    public ArrayList<Book> filter(String genre,String state, String search) throws RemoteException {
        return client.filter(genre,state,search);
    }

    @Override
    public void borrowBooks(Book book, Patron patron) throws RemoteException, SQLException {
        client.borrowBooks(book,patron);
    }

    @Override public void wishlistBook(Book book, Patron patron)
        throws RemoteException, SQLException
    {
        client.wishlistBook(book,patron);
    }

    @Override public boolean isWishlisted(Book book, Patron patron)
        throws RemoteException, SQLException
    {
        return client.isWishlisted(book,patron);
    }

    @Override
    public void returnBookToDatabase(Book book, Patron patron) throws RemoteException, SQLException {
        client.returnBookToDatabase(book,patron);
    }

    @Override
    public void donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron) throws SQLException, RemoteException {
        client.donateBook(title, author, isbn, year, publisher, pageCount, genre, patron);
    }

    @Override public void deleteFromWishlist(Book book, Patron patron) throws RemoteException, SQLException
    {
        client.deleteFromWishlist(book,patron);
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
    public void updateUsername(String oldUsername, String newUsername) throws RemoteException {
        try{
            client.updateUsername(oldUsername, newUsername);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateEmail(String oldEmail, String newEmail) throws RemoteException {
        try{
            client.updateEmail(oldEmail, newEmail);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updatePhoneNumber(String oldPhoneNumber, String newPhoneNumber) throws RemoteException {
        try{
            client.updatePhoneNumber(oldPhoneNumber, newPhoneNumber);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateFirstName(String oldFirstName, String newFirstName) throws RemoteException {
        try{
            client.updateFirstName(oldFirstName, newFirstName);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateLastName(String oldLastName, String newLastName) throws RemoteException {
        try{
            client.updateLastName(oldLastName, newLastName);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) throws RemoteException {
        try{
            client.updatePassword(oldPassword, newPassword);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt);
    }
}
