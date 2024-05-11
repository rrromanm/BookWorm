package sep.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import sep.jdbc.BookDatabaseImplementation;
import sep.jdbc.PatronDatabaseImplementation;
import sep.model.Book;
import sep.model.Patron;
import sep.shared.LibraryInterface;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryImplementation implements LibraryInterface {
    private BookDatabaseImplementation bookDatabase;
    private PatronDatabaseImplementation patronDatabase;

    public LibraryImplementation() {
        try
        {
            this.bookDatabase = BookDatabaseImplementation.getInstance();
            this.patronDatabase = PatronDatabaseImplementation.getInstance();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
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


    @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException
    {
        Patron createdPatron = new Patron(first_name,  last_name,  username,  password,  email, phone_number,  fees); //TODO: I think this is redundant
        try
        {
            this.patronDatabase.createPatron(username, password, first_name,
                last_name, email, phone_number);
        }
        catch (SQLException e)
        {

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
    public boolean login(String username, String password) throws RemoteException {
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
    public void updateUsername(String oldUsername, String newUsername) throws RemoteException {
        try{
            patronDatabase.updateUsername(oldUsername, newUsername);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateEmail(String oldEmail, String newEmail) throws RemoteException {
        try{
            patronDatabase.updateEmail(oldEmail, newEmail);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updatePhoneNumber(String oldPhoneNumber, String newPhoneNumber) throws RemoteException {
        try{
            patronDatabase.updatePhone(oldPhoneNumber, newPhoneNumber);
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateFirstName(String oldFirstName, String newFirstName) throws RemoteException {
        try{
            patronDatabase.updateFirstName(oldFirstName, newFirstName);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateLastName(String oldLastName, String newLastName) throws RemoteException {
        try{
            patronDatabase.updateLastName(oldLastName, newLastName);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void borrowBooks(Book book, Patron parton) throws RemoteException, SQLException {
        bookDatabase.borrowBook(book,parton);
    }

    @Override public void addPropertyChangeListener(
        RemotePropertyChangeListener<Book> listener)
    {
        // idk
    }

    @Override public void removePropertyChangeListener(
        RemotePropertyChangeListener<Book> listener)
    {

    }

    @Override public void returnBook(Book book, Patron patron)
    {

    }
}
