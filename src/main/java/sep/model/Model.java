package sep.model;

import dk.via.remote.observer.RemotePropertyChangeListener;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Model {
    ArrayList<Book> getAllBooks() throws RemoteException;
    void addPropertyChangeListener(RemotePropertyChangeListener<Book> listener);
    void removePropertyChangeListener(RemotePropertyChangeListener<Book> listener);
    void borrow(Book book, Patron patron);
    void returnBook(Book book, Patron patron);

    void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException;
    boolean login(String username, String password) throws RemoteException;
    boolean loginAsAdmin(String username, String password) throws RemoteException;
    void updateUsername(String oldUsername, String newUsername) throws RemoteException;
    void updateEmail(String oldEmail, String newEmail) throws RemoteException;
    void updatePhoneNumber(String oldPhoneNumber, String newPhoneNumber) throws RemoteException;
    void updateFirstName(String oldFirstName, String newFirstName) throws RemoteException;
    void updateLastName(String oldLastName, String newLastName) throws RemoteException;
    String getError();
    ArrayList<Book> filter(String genre, String state, String search) throws RemoteException;


}

