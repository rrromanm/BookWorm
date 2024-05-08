package sep.model;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Model {
    ArrayList<Book> getAllBooks() throws RemoteException;
    ArrayList<Book> filter(String genre, String state, String search) throws RemoteException;
//    void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number) throws RemoteException;
    void login(String username, String password) throws RemoteException;
}

