package sep.server;

import sep.jdbc.BookDatabaseImplementation;
import sep.jdbc.PatronDatabaseImplementation;
import sep.model.Book;
import sep.model.Patron;
import sep.shared.LibraryInterface;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryImplementation implements LibraryInterface {
    private BookDatabaseImplementation bookDatabase;
    private PatronDatabaseImplementation patronDatabase;

    public LibraryImplementation() {
        try
        {
            this.bookDatabase = BookDatabaseImplementation.getInstance();
            this.patronDatabase = PatronDatabaseImplementation.getInstance();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override public synchronized ArrayList<Book> getAllBooks() {
        try
        {
            return this.bookDatabase.readBooks();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number) throws RemoteException {
        Patron createdPatron = new Patron(username, password, first_name, last_name, email, phone_number);
        try{
            this.patronDatabase.createPatron(username, password, first_name, last_name, email, phone_number);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        try{
            return patronDatabase.login(username, password);

        }catch(SQLException e){
            throw new IllegalArgumentException("Account doesn't exist.");
        }
    }

    @Override
    public boolean loginAsAdmin(String username, String password) throws RemoteException {
        try{
            return patronDatabase.loginAsAdmin(username, password);

        }catch(SQLException e){
            throw new IllegalArgumentException("Account doesn't exist.");
        }
    }


}
