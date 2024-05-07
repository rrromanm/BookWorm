package sep.client;

import sep.model.Book;
import sep.shared.LibraryInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientImplementation implements ClientInterface {
    private LibraryInterface library;

    public ClientImplementation(LibraryInterface library) {
        this.library = library;
    }


    @Override
    public ArrayList<Book> getAllBooks() throws RemoteException {
        return library.getAllBooks();
    }
}
