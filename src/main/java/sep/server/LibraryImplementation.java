package sep.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import sep.client.ClientInterface;
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
    private final RemotePropertyChangeSupport<Patron> support;

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

    @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException
    {
        try
        {
            if (patronDatabase.usernameExists(username)){
                System.out.println("Username already in use.");
            } else {
                this.patronDatabase.createPatron(username, password, first_name,
                    last_name, email, phone_number);
            }

        }
        catch (SQLException ignored)
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
    public void updatePassword(String oldPassword, String newPassword) throws RemoteException {
        try {
            patronDatabase.updatePassword(oldPassword, newPassword);
        }catch(SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void borrowBooks(Book book, Patron parton) throws RemoteException, SQLException {
        bookDatabase.borrowBook(book,parton);
        this.support.firePropertyChange("BorrowBook", null,parton);
//        System.out.println("borrowing book sent from library implementation");
    }

    @Override public void returnBookToDatabase(Book book, Patron patron) throws SQLException, RemoteException {
        bookDatabase.returnBookToDatabase(book,patron);
        this.support.firePropertyChange("returnedBook", null, patron);
        System.out.println("returning book sent from library implementation");
    }

    @Override
    public synchronized void addRemotePropertyChangeListener(RemotePropertyChangeListener listener) throws RemoteException
    {
        this.support.addPropertyChangeListener(listener);
        System.out.println("property change listener added" + listener.toString());
        System.out.println(this.support.getPropertyChangeListeners().length);
        support.firePropertyChange("something", null, null);
    }
}
