package sep.view;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import javafx.application.Platform;
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
    public void init(ViewHandler viewHandler, MainViewModel viewModel, Region root)
        throws RemoteException, SQLException
    {
        this.viewHandler = viewHandler;
        this.mainViewModel = viewModel;
        this.root = root;
        this.support = new RemotePropertyChangeSupport<>();
        initializeTableView();
        initializeStateComboBox();
        initializeGenreComboBox(); // not completed
        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty();
        this.mainViewModel.bindList(bookTableView.itemsProperty());
        viewModel.resetBookList();
        checkFees();
        // somehow we need to figure out how to change the button to an image of the bell for notification and
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
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
    }
    public void initializeStateComboBox(){
        String[] stateString = {"All","Available", "Borrowed"};
        stateComboBox.getItems().addAll(stateString);
        stateComboBox.getSelectionModel().selectFirst();
    }
    public void initializeGenreComboBox() throws SQLException
    {
        genreComboBox.getItems().add("All");
        genreComboBox.getItems().addAll(BookDatabaseImplementation.getInstance()
            .readGenres());
        genreComboBox.getSelectionModel().selectFirst();
    }
    @FXML public void onStateClick() throws RemoteException {
        String genreChoice = genreComboBox.getSelectionModel().getSelectedItem();
        String stateChoice = stateComboBox.getSelectionModel().getSelectedItem();
        String searchChoice = searchTextField.getText();
        mainViewModel.showFiltered(stateChoice,genreChoice,searchChoice);
    }

    @FXML public void onGenreClick() throws RemoteException {
        String genreChoice = genreComboBox.getSelectionModel().getSelectedItem();
        String stateChoice = stateComboBox.getSelectionModel().getSelectedItem();
        String searchChoice = searchTextField.getText();
        mainViewModel.showFiltered(stateChoice,genreChoice,searchChoice);
    }
    @FXML public void onSearch() throws RemoteException
    {
        String genreChoice = genreComboBox.getSelectionModel().getSelectedItem();
        String stateChoice = stateComboBox.getSelectionModel().getSelectedItem();
        String searchChoice = searchTextField.getText();
        mainViewModel.showFiltered(stateChoice,genreChoice,searchChoice);
    }

    @FXML public void onViewProfile() throws RemoteException
    {
        viewHandler.openView(ViewFactory.PROFILE);
    }
    @FXML public void onMyBooks() throws RemoteException
    {
        viewHandler.openView(ViewFactory.MYBOOKS);
    }
    @FXML public void onSeeEvents() throws RemoteException
    {
        viewHandler.openView(ViewFactory.EVENTSVIEW);
    }
    @FXML public void onDonate() throws RemoteException
    {
        viewHandler.openView(ViewFactory.DONATEBOOK);
    }

    @FXML public void onBorrow() throws IOException, SQLException {
        if(mainViewModel.getAmountOfBorrowedBooks(UserSession.getInstance().getLoggedInUser()) < 3)
        {
            mainViewModel.borrowBook(selectedBook.get(), UserSession.getInstance().getLoggedInUser());
            mainViewModel.resetBookList();
            bookTableView.getSelectionModel().clearSelection();
            borrowButton.setDisable(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Book borrowed successfully, enjoy!");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You cannot borrow more books");
            alert.setHeaderText("You reached the maximum amount of borrowed books");
            alert.setContentText("In order to borrow more books return the previous ones");
            alert.show();
        }
    }
    @FXML public void onWishlist() throws IOException, SQLException{
        mainViewModel.wishlistBook(selectedBook.get(),UserSession.getInstance().getLoggedInUser());
        mainViewModel.resetBookList();
        bookTableView.getSelectionModel().clearSelection();
        wishlistButton.setDisable(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Book wishlisted!");
        alert.show();
    }
    @FXML public void onHelp() throws RemoteException
    {
        viewHandler.openView(ViewFactory.HELP);
    }
    @FXML public void onLogout() throws RemoteException {
        viewHandler.openView(ViewFactory.LOGIN);
        // maybe we need to add some more so the user disconnects from the server
    }
    @FXML public void onSelect() throws SQLException, RemoteException
    {
        if(selectedBook.get().getState() instanceof Available)
        {
            borrowButton.setDisable(false);
        }
        if(selectedBook.get().getState() instanceof Borrowed) {
            borrowButton.setDisable(true);
        }
        if(mainViewModel.isWishlisted(selectedBook.get(),UserSession.getInstance().getLoggedInUser())){
            wishlistButton.setDisable(true);
        }
        if(!mainViewModel.isWishlisted(selectedBook.get(),UserSession.getInstance().getLoggedInUser())){
            wishlistButton.setDisable(false);
        }
    }

    public Region getRoot(){
        return root;
    }

    public void checkFees()
    {
        if (UserSession.getInstance().getLoggedInUser().getFees() > 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText("You have unpaid fees: " + UserSession.getInstance().getLoggedInUser().getFees());
            alert.show();
        }
    }

    public void reset()
    {
        searchTextField.clear();
        stateComboBox.getSelectionModel().selectFirst();
        borrowButton.setDisable(true);
    }
}