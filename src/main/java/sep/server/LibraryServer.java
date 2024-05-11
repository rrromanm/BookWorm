package sep.server;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LibraryServer {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1098); //change to port 1099
        LibraryImplementation library = new LibraryImplementation();
        Remote remote = UnicastRemoteObject.exportObject(library, 0);
        registry.bind("library", remote);
        System.out.println("Server running");
    }
}
