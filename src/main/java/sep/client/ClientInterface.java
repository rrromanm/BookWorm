package sep.client;

import sep.model.Book;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientInterface {
    ArrayList<Book> getAllBooks() throws RemoteException;
}
