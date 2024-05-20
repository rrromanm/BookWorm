package sep.client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import sep.file.FileLog;
import sep.model.Book;
import sep.model.Patron;
import sep.model.UserSession;
import sep.shared.ConnectorInterface;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client extends UnicastRemoteObject implements RemotePropertyChangeListener,ClientInterface, Serializable {
    private final ConnectorInterface library;
    private final PropertyChangeSupport support;
    private File file;
    private FileLog fileLog;

    public Client(ConnectorInterface library) throws RemoteException {
        this.library = library;
        this.support = new PropertyChangeSupport(this);
        this.library.addRemotePropertyChangeListener(this);
        this.file =  new File("src/main/java/sep/file/LibraryLog");
        this.fileLog = FileLog.getInstance(file);
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
    public void borrowBooks(Book book, Patron patron) throws IOException, SQLException {
        library.borrowBooks(book, patron);
        fileLog.log(patron.getUsername() + " has borrowed a \"" + book.getTitle() + "\"");
    }

  @Override public void wishlistBook(Book book, Patron patron) throws IOException, SQLException
  {
    library.wishlistBook(book, patron);
    fileLog.log(patron.getUsername() + " has added a \"" + book.getTitle() + "\" to the wishlist");
  }

  @Override public boolean isWishlisted(Book book, Patron patron) throws RemoteException, SQLException
  {
    return library.isWishlisted(book, patron);
  }

  @Override public void returnBookToDatabase(Book book, Patron patron)
      throws SQLException, IOException
  {
    library.returnBookToDatabase(book, patron);
    fileLog.log(patron.getUsername() + " has returned a \"" + book.getTitle() + "\"");
  }

    @Override public void donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron)
        throws SQLException, IOException
    {
        library.donateBook(title, author, isbn, year, publisher, pageCount, genre, patron);
        fileLog.log(patron.getUsername() + " has donated a \"" + title + "\"");
    }

  @Override public void deleteFromWishlist(Book book, Patron patron)
      throws SQLException, IOException
  {
    library.deleteFromWishlist(book,patron);
    fileLog.log(patron.getUsername() + " has deleted a \"" + book.getTitle() + "\" from the wishlist");
  }

    @Override
    public void approveDonatedBook(int id, String title, String author, long isbn, int year, String publisher, int pageCount, String genreId) throws SQLException, RemoteException {
        library.approveDonatedBook(id, title, author, isbn,year, publisher, pageCount, genreId);
    }

    @Override
    public void rejectDonatedBook(int bookId) throws SQLException, RemoteException {
        library.rejectDonatedBook(bookId);
    }

    @Override
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number, int fees) throws RemoteException {
        try{
            library.createPatron(username, password, first_name, last_name, email, phone_number, fees);
            fileLog.log("New user has been created: " + username );
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }

   }

    @Override
    public Patron login(String username, String password) throws IOException
    {
        Patron userLoggedIn = library.login(username, password);
        UserSession.getInstance().setLoggedInUser(userLoggedIn);
        fileLog.log(userLoggedIn.getUsername() + " has logged in");
        return userLoggedIn;
    }

    @Override
    public boolean loginAsAdmin(String username, String password) throws IOException
    {
        fileLog.log("Admin has logged in");
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
           if (event.getPropertyName().equals("DonatedBookApproved"))
           {
               this.support.firePropertyChange("DonatedBookApproved", false,true);
           }
           if (event.getPropertyName().equals("DonatedBookRejected"))
           {
               this.support.firePropertyChange("DonatedBookRejected", false,true);
           }
        });
    }
}
