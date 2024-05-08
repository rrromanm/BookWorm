package sep.shared;

import sep.model.Book;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface LibraryInterface extends Remote { //TODO: Rename it please to connector or sum
    ArrayList<Book> getAllBooks() throws RemoteException;
//    void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number) throws RemoteException;
    boolean login(String username, String password) throws RemoteException;
}
