package sep.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.*;
import sep.viewmodel.MainViewModel;

public class MainViewController {
    @FXML private Button notificationButton;
    @FXML private Button viewProfileButton;
    @FXML private Button myBooksButton;
    @FXML private Button seeEventsButton;
    @FXML private Button donateButton;
    @FXML private Button reserveButton;
    @FXML private Button borrowButton;
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
    public void init(ViewHandler viewHandler, MainViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.mainViewModel = viewModel;
        this.root = root;
        initializeTableView();
        initializeStateComboBox();
        initializeGenreComboBox(); // not completed
        populateBookTableView(); // not implemented at all
        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty();


        // somehow we need to figure out how to change the button to an image of the bell for notification and
        // make imageView fit into the circle
        // populate the tableView should also be here (we will do it from the database)
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
        String[] stateString = {"Available,Reserved,Borrowed,Borrowed & Reserved"};
        stateComboBox.getItems().addAll(stateString);
        stateComboBox.getSelectionModel().selectFirst();
    }
    public void initializeGenreComboBox(){
        String[] genreString = {""};  // I have no idea what genres are we going to have
        genreComboBox.getItems().addAll(genreString);
        genreComboBox.getSelectionModel().selectFirst();
    }
    public void populateBookTableView()
    {
        //needs to be implemented
    }
    @FXML public void onViewProfile()
    {
       viewHandler.openView(ViewFactory.//VIEWPROFILE);
    }
    @FXML public void onMyBooks()
    {
        viewHandler.openView(ViewFactory.//MYBOOKS);
    }
    @FXML public void onSeeEvents()
    {
        viewHandler.openView(ViewFactory.//EVENTS);
    }
    @FXML public void onDonate()
    {
        viewHandler.openView(ViewFactory.//DONATE);
    }
    @FXML public void onReserve()
    {
        mainViewModel.reserve(); // the method should be here
    }
    @FXML public void onBorrow()
    {
        mainViewModel.borrow(); //the method should be here
    }
    @FXML public void onSearch()
    {
        // it needs to take whatever is in the TextField and then search through the database and get the result in the TableView
    }
    @FXML public void onHelp()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Contact Info");
        alert.setContentText("If you need any help please check out our User Guide or contact us here: +45 77 22 11 84");
        alert.showAndWait();
        // I think we wanted on this to download the pdf file of the User Guide. We will do it later
    }
    @FXML public void onLogout()
    {
        viewHandler.closeView();
        // maybe we need to add some more so the user disconnects from the server
    }
    @FXML public void onSelect(){
        if(selectedBook.get().getState() instanceof Available)
        {
            borrowButton.setDisable(true);
            reserveButton.setDisable(false);
        }
        if(selectedBook.get().getState() instanceof Reserved)
        {
            borrowButton.setDisable(false);  // it can cause some problems since it was designed for the single user client
            reserveButton.setDisable(true);
        }
        if(selectedBook.get().getState() instanceof Borrowed)
        {
            borrowButton.setDisable(true);
            reserveButton.setDisable(false);
        }
        if(selectedBook.get().getState() instanceof Borrowed_Reserved)
        {
            borrowButton.setDisable(true);
            reserveButton.setDisable(true);
        }

        // we still need to figure out how to show the description of the book
    }
    public Region getRoot(){
        return root;
    }
    public void reset()
    {
        searchTextField.clear();
        stateComboBox.getSelectionModel().selectFirst();
        borrowButton.setDisable(true);
        reserveButton.setDisable(true);
    }


}
