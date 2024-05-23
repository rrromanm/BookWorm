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
    @FXML private TableColumn<Book, String> returnDateColumn;

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
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    public void populateTableView() throws RemoteException
    {
        myBooksViewModel.resetBookList(UserSession.getInstance().getLoggedInUser());
    }

    @FXML
    public void onReturn() throws IOException, SQLException {

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Return");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to return the selected book?");

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

            confirmationAlert.showAndWait().ifPresent(type -> {
                if (type == yesButton) {
                    try {
                        myBooksViewModel.returnBook(selectedBook.get(), UserSession.getInstance().getLoggedInUser());
                        populateTableView();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Book Returned");
                        alert.setHeaderText(null);
                        alert.setContentText("The book has been successfully returned.");
                        alert.showAndWait();
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Failed to return the book.");
                        alert.setContentText("An error occurred while processing the return. Please try again.");
                        alert.showAndWait();
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("An unexpected error occurred.");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });
    }



    @FXML public void onExtend() throws SQLException, RemoteException {
        myBooksViewModel.extendBook(selectedBook.get(), UserSession.getInstance().getLoggedInUser());
        bookTableView.getSelectionModel().clearSelection();
        extendButton.setDisable(true);
        booksToExtend = myBooksViewModel.checkBooksToExtend(UserSession.getInstance().getLoggedInUser());
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
        boolean canExtend = false;
        for (String bookTitle : booksToExtend) {
            if (bookTitle.equals(selectedBook.get().getTitle())) {
                canExtend = true;
                break;
            }
        }
        extendButton.setDisable(!canExtend);
    }


    public Region getRoot() {
        return root;
    }

    public void reset() {

    }
}
