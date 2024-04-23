package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Book;
import sep.model.State;
import sep.viewmodel.MainViewModel;
import sep.viewmodel.ProfileViewModel;

import java.util.Optional;

public class ProfileViewController {
    @FXML private Button editButton;
    @FXML private Button passwordButton;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;
    @FXML private Label passwordLabel;
    @FXML private Label feesLabel;
    @FXML private Label booksReadLabel;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField userIDTextField;
    @FXML private TableView<Book> wishlistBookTableView;
    @FXML private TableColumn<Book,String> WLtitleColumn;
    @FXML private TableColumn<Book, String> WLauthorColumn;
    @FXML private TableColumn<Book, Integer> WLyearColumn;
    @FXML private TableColumn<Book, String> WLpublisherColumn;
    @FXML private TableColumn<Book, Integer> WLisbnColumn;
    @FXML private TableColumn<Book, Integer> WLpageCountColumn;
    @FXML private TableColumn<Book, Integer> WLbookIdColumn;
    @FXML private TableColumn<Book, String> WLgenreColumn;
    @FXML private TableColumn<Book, State> WLstateColumn;
    @FXML private TableView<Book> historyOfBookTableView;
    @FXML private TableColumn<Book,String> HBtitleColumn;
    @FXML private TableColumn<Book, String> HBauthorColumn;
    @FXML private TableColumn<Book, Integer> HByearColumn;
    @FXML private TableColumn<Book, String> HBpublisherColumn;
    @FXML private TableColumn<Book, Integer> HBisbnColumn;
    @FXML private TableColumn<Book, Integer> HBpageCountColumn;
    @FXML private TableColumn<Book, Integer> HBbookIdColumn;
    @FXML private TableColumn<Book, String> HBgenreColumn;
    private Region root;
    private ViewHandler viewHandler;
    private ProfileViewModel profileViewModel;
    private boolean edit;
    private boolean showPassword;
    public void init(ViewHandler viewHandler, ProfileViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.profileViewModel = viewModel;
        this.root = root;
        initializeHistoryTableView();
        initializeWishlistTableView();
        populateHBTableView(); // not implemented
        populateWLTableView(); // not implemented
        initializeLabels(); // not completed
        fillTextFields(); // not implemented
        edit = false;
        showPassword = false;
    }
    public void initializeLabels(){
        booksReadLabel.setVisible(true);
        feesLabel.setVisible(true);
        // we need to get the data about the user fees and the amount of books read by him
        feesLabel.setText("Outstanding fees: " + /*fees amount*/);
        booksReadLabel.setText("Books read: " + /*amount of books read by the user*/);
    }
    public void initializeWishlistTableView(){
        WLtitleColumn.setCellValueFactory(new PropertyValueFactory<>("WLtitle"));
        WLauthorColumn.setCellValueFactory(new PropertyValueFactory<>("WLauthor"));
        WLyearColumn.setCellValueFactory(new PropertyValueFactory<>("WLyear"));
        WLpublisherColumn.setCellValueFactory(new PropertyValueFactory<>("WLpublisher"));
        WLisbnColumn.setCellValueFactory(new PropertyValueFactory<>("WLisbn"));
        WLpageCountColumn.setCellValueFactory(new PropertyValueFactory<>("WLpageCount"));
        WLbookIdColumn.setCellValueFactory(new PropertyValueFactory<>("WLbookId"));
        WLgenreColumn.setCellValueFactory(new PropertyValueFactory<>("WLgenre"));
        WLstateColumn.setCellValueFactory(new PropertyValueFactory<>("WLstate"));
    }
    public void initializeHistoryTableView(){
        HBtitleColumn.setCellValueFactory(new PropertyValueFactory<>("HBtitle"));
        HBauthorColumn.setCellValueFactory(new PropertyValueFactory<>("HBauthor"));
        HByearColumn.setCellValueFactory(new PropertyValueFactory<>("HByear"));
        HBpublisherColumn.setCellValueFactory(new PropertyValueFactory<>("HBpublisher"));
        HBisbnColumn.setCellValueFactory(new PropertyValueFactory<>("HBisbn"));
        HBpageCountColumn.setCellValueFactory(new PropertyValueFactory<>("HBpageCount"));
        HBbookIdColumn.setCellValueFactory(new PropertyValueFactory<>("HBbookId"));
        HBgenreColumn.setCellValueFactory(new PropertyValueFactory<>("HBgenre"));
    }
    public void fillTextFields(){
        // We need to get the data about the user and display it in the textFields
    }
    public void populateWLTableView(){
        // We need to get the data about the user wishlist and populate the table with the books
    }
    public void populateHBTableView(){
        // We need to get the data about the user history of books and populate the table with the books
    }
    @FXML public void onEdit(){
        if(!edit) {
            editButton.setStyle("-fx-text-fill:red;"); // color of the clicked button can be changed
            firstNameTextField.setEditable(true);
            lastNameTextField.setEditable(true);
            usernameTextField.setEditable(true);
            passwordTextField.setEditable(true);
            emailTextField.setEditable(true);
            phoneNumberTextField.setEditable(true);
            userIDTextField.setEditable(true);
            passwordButton.setVisible(true);
            saveButton.setVisible(true);
            edit = true;
        }
        else
        {
            editButton.setStyle("");
            firstNameTextField.setEditable(false);
            lastNameTextField.setEditable(false);
            usernameTextField.setEditable(false);
            passwordTextField.setEditable(false);
            emailTextField.setEditable(false);
            phoneNumberTextField.setEditable(false);
            userIDTextField.setEditable(false);
            passwordButton.setVisible(false);
            saveButton.setVisible(false);
            reset();
            edit = false;
        }
    }
    @FXML public void onPassword(){
        if(!showPassword){
            passwordButton.setStyle("-fx-text-fill:red;");
            passwordTextField.setVisible(true);
            passwordLabel.setVisible(true);
            showPassword = true;
        }
        else
        {
            passwordButton.setStyle("");
            passwordTextField.setVisible(false);
            passwordLabel.setVisible(false);
            showPassword = false;
        }
    }
    @FXML public void onSave(){
        // we need to save the changes to the database
    }
    @FXML public void onDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.setContentText("WARNING: ACCOUNT WILL BE PERMANENTLY DELETED");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // here we need to implement deleting of the account
        }
    }
    @FXML public void onBack(){
        viewHandler.openView(ViewFactory.USERMAIN);
    }
    public Region getRoot(){
        return root;
    }
    public void reset()
    {
        fillTextFields(); //not implemented
        initializeLabels();
        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        usernameTextField.setEditable(false);
        passwordTextField.setEditable(false);
        emailTextField.setEditable(false);
        phoneNumberTextField.setEditable(false);
        userIDTextField.setEditable(false);
        passwordButton.setVisible(false);
        saveButton.setVisible(false);
        edit =false;
        showPassword = false;
    }
}
