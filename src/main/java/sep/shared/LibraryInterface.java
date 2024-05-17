package sep.shared;

import dk.via.remote.observer.RemotePropertyChangeListener;
import sep.model.Book;
import sep.model.Patron;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface LibraryInterface extends Remote { //TODO: Rename it please to connector or sum
    ArrayList<Book> getAllBooks() throws RemoteException;
    ArrayList<Book> getBorrowedBooks(Patron patron) throws RemoteException;
    ArrayList<Book> getHistoryOfBooks(Patron patron) throws RemoteException;
    ArrayList<Book> getWishlistedBooks(Patron patron) throws RemoteException;
    ArrayList<Book> getDonatedBooks() throws RemoteException;
    int getAmountOfReadBooks(Patron patron) throws RemoteException;
    ArrayList<Book> filter(String genre,String state, String search) throws RemoteException;
    void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException;
    Patron login(String username, String password) throws RemoteException;
    boolean loginAsAdmin(String username, String password) throws RemoteException;
    void updateUsername(String oldUsername, String newUsername) throws RemoteException;
    void updateEmail(String oldEmail, String newEmail) throws RemoteException;
    void updatePhoneNumber(String oldPhoneNumber, String newPhoneNumber) throws RemoteException;
    void updateFirstName(String oldFirstName, String newFirstName) throws RemoteException;
    void updateLastName(String oldLastName, String newLastName) throws RemoteException;
    void updatePassword(String oldPassword, String newPassword) throws RemoteException;
    void borrowBooks(Book book, Patron patron) throws RemoteException, SQLException;
    void wishlistBook(Book book, Patron patron) throws RemoteException, SQLException;
    boolean isWishlisted(Book book, Patron patron) throws RemoteException,SQLException;
    void returnBookToDatabase(Book book, Patron patron) throws RemoteException, SQLException;
    void deleteFromWishlist(Book book,Patron patron) throws RemoteException, SQLException;
    void addRemotePropertyChangeListener(RemotePropertyChangeListener<Patron> listener) throws RemoteException;
    Book donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron) throws RemoteException, SQLException;
}
