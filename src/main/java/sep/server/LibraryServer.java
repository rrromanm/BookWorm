package sep.server;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class represents a library server that manages books and patrons.
 * It provides methods to create, update, and delete books and patrons,
 * as well as borrow and return books.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class LibraryServer {
    /**
     * Main method to start the library server.
     * @param args Command-line arguments (not used)
     * @throws Exception If an error occurs while starting the server
     */
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        Connector library = new Connector();
        Remote remote = UnicastRemoteObject.exportObject(library, 0);
        registry.bind("library", remote);
        System.out.println("Server running");
    }
}
