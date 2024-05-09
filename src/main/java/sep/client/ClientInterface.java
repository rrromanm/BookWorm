package sep.client;

import sep.model.Book;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientInterface {
    ArrayList<Book> getAllBooks() throws RemoteException;

    void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException;
    boolean login(String username, String password) throws RemoteException;
    boolean loginAsAdmin(String username, String password) throws RemoteException;
    void updateUsername(String oldUsername, String newUsername) throws RemoteException;
    void updateEmail(String oldEmail, String newEmail) throws RemoteException;
    void updatePhoneNumber(String oldPhoneNumber, String newPhoneNumber) throws RemoteException;
    void updateFirstName(String oldFirstName, String newFirstName) throws RemoteException;
    void updateLastName(String oldLastName, String newLastName) throws RemoteException;
    ArrayList<Book> filter(String genre, String state,String search) throws RemoteException;

}
