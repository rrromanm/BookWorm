package sep.model;


import sep.client.ClientImplementation;
import sep.client.ClientInterface;
import sep.shared.LibraryInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ModelManager extends UnicastRemoteObject implements Model {
    private final LibraryInterface library;
    private final ClientInterface client;

    public ModelManager(LibraryInterface library) throws RemoteException {
        super();
        this.library = library;
        this.client = new ClientImplementation(library);
    }

    @Override
    public ArrayList<Book> getAllBooks() throws RemoteException {
        return client.getAllBooks();
    }
}
