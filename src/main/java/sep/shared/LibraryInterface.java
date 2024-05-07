package sep.shared;

import sep.model.Book;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface LibraryInterface extends Remote {
    ArrayList<Book> getAllBooks() throws RemoteException;
}
