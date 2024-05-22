package sep.client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import sep.file.FileLog;
import sep.model.Book;
import sep.model.Patron;
import sep.model.UserSession;
import sep.model.Event;
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

    public Client(ConnectorInterface library) throws RemoteException, SQLException, IOException {
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
    public void borrowBooks(Book book, Patron patron) throws IOException, SQLException {
        library.borrowBooks(book, patron);
        fileLog.log(patron.getUsername() + " has borrowed a \"" + book.getTitle() + "\"");
    }

  @Override public void wishlistBook(Book book, Patron patron) throws IOException, SQLException
  {
    library.wishlistBook(book, patron);
    fileLog.log(patron.getUsername() + " has added a \"" + book.getTitle() + "\" to the wishlist");
  }

  @Override
  public void extendBook(Book book, Patron patron) throws RemoteException, SQLException
  {
      library.extendBook(book, patron);
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




    @Override
    public void deleteBook(int bookID,String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException {
        try{
            library.deleteBook(bookID,title, author, year, publisher, isbn, pageCount, genre);
            fileLog.log(title + " has been deleted from the database by Admin");
        } catch(Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void createBook(String title, String author,String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException{
        try{
            library.createBook(title, author, year, publisher, isbn, pageCount, genre);
            fileLog.log(title + " has been added to the database by Admin");
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        try{
            library.updateBook(bookID, title, author, year, publisher, isbn, pageCount, genre);
            fileLog.log(title + " has been updated in the database by Admin");
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override public void donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron) throws SQLException, RemoteException, IOException
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

  @Override public ArrayList<String> getEndingBooks(Patron patron)
      throws RemoteException
  {
    return library.getEndingBooks(patron);
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
   public void createEvent(String title, String description, String eventDate) throws RemoteException{
        try{
            library.createEvent(title, description, eventDate);
        } catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
   }

    @Override
    public void deleteEvent(int id) throws RemoteException{
        try{
            library.deleteEvent(id);
        } catch (Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void deletePatron(int id) throws RemoteException{
        try{
            library.deletePatron(id);
        } catch (Exception e){
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
    public void updateUsername(int userID, String newUsername) throws RemoteException {
        try{
            library.updateUsername(userID, newUsername);
        }catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }

    }

    @Override
    public void updateEmail(int userID, String newEmail) throws RemoteException {
        try{
            library.updateEmail(userID, newEmail);
        }catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updatePhoneNumber(int userID, String newPhoneNumber) throws RemoteException {
        try{
            library.updatePhoneNumber(userID, newPhoneNumber);
        }catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateFirstName(int userID, String newFirstName) throws RemoteException {
        try{
            library.updateFirstName(userID, newFirstName);
        }catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateLastName(int userID, String newLastName) throws RemoteException {
        try{
            library.updateLastName(userID, newLastName);
        } catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updatePassword(int userID, String newPassword) throws RemoteException {
        try{
            library.updatePassword(userID, newPassword);
        } catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void updateFees(int userID, int newFees) throws RemoteException {
        try{
            library.updateFees(userID, newFees);
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
           if (event.getPropertyName().equals("createPatron"))
           {
               this.support.firePropertyChange("createPatron", false, true);
           }
           if (event.getPropertyName().equals("DonatedBookApproved"))
           {
               this.support.firePropertyChange("DonatedBookApproved", false,true);
           }
           if (event.getPropertyName().equals("DonatedBookRejected")) {
               this.support.firePropertyChange("DonatedBookRejected", false, true);
           }
           if(event.getPropertyName().equals("removeBook")){
               this.support.firePropertyChange("removeBook", false, true);
           }
           if(event.getPropertyName().equals("updateBook")){
               this.support.firePropertyChange("updateBook", false, true);
           }
           if(event.getPropertyName().equals("createBook")){
               this.support.firePropertyChange("createBook", false, true);
           }
           if(event.getPropertyName().equals("updatePatron")){
               this.support.firePropertyChange("updatePatron", false, true);
           }
           if (event.getPropertyName().equals("ExtendBook"))
           {
               this.support.firePropertyChange("ExtendBook", false, true);
           }
        });
    }
}
