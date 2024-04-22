package sep.model;


import sep.shared.LibraryInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ModelManager extends UnicastRemoteObject implements Model {
    private final LibraryInterface libraryInterface;

    public ModelManager(LibraryInterface libraryInterface) throws RemoteException {
        super();
        this.libraryInterface = libraryInterface;
    }
}
