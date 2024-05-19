package sep.client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import sep.model.Book;
import sep.model.Patron;
import sep.model.UserSession;
import sep.model.Event;
import sep.shared.ConnectorInterface;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client extends UnicastRemoteObject implements RemotePropertyChangeListener,ClientInterface, Serializable {
    private final ConnectorInterface library;
    private final PropertyChangeSupport support;

    public Client(ConnectorInterface library) throws RemoteException {
        this.library = library;
        this.support = new PropertyChangeSupport(this);
        this.library.addRemotePropertyChangeListener(this);
    }


    @Override
    public ArrayList<Book> getAllBooks() throws RemoteException {
        return library.getAllBooks();
    }

    @Override
    public ArrayList<Event> getAllEvents() throws RemoteException {
        return library.getAllEvents();
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

  @Override public ArrayList<Book> getWishlistedBooks(Patron patron)
      throws RemoteException
  {
    return library.getWishlistedBooks(patron);
  }

    @Override
    public ArrayList<Book> getDonatedBooks() throws RemoteException {
        return library.getDonatedBooks();
    }

    @Override public int getAmountOfReadBooks(Patron patron)
      throws RemoteException
  {
    return library.getAmountOfReadBooks(patron);
  }

  @Override public int getAmountOfBorrowedBooks(Patron patron)
      throws RemoteException
  {
    return library.getAmountOfBorrowedBooks(patron);
  }

  @Override
    public ArrayList<Book> filter(String genre,String state, String search) throws RemoteException {
        return library.filter(genre, state,search);
    }

    @Override
    public void borrowBooks(Book book, Patron patron) throws RemoteException, SQLException {
        library.borrowBooks(book, patron);
    }

  @Override public void wishlistBook(Book book, Patron patron) throws RemoteException, SQLException
  {
    library.wishlistBook(book, patron);
  }

  @Override public boolean isWishlisted(Book book, Patron patron) throws RemoteException, SQLException
  {
    return library.isWishlisted(book, patron);
  }

  @Override public void returnBookToDatabase(Book book, Patron patron) throws SQLException, RemoteException
  {
      library.returnBookToDatabase(book, patron);
  }

    @Override public void donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron) throws SQLException, RemoteException
    {
        library.donateBook(title, author, isbn, year, publisher, pageCount, genre, patron);
    }

  @Override public void deleteFromWishlist(Book book, Patron patron) throws SQLException, RemoteException
  {
    library.deleteFromWishlist(book,patron);
  }

  @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException {
        try{
            library.createPatron(username, password, first_name, last_name, email, phone_number, fees);
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }

   }

   @Override
   public void createEvent(String title, String description, String eventDate) throws RemoteException{
        try{
            library.createEvent(title, description, eventDate);
        } catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
   }

    @Override
    public Patron login(String username, String password) throws RemoteException {
        Patron userLoggedIn = library.login(username, password);
        UserSession.getInstance().setLoggedInUser(userLoggedIn);
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

    @Override
    public void updateFees(int oldFees, int newFees) throws RemoteException {
        try{
            library.updateFees(oldFees, newFees);
        }catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(RemotePropertyChangeEvent event) throws RemoteException {
        Platform.runLater(() -> {
            if (event.getPropertyName().equals("BorrowBook"))
            {
                this.support.firePropertyChange("BorrowBook", false, true);
            }
            if (event.getPropertyName().equals("ReturnBook"))
            {
                this.support.firePropertyChange("ReturnBook", false, true);
            }
           if (event.getPropertyName().equals("Wishlist"))
           {
            this.support.firePropertyChange("Wishlist", false, true);
           }
           if (event.getPropertyName().equals("BookDonate"))
           {
               this.support.firePropertyChange("BookDonate", false, true);
           }
           if (event.getPropertyName().equals("createPatron")){
               this.support.firePropertyChange("createPatron", false, true);
           }
        });
    }
}
