package sep.view;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Book;
import sep.model.Patron;
import sep.model.State;
import sep.model.UserSession;
import sep.viewmodel.MyBooksViewModel;
import sep.viewmodel.ProfileViewModel;

import java.rmi.RemoteException;

public class MyBooksViewController implements RemotePropertyChangeListener
{
    @FXML private Button returnButton;
    @FXML private Button extendButton;
    @FXML private Button backButton;
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book,String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, Integer> isbnColumn;
    @FXML private TableColumn<Book, Integer> pageCountColumn;
    @FXML private TableColumn<Book, Integer> bookIdColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    private Region root;
    private ViewHandler viewHandler;
    private MyBooksViewModel myBooksViewModel;
    private ReadOnlyObjectProperty<Book> selectedBook;
    private Patron loggedInUser;
    public void init(ViewHandler viewHandler, MyBooksViewModel viewModel, Region root)
        throws RemoteException
    {
        this.viewHandler = viewHandler;
        this.myBooksViewModel = viewModel;
        this.root = root;
        loggedInUser = UserSession.getInstance().getLoggedInUser();
        //myBooksViewModel.addPropertyChangeListener(this);
        initializeTableView();
        populateTableView();
        this.myBooksViewModel.bindList(bookTableView.itemsProperty());
        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty();
    }
    public void initializeTableView(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }
    public void populateTableView() throws RemoteException
    {
        myBooksViewModel.resetBookList(loggedInUser);
    }
    @FXML public void onReturn(){
        //myBooksViewModel.returnBook(); // it should have this method
    }
    @FXML public void onExtend(){
        // extend the deadline for the book
    }
    @FXML public void onBack(){
        viewHandler.openView(ViewFactory.USERMAIN);
    }
    @FXML public void onSelect(){
        returnButton.setDisable(false);
        /*if(selectedBook.get())
            here we need to implement so the extendButton appears based on the remaining deadline
         */
    }

    public Region getRoot() {
        return root;
    }
    public void reset(){

    }

    @Override public void propertyChange(RemotePropertyChangeEvent evt) throws RemoteException
    {
        Platform.runLater(() -> {
            if ("UserLoggedIn".equals(evt.getPropertyName())) {
                // Handle user logged-in event
                loggedInUser = (Patron) evt.getNewValue();
                System.out.println("User logged in: " + loggedInUser.getUsername());
                // Perform any UI updates or actions here
            }
        });
    }
}
