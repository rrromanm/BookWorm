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

    @Override
    public ArrayList<Book> filterByState(String genre,String state) throws RemoteException {
        return library.filterByState(genre, state);
    }

//    @Override
//    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number) throws RemoteException {
//        library.createPatron(username, password, first_name, last_name, email, phone_number);
//    }

    @Override
    public void login(String username, String password) throws RemoteException {
        library.login(username, password);
    }

}
