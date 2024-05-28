package sep.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Book;
import sep.model.Patron;
import sep.model.UserSession;
import sep.viewmodel.MyBooksViewModel;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller class responsible for managing the My Books view. This view allows users to
 * view their borrowed books, return books, and extend the borrowing period if applicable.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
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

    /**
     * Initializes the controller with the required dependencies and sets up the initial state of the UI components.
     *
     * @param viewHandler The view handler responsible for managing views in the application.
     * @param viewModel The view model associated with this view.
     * @param root The root region where the UI components will be displayed.
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
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

    /**
     * Initializes the table view by setting up cell value factories for each column.
     */
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

    /**
     * Populates the table view with the user's books.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    public void populateTableView() throws RemoteException
    {
        myBooksViewModel.resetBookList(UserSession.getInstance().getLoggedInUser());
    }

    /**
     * Handles the action when the user clicks the "Return" button.
     * Displays a confirmation dialog asking the user if they are sure they want to return the selected book.
     * If confirmed, returns the selected book and updates the table view accordingly.
     * Displays success or error alerts based on the outcome of the return operation.
     *
     * @throws IOException If an I/O exception occurs while performing the action.
     * @throws SQLException If an SQL exception occurs while interacting with the database.
     */
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


    /**
     * Handles the action when the user clicks the "Extend" button.
     * Extends the return date for the selected book if it's eligible for extension.
     * Clears the selection in the table view, disables the extend button, updates the list of books eligible for extension,
     * and refreshes the table view to reflect the changes.
     * Displays a success alert informing the user that the book has been successfully extended.
     *
     * @throws SQLException If an SQL exception occurs while interacting with the database.
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
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

    /**
     * Handles the action when the user clicks the "Back" button.
     * Navigates back to the main user view by opening the User Main view.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    @FXML public void onBack() throws RemoteException
    {
        viewHandler.openView(ViewFactory.USERMAIN);
    }

    /**
     * Handles the action when a book is selected in the table view.
     * Enables the return button and checks if the selected book is eligible for extension.
     * If the selected book is eligible for extension, enables the extend button; otherwise, disables it.
     */
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

    /**
     * Retrieves the root region of the UI.
     *
     * @return The root region of the UI.
     */
    public Region getRoot() {
        return root;
    }

    /**
     * Resets the UI components.
     */
    public void reset() {

    }
}
