package sep.model;


import sep.client.ClientImplementation;
import sep.client.ClientInterface;
import sep.model.validators.*;
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

    @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number) throws RemoteException {
        try{

            UsernameValidator.validate(username);
            EmailValidator.validate(email);
            PasswordValidator.validate(password);
            PhoneValidator.validate(phone_number);
            NameValidator.validate(first_name);
            NameValidator.validate(last_name);
            this.client.createPatron( username, password, first_name, last_name, email, phone_number);

        }catch(Exception e){
            throw new RemoteException(e.getMessage());
        }

    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        try{

            return this.client.login(username, password);

        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }

    }
}
