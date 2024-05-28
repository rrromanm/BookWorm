package sep.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Book;
import sep.model.Patron;
import sep.model.State;
import sep.model.UserSession;
import sep.model.validators.*;
import sep.viewmodel.ProfileViewModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * The controller class for the Profile view.
 * Manages the user's profile information and interactions, such as editing, saving, deleting the account,
 * managing the wishlist, and displaying user details.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class ProfileViewController implements PropertyChangeListener {
    @FXML private Button editButton;
    @FXML private Button passwordButton;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;
    @FXML private Button wishlistButton;
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
    private boolean labelsInitialized;
    private ReadOnlyObjectProperty<Book> wishlistSelectedBook;

    private String originalFirstName;
    private String originalLastName;
    private String originalUsername;
    private String originalEmail;
    private String originalPhoneNumber;
    private String originalPassword;
    private int originalID;

    /**
     * Initializes the ProfileViewController with necessary dependencies and sets up initial UI state.
     *
     * @param viewHandler   The ViewHandler instance for managing view navigation.
     * @param viewModel     The ProfileViewModel instance for managing profile-related operations.
     * @param root          The root Region representing the Profile view.
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    public void init(ViewHandler viewHandler, ProfileViewModel viewModel, Region root)
        throws RemoteException
    {
        this.viewHandler = viewHandler;
        this.profileViewModel = viewModel;
        this.root = root;
        initializeHistoryTableView();
        initializeWishlistTableView();
        populateHBTableView();
        populateWLTableView();
        populateTextFields();
        this.profileViewModel.bindHistoryList(historyOfBookTableView.itemsProperty());
        this.profileViewModel.bindWishlistList(wishlistBookTableView.itemsProperty());
        historyOfBookTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.wishlistSelectedBook = wishlistBookTableView.getSelectionModel().selectedItemProperty();
        wishlistBookTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> onWishlistSelect());
        profileViewModel.addPropertyChangeListener(this);

        booksReadLabel.setVisible(true);
        feesLabel.setVisible(true);
        feesLabel.setText("Outstanding fees: " + UserSession.getInstance().getLoggedInUser().getFees());
        booksReadLabel.setText("Amount of books read: " + profileViewModel.getAmountOfReadBooks(UserSession.getInstance().getLoggedInUser()));

        this.profileViewModel.bindPassword(passwordTextField.textProperty());
        this.profileViewModel.bindFirstName(firstNameTextField.textProperty());
        this.profileViewModel.bindLastName(lastNameTextField.textProperty());
        this.profileViewModel.bindUsername(usernameTextField.textProperty());
        this.profileViewModel.bindEmail(emailTextField.textProperty());
        this.profileViewModel.bindPhoneNumber(phoneNumberTextField.textProperty());
        this.profileViewModel.bindUserId(userIDTextField.textProperty());


        edit = false;
        showPassword = false;
        viewModel.addPropertyChangeListener(this);

    }

    /**
     * Populates the text fields in the profile view with the user's profile data.
     * Invokes the ProfileViewModel to fill the data.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    public void populateTextFields() throws RemoteException {
        profileViewModel.fillData();
    }

    /**
     * Initializes the columns of the wishlist table view with appropriate cell value factories.
     * Associates each column with the corresponding Book properties for displaying wishlist items.
     */
    public void initializeWishlistTableView() {
        WLtitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        WLauthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        WLyearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        WLpublisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        WLisbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        WLpageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        WLbookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        WLgenreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        WLstateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
    }

    /**
     * Initializes the columns of the history table view with appropriate cell value factories.
     * Associates each column with the corresponding Book properties for displaying historical book data.
     */
    public void initializeHistoryTableView() {
        HBtitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        HBauthorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
        HByearColumn.setCellValueFactory(new PropertyValueFactory<>("Year"));
        HBpublisherColumn.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
        HBisbnColumn.setCellValueFactory(new PropertyValueFactory<>("Isbn"));
        HBpageCountColumn.setCellValueFactory(new PropertyValueFactory<>("PageCount"));
        HBbookIdColumn.setCellValueFactory(new PropertyValueFactory<>("BookId"));
        HBgenreColumn.setCellValueFactory(new PropertyValueFactory<>("Genre"));
    }

    /**
     * Populates the wishlist table view with the current user's wishlist items.
     * Invokes the ProfileViewModel to reset the wishlist list.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    public void populateWLTableView() throws RemoteException {
        profileViewModel.resetWishlistList(UserSession.getInstance().getLoggedInUser());
    }

    /**
     * Populates the history table view with the current user's historical book data.
     * Invokes the ProfileViewModel to reset the history list.
     *
     * @throws RemoteException If a communication-related exception occurs during method invocation.
     */
    public void populateHBTableView() throws RemoteException {
        profileViewModel.resetHistoryList(UserSession.getInstance().getLoggedInUser());
    }

    /**
     * Handles the action event when the user clicks the "Edit" button.
     * Toggles between edit mode and view mode for user profile details.
     * Enables editing of profile fields and displays relevant buttons accordingly.
     */
    @FXML
    public void onEdit() {
        try {
            if (!edit) {
                editButton.setStyle("-fx-text-fill:red;");
                firstNameTextField.setEditable(true);
                lastNameTextField.setEditable(true);
                usernameTextField.setEditable(true);
                passwordTextField.setEditable(true);
                emailTextField.setEditable(true);
                phoneNumberTextField.setEditable(true);
                passwordButton.setVisible(true);
                saveButton.setVisible(true);
                deleteButton.setVisible(true);

                originalFirstName = firstNameTextField.getText();
                originalLastName = lastNameTextField.getText();
                originalUsername = usernameTextField.getText();
                originalEmail = emailTextField.getText();
                originalPhoneNumber = phoneNumberTextField.getText();
                originalPassword = passwordTextField.getText();

                edit = true;
            } else {
                editButton.setStyle("");
                reset();
                edit = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        wishlistButton.setDisable(true);
        wishlistButton.setVisible(false);
    }


    /**
     * Handles the action event when the user clicks the "Password" button.
     * Toggles the visibility of the password field and label based on the current state.
     * Changes the style of the password button to indicate visibility status.
     * Disables and hides the wishlist button during password management.
     */
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
        wishlistButton.setDisable(true);
        wishlistButton.setVisible(false);
    }

    /**
     * Saves the changes made to the user profile.
     * Validates the updated user data before saving.
     * Updates the user's information in the system.
     * Resets the editing state after saving changes.
     * Displays an alert indicating successful save or error messages if validation fails.
     * Disables and hides the wishlist button after saving changes.
     */
    @FXML public void onSave(){
        try{
            String oldUsername = UserSession.getInstance().getLoggedInUser().getUsername();
            String oldEmail = UserSession.getInstance().getLoggedInUser().getEmail();
            String oldFirstName = UserSession.getInstance().getLoggedInUser().getFirstName();
            String oldLastName = UserSession.getInstance().getLoggedInUser().getLastName();
            String oldPhoneNumber = UserSession.getInstance().getLoggedInUser().getPhoneNumber();
            String oldPassword = UserSession.getInstance().getLoggedInUser().getPassword();

            String newUsername = usernameTextField.getText();
            String newEmail = emailTextField.getText();
            String newFirstName = firstNameTextField.getText();
            String newLastName = lastNameTextField.getText();
            String newPhoneNumber = phoneNumberTextField.getText();
            String newPassword = passwordTextField.getText();
            Patron newUser = new Patron(UserSession.getInstance().getLoggedInUser().getUserID(),newFirstName,newLastName,newUsername,newPassword,newEmail,newPhoneNumber,UserSession.getInstance().getLoggedInUser().getFees());

            UsernameValidator.validate(newUsername);
            EmailValidator.validate(newEmail);
            PasswordValidator.validate(newPassword);
            PhoneValidator.validate(newPhoneNumber);
            NameValidator.validate(newFirstName);
            NameValidator.validate(newLastName);

            if(!newUsername.equals(oldUsername)){
                profileViewModel.updateUsername(newUsername,originalID);
            }

            if(!newEmail.equals(oldEmail)){
                profileViewModel.updateEmail(newEmail,originalID);
            }

            if(!newFirstName.equals(oldFirstName)){
                profileViewModel.updateFirstName(newFirstName,originalID);
            }

            if(!newLastName.equals(oldLastName)){
                profileViewModel.updateLastName(newLastName,originalID);
            }

            if(!newPhoneNumber.equals(oldPhoneNumber)){
                profileViewModel.updatePhoneNumber(newPhoneNumber,originalID);
            }

            if(!newPassword.equals(oldPassword)){
                profileViewModel.updatePassword(newPassword,originalID);
            }
            UserSession.getInstance().setLoggedInUser(newUser);
            onEdit();
            labelsInitialized = false;
            populateTextFields();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Save changes");
            alert.setHeaderText("Changes to the account has been saved");
            alert.show();


        }catch (Exception e){
            showAlert(e.getMessage());
        }
        wishlistButton.setDisable(true);
        wishlistButton.setVisible(false);

    }

    /**
     * Deletes the user account after confirming with the user.
     * Displays a confirmation dialog to confirm account deletion.
     * If confirmed, deletes the account and navigates to the login view.
     * Displays an error message if account deletion fails.
     * Disables and hides the wishlist button after account deletion.
     *
     * @throws RemoteException if an error occurs during remote communication
     */
    @FXML
    public void onDelete() throws RemoteException{
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Account");
        confirmationAlert.setHeaderText("Are you sure you want to delete your account?");
        confirmationAlert.setContentText("WARNING: ACCOUNT WILL BE PERMANENTLY DELETED");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                profileViewModel.deletePatron();
                viewHandler.openView(ViewFactory.LOGIN);
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("An error occurred while trying to delete your account");
                errorAlert.setContentText(e.getMessage());
                errorAlert.show();
            }
        }
        wishlistButton.setDisable(true);
        wishlistButton.setVisible(false);
    }

    /**
     * Navigates back to the user main view.
     * Opens the user main view using the ViewHandler.
     *
     * @throws RemoteException if an error occurs during remote communication
     */
    @FXML public void onBack() throws RemoteException
    {
        viewHandler.openView(ViewFactory.USERMAIN);
    }


    /**
     * Handles the action of removing a book from the wishlist.
     * Removes the selected book from the wishlist using the ProfileViewModel.
     * Resets the wishlist view and clears the selection in the wishlist table view.
     * Displays a success alert.
     *
     * @throws IOException  if an I/O error occurs
     * @throws SQLException if a SQL error occurs
     */
    @FXML
    public void onWishlist() throws IOException, SQLException {
        profileViewModel.removeFromWishlist(wishlistSelectedBook.get(), UserSession.getInstance().getLoggedInUser());
        profileViewModel.resetWishlistList(UserSession.getInstance().getLoggedInUser());
        wishlistBookTableView.getSelectionModel().clearSelection();
        wishlistButton.setDisable(true);
        wishlistButton.setVisible(false);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Book removed from wishlist!");
        alert.show();
    }

    /**
     * Handles the selection of a book in the wishlist.
     * Enables or disables the wishlist button based on whether a book is selected.
     */
    @FXML
    public void onWishlistSelect() {
        if (wishlistSelectedBook != null) {
            wishlistButton.setDisable(false);
            wishlistButton.setVisible(true);
        } else {
            wishlistButton.setDisable(true);
            wishlistButton.setVisible(false);
        }
    }

    /**
     * Handles the selection of a book in the history.
     * Disables the wishlist button.
     */
    @FXML
    public void onHistorySelect() {
        wishlistButton.setDisable(true);
        wishlistButton.setVisible(false);
    }

    /**
     * Gets the root of the profile view.
     *
     * @return the root of the profile view
     */
    public Region getRoot() {
        return root;
    }

    /**
     * Resets the profile view to its initial state.
     * Sets text fields to their original values, disables editing, and hides password fields.
     *
     * @throws RemoteException if a remote error occurs
     */
    public void reset() throws RemoteException {
        firstNameTextField.setText(originalFirstName);
        lastNameTextField.setText(originalLastName);
        usernameTextField.setText(originalUsername);
        emailTextField.setText(originalEmail);
        phoneNumberTextField.setText(originalPhoneNumber);
        passwordTextField.setText(originalPassword);

        populateTextFields();
        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        usernameTextField.setEditable(false);
        passwordTextField.setEditable(false);
        emailTextField.setEditable(false);
        phoneNumberTextField.setEditable(false);
        userIDTextField.setEditable(false);
        passwordButton.setVisible(false);
        saveButton.setVisible(false);
        deleteButton.setVisible(false);
        edit = false;

        passwordTextField.setVisible(false);
        passwordLabel.setVisible(false);
        passwordButton.setStyle("");
        showPassword = false;
    }

    /**
     * Displays an alert with the given message.
     *
     * @param message the message to display in the alert
     */
    public void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Responds to property change events.
     * Displays a notification when the patron's account is updated.
     * Updates the fees and books read labels when the user logs in.
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("updatePatron".equals(evt.getPropertyName())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText("Changes made to your account!");
            alert.setContentText("Please logout and login with new credentials.");
            alert.show();
        }
        if ("login".equals(evt.getPropertyName())) {
            try {
                feesLabel.setText("Outstanding fees: " + UserSession.getInstance().getLoggedInUser().getFees());
                booksReadLabel.setText("Amount of books read: " + profileViewModel.getAmountOfReadBooks(UserSession.getInstance().getLoggedInUser()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

