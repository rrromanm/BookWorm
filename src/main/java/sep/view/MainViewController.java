package sep.view;

import dk.via.remote.observer.RemotePropertyChangeSupport;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.jdbc.BookDatabaseImplementation;
import sep.model.*;
import sep.viewmodel.MainViewModel;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 *
 * This class is the controller for the main view. It handles user interactions for various functionalities
 * such as viewing the user's profile, notifications, borrowed books, events, donating books, borrowing books,
 * wishlisting books, getting help, and logging out. It also manages the display of books in a table view,
 * searching for books, and updating book states based on user actions.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class MainViewController
{
    @FXML private Button notificationButton;
    @FXML private Button viewProfileButton;
    @FXML private Button myBooksButton;
    @FXML private Button seeEventsButton;
    @FXML private Button donateButton;
    @FXML private Button borrowButton;
    @FXML private Button wishlistButton;
    @FXML private Button helpButton;
    @FXML private Button logoutButton;
    @FXML private Button searchButton;
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book,String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, Integer> isbnColumn;
    @FXML private TableColumn<Book, Integer> pageCountColumn;
    @FXML private TableColumn<Book, Integer> bookIdColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    @FXML private TableColumn<Book, State> stateColumn;
    @FXML private ComboBox<String> genreComboBox;
    @FXML private ComboBox<String> stateComboBox;
    @FXML private TextField searchTextField;

    private Region root;
    private ViewHandler viewHandler;
    private MainViewModel mainViewModel;
    private ReadOnlyObjectProperty<Book> selectedBook;
    private RemotePropertyChangeSupport<Patron> support;

    /**
     * Initializes the components of the application, including setting up the view handler, main view model,
     * and root region. It also initializes the table view, state combo box, and genre combo box. Additionally,
     * it binds the selected book property to the table view's selected item property, binds the main view model's
     * book list to the table view's items property, resets the book list in the view model, and finally,
     * triggers the method to see notifications.
     *
     * @param viewHandler The view handler responsible for managing views in the application.
     * @param viewModel The main view model containing the application's data and logic.
     * @param root The root region where the UI components will be displayed.
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     * @throws SQLException If an SQL exception occurs while interacting with the database.
     */
    public void init(ViewHandler viewHandler, MainViewModel viewModel, Region root)
        throws RemoteException, SQLException
    {
        this.viewHandler = viewHandler;
        this.mainViewModel = viewModel;
        this.root = root;
        this.support = new RemotePropertyChangeSupport<>();
        initializeTableView();
        initializeStateComboBox();
        initializeGenreComboBox();
        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty();
        this.mainViewModel.bindList(bookTableView.itemsProperty());
        viewModel.resetBookList();
        seeNotifications();
    }
    /**
     * Initializes the table view by setting up cell value factories for each column:
     * title, author, year, publisher, ISBN, page count, book ID, genre, and state.
     * Each cell value factory is set to retrieve the corresponding property value
     * from the Book model object.
     */
    public void initializeTableView() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
    }

    /**
     * Initializes the state combo box by populating it with options "All", "Available",
     * and "Borrowed". The default selection is set to "All".
     */
    public void initializeStateComboBox() {
        String[] stateString = {"All", "Available", "Borrowed"};
        stateComboBox.getItems().addAll(stateString);
        stateComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Initializes the genre combo box by populating it with options "All" and genres
     * retrieved from the database. The default selection is set to "All".
     *
     * @throws SQLException If an SQL exception occurs while retrieving genres from the database.
     */
    public void initializeGenreComboBox() throws SQLException {
        genreComboBox.getItems().add("All");
        genreComboBox.getItems().addAll(BookDatabaseImplementation.getInstance().readGenres());
        genreComboBox.getSelectionModel().selectFirst();
    }


    /**
     * Handles the search action by retrieving the selected genre, state, and search text,
     * then instructs the main view model to display filtered results based on the provided criteria.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    @FXML
    public void onSearch() throws RemoteException {
        String genreChoice = genreComboBox.getSelectionModel().getSelectedItem();
        String stateChoice = stateComboBox.getSelectionModel().getSelectedItem();
        String searchChoice = searchTextField.getText();
        mainViewModel.showFiltered(stateChoice, genreChoice, searchChoice);
    }

    /**
     * Handles the action to view the user profile by opening the profile view using the view handler.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    @FXML
    public void onViewProfile() throws RemoteException {
        viewHandler.openView(ViewFactory.PROFILE);
    }

    /**
     * Handles the action to view notifications by triggering the method to display notifications.
     *
     * @throws SQLException If an SQL exception occurs while interacting with the database.
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    @FXML
    public void onNotification() throws SQLException, RemoteException {
        seeNotifications();
    }

    /**
     * Handles the action to view the user's books by opening the my books view using the view handler.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    @FXML
    public void onMyBooks() throws RemoteException {
        viewHandler.openView(ViewFactory.MYBOOKS);
    }

    /**
     * Handles the action to view events by opening the events view using the view handler.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    @FXML
    public void onSeeEvents() throws RemoteException {
        viewHandler.openView(ViewFactory.EVENTSVIEW);
    }

    /**
     * Handles the action to donate a book by opening the donate book view using the view handler.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    @FXML
    public void onDonate() throws RemoteException {
        viewHandler.openView(ViewFactory.DONATEBOOK);
    }


    /**
     * Handles the action to borrow a book. Checks if the user has not reached the maximum limit of borrowed books.
     * If the condition is met, the book is borrowed for the logged-in user, and a success message is displayed.
     * Otherwise, a message indicating that the user cannot borrow more books is shown.
     *
     * @throws IOException If an I/O exception occurs while performing the action.
     * @throws SQLException If an SQL exception occurs while interacting with the database.
     */
    @FXML
    public void onBorrow() throws IOException, SQLException {
        if (mainViewModel.getAmountOfBorrowedBooks(UserSession.getInstance().getLoggedInUser()) < 3) {
            mainViewModel.borrowBook(selectedBook.get(), UserSession.getInstance().getLoggedInUser());
            mainViewModel.resetBookList();
            bookTableView.getSelectionModel().clearSelection();
            borrowButton.setDisable(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Book borrowed successfully, enjoy!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You cannot borrow more books");
            alert.setHeaderText("You reached the maximum amount of borrowed books");
            alert.setContentText("In order to borrow more books return the previous ones");
            alert.show();
        }
    }

    /**
     * Handles the action to add a book to the wishlist for the logged-in user.
     * After adding the book, the book list is reset, and the wishlist button is disabled.
     * A success message is displayed to inform the user about the action.
     *
     * @throws IOException If an I/O exception occurs while performing the action.
     * @throws SQLException If an SQL exception occurs while interacting with the database.
     */
    @FXML
    public void onWishlist() throws IOException, SQLException {
        mainViewModel.wishlistBook(selectedBook.get(), UserSession.getInstance().getLoggedInUser());
        mainViewModel.resetBookList();
        bookTableView.getSelectionModel().clearSelection();
        wishlistButton.setDisable(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Book wishlisted!");
        alert.show();
    }

    /**
     * Handles the action to open the help view using the view handler.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    @FXML
    public void onHelp() throws RemoteException {
        viewHandler.openView(ViewFactory.HELP);
    }

    /**
     * Handles the action to log out by opening the login view using the view handler.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    @FXML
    public void onLogout() throws RemoteException {
        viewHandler.openView(ViewFactory.LOGIN);
    }

    /**
     * Handles the action when a book is selected in the table view. It checks the state of the selected book
     * and enables or disables the borrow button accordingly. It also checks if the book is wishlisted for the
     * logged-in user and disables the wishlist button accordingly.
     *
     * @throws SQLException If an SQL exception occurs while interacting with the database.
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    @FXML
    public void onSelect() throws SQLException, RemoteException {
        if (selectedBook.get().getState() instanceof Available) {
            borrowButton.setDisable(false);
        }
        if (selectedBook.get().getState() instanceof Borrowed) {
            borrowButton.setDisable(true);
        }
        if (mainViewModel.isWishlisted(selectedBook.get(), UserSession.getInstance().getLoggedInUser())) {
            wishlistButton.setDisable(true);
        }
        if (!mainViewModel.isWishlisted(selectedBook.get(), UserSession.getInstance().getLoggedInUser())) {
            wishlistButton.setDisable(false);
        }
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
     * Displays notifications to the user, including information about unpaid fees and books whose borrowing time is ending.
     *
     * @throws SQLException If an SQL exception occurs while interacting with the database.
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    public void seeNotifications() throws SQLException, RemoteException {
        if (UserSession.getInstance().getLoggedInUser().getFees() > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText("You have unpaid fees: " + UserSession.getInstance().getLoggedInUser().getFees());
            alert.setContentText("Please pay them as soon as possible!");
            alert.show();
        }
        if (!mainViewModel.getEndingBooks(UserSession.getInstance().getLoggedInUser()).isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText("Your borrowing time is coming to an end");
            stringBuilder.append("The books: ");
            for (String bookTitle : mainViewModel.getEndingBooks(UserSession.getInstance().getLoggedInUser())) {
                stringBuilder.append("\n").append("\"" + bookTitle + "\"");
            }
            alert.setContentText(stringBuilder.toString());
            alert.show();
        }
    }

    /**
     * Resets the UI components, clearing the search text field and resetting the state combo box.
     * Additionally, it disables the borrow button.
     */
    public void reset() {
        searchTextField.clear();
        stateComboBox.getSelectionModel().selectFirst();
        borrowButton.setDisable(true);
    }

}