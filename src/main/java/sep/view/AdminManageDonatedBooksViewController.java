package sep.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import sep.viewmodel.AdminManageDonatedBooksViewModel;
import sep.viewmodel.MainViewModel;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Controller class for the librarian interface to manage donated books.
 * This class handles user interactions and updates the view based on changes in the underlying data.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminManageDonatedBooksViewController
{
    @FXML
    private Button back;
    @FXML
    private Button approve;
    @FXML
    private Button reject;
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book,String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, Integer> isbnColumn;
    @FXML private TableColumn<Book, Integer> pageCountColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    private Region root;
    private ViewHandler viewHandler;
    private AdminManageDonatedBooksViewModel donatedBooksViewModel;
    private ReadOnlyObjectProperty<Book> selectedBook;

    /**
     * Initializes the view with the provided view handler, view model, and root region.
     *
     * @param viewHandler The view handler for managing views.
     * @param viewModel   The view model for managing donated books.
     * @param root        The root region of the view.
     * @throws RemoteException If there is a problem with remote communication.
     */
    public void init(ViewHandler viewHandler, AdminManageDonatedBooksViewModel viewModel, Region root) throws RemoteException {
        this.viewHandler = viewHandler;
        this.donatedBooksViewModel = viewModel;
        this.root = root;
        initializeTableView();
        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty();
        this.donatedBooksViewModel.bindList(bookTableView.itemsProperty());
        viewModel.resetBookList();
    }

    /**
     * Initializes the table view for displaying donated books.
     * This method sets up the cell value factories for each column in the table view.
     * Each column is bound to a property of the Book object.
     */
    public void initializeTableView(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }
    /**
     * Handles the event when the back button is clicked.
     * This method is triggered when the back button in the user interface is clicked.
     * It navigates back to the admin main view.
     *
     * @throws RemoteException if a remote communication-related exception occurs
     */
    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("adminMainView");
    }

    /**
     * Handles the event when the approve button is clicked.
     * This method is triggered when the approve button in the user interface is clicked.
     * It calls the viewModel to approve the donated book with the selected book's details.
     * If successful, it displays a success message using an alert dialog.
     *
     * @throws SQLException    if a database access error occurs
     * @throws RemoteException if a remote communication-related exception occurs
     */
    @FXML
    private void onApprove() throws SQLException, RemoteException {
        donatedBooksViewModel.approveDonatedBook(selectedBook.get().getBookId(), selectedBook.get().getTitle(), selectedBook.get().getAuthor(),
                selectedBook.get().getIsbn(), selectedBook.get().getYear(), selectedBook.get().getPublisher(), selectedBook.get().getPageCount(),
                selectedBook.get().getGenre());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Book added to system!");
        alert.showAndWait();
    }

    /**
     * Handles the event when the reject button is clicked.
     * This method is triggered when the reject button in the user interface is clicked.
     * It calls the viewModel to reject the donated book with the selected book's ID.
     * If successful, it displays a success message using an alert dialog.
     *
     * @throws SQLException    if a database access error occurs
     * @throws RemoteException if a remote communication-related exception occurs
     */
    @FXML
    private void onReject() throws SQLException, RemoteException {
        donatedBooksViewModel.rejectDonatedBook(selectedBook.get().getBookId());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Book rejected!");
        alert.showAndWait();
    }

    /**
     * Resets the input fields and selection in the user interface.
     * This method clears the input fields and selection in the user interface,
     * allowing the user to reset the form or selection.
     */
    public void reset()
    {

    }

    /**
     * Gets the root node of the view.
     * This method returns the root node of the view, which is used to display the entire view.
     *
     * @return the root node of the view
     */
    public Region getRoot()
    {
        return root;
    }
}
