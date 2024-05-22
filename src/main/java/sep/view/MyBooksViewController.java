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

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyBooksViewController
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
    private ArrayList<String> booksToExtend;

    public void init(ViewHandler viewHandler, MyBooksViewModel viewModel, Region root) throws RemoteException
    {
        this.viewHandler = viewHandler;
        this.myBooksViewModel = viewModel;
        this.root = root;
        loggedInUser = UserSession.getInstance().getLoggedInUser();
        initializeTableView();
        populateTableView();
        this.myBooksViewModel.bindList(bookTableView.itemsProperty());
        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty();
        booksToExtend = viewModel.checkBooksToExtend(UserSession.getInstance().getLoggedInUser());
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
        myBooksViewModel.resetBookList(UserSession.getInstance().getLoggedInUser());
    }

    @FXML public void onReturn() throws IOException, SQLException {
        myBooksViewModel.returnBook(selectedBook.get(), UserSession.getInstance().getLoggedInUser());
        populateTableView();
    }

    @FXML public void onExtend() throws SQLException, RemoteException {
        myBooksViewModel.extendBook(selectedBook.get(), UserSession.getInstance().getLoggedInUser());
        bookTableView.getSelectionModel().clearSelection();
        extendButton.setDisable(true);
        populateTableView();


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Book successfully extended, enjoy!");
        alert.setContentText("PS: The book return date was extended by 10 days.");
        alert.show();
    }

    @FXML public void onBack() throws RemoteException
    {
        viewHandler.openView(ViewFactory.USERMAIN);
    }

    @FXML public void onSelect(){
        returnButton.setDisable(false);
        for (int i = 0; i < booksToExtend.size(); i++) {
            if (booksToExtend.get(i).equals(selectedBook.get().getTitle())) {
                extendButton.setDisable(false);
            }
            else {
                extendButton.setDisable(true);
            }
        }
    }

    public Region getRoot() {
        return root;
    }

    public void reset() {

    }
}
