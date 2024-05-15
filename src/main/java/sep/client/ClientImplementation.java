package sep.client;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import sep.model.Book;
import sep.model.Patron;
import sep.model.UserSession;
import sep.shared.LibraryInterface;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientImplementation implements ClientInterface {
    private LibraryInterface library;
    private final RemotePropertyChangeSupport<Patron> support;

    public ClientImplementation(LibraryInterface library) {
        this.library = library;
      this.support = new RemotePropertyChangeSupport<Patron>();
    }


    @Override
    public ArrayList<Book> getAllBooks() throws RemoteException {
        return library.getAllBooks();
    }

  @Override public ArrayList<Book> getBorrowedBooks(Patron patron) throws RemoteException
  {
    return library.getBorrowedBooks(patron);
  }

  @Override public ArrayList<Book> getHistoryOfBooks(Patron patron)
      throws RemoteException
  {
    return library.getHistoryOfBooks(patron);
  }

  @Override
    public ArrayList<Book> filter(String genre,String state, String search) throws RemoteException {
        return library.filter(genre, state,search);
    }

    @Override
    public void borrowBooks(Book book, Patron patron) throws RemoteException, SQLException {
        library.borrowBooks(book, patron);
    }

    @Override
    public void returnBookToDatabase(Book book, Patron patron) throws SQLException, RemoteException {
        library.returnBookToDatabase(book, patron);
    }

  @Override public void addPropertyChangeListener(RemotePropertyChangeListener<Patron> listener)
  {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removePropertyChangeListener(RemotePropertyChangeListener<Patron> listener)
  {
    support.removePropertyChangeListener(listener);
  }

  @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException {
        library.createPatron(username,  password,  first_name, last_name,  email,  phone_number, fees);
   }

    @Override
    public Patron login(String username, String password) throws RemoteException {
        Patron userLoggedIn = library.login(username, password);
        UserSession.getInstance().setLoggedInUser(userLoggedIn);
        support.firePropertyChange("UserLoggedIn",null,userLoggedIn);
        return userLoggedIn;
    }

    @Override
    public boolean loginAsAdmin(String username, String password) throws RemoteException {
        return library.loginAsAdmin(username, password);
    }

    @Override
    public void updateUsername(String oldUsername, String newUsername) throws RemoteException {
        try{
            library.updateUsername(oldUsername, newUsername);
        }catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }

    }

    @Override
    public void updateEmail(String oldEmail, String newEmail) throws RemoteException {
        try{
            library.updateEmail(oldEmail, newEmail);
        }catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updatePhoneNumber(String oldPhoneNumber, String newPhoneNumber) throws RemoteException {
        try{
            library.updatePhoneNumber(oldPhoneNumber, newPhoneNumber);
        }catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateFirstName(String oldFirstName, String newFirstName) throws RemoteException {
        try{
            library.updateFirstName(oldFirstName, newFirstName);
        }catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateLastName(String oldLastName, String newLastName) throws RemoteException {
        try{
            library.updateLastName(oldLastName, newLastName);
        } catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) throws RemoteException {
        try{
            library.updatePassword(oldPassword, newPassword);
        } catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }


}
