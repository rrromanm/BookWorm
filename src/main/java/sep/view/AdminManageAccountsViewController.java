package sep.view;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Event;
import sep.model.Patron;
import sep.model.UserSession;
import sep.model.validators.*;
import sep.viewmodel.AdminManageAccountsViewModel;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class AdminManageAccountsViewController implements RemotePropertyChangeListener {
    private ViewHandler viewHandler;
    private AdminManageAccountsViewModel viewModel;
    private Region root;
    @FXML
    private Button backButton;
    @FXML
    private Button SaveButton;
    @FXML
    private Button savePatronChangesButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button SaveUserButton;
    @FXML
    private TableView<Patron> UserTableView;
    @FXML private TableColumn<Patron, Integer> IDcolumn;
    @FXML private TableColumn<Patron, String> FirstNameColumn;
    @FXML private TableColumn<Patron, String> LastNameColumn;
    @FXML private TableColumn<Patron, String> UsernameColumn;
    @FXML private TableColumn<Patron, String> EmailColumn;
    @FXML private TableColumn<Patron, Integer> phoneNumberColumn;
    @FXML private TableColumn<Patron, Integer> FeeColumn;

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField usernameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField userIDTextField;

    @FXML private TextField feesTextField;

    private String originalFirstName;
    private String originalLastName;
    private String originalUsername;
    private String originalEmail;
    private String originalPhoneNumber;
    private int originalFee;

    private ReadOnlyObjectProperty<Patron> selectedUser;

    public void init(ViewHandler viewHandler, AdminManageAccountsViewModel viewModel, Region root) throws RemoteException {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeTableView();
        this.selectedUser = UserTableView.getSelectionModel().selectedItemProperty();
        this.viewModel.bindList(UserTableView.itemsProperty());
        initializeTextFields();
        loadTableData();

        viewModel.addPropertyChangeListener(evt -> {
            if ("patronList".equals(evt.getPropertyName())) {
                UserTableView.refresh();
            }
            if ("removePatron".equals(evt.getPropertyName())) {
                UserTableView.refresh();
                initializeTextFields();
            }
            if ("updatePatron".equals(evt.getPropertyName())) {
                UserTableView.refresh();
                initializeTextFields();
            }
        });
    }

    private void loadTableData() {
        try {
            viewModel.loadPatrons();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initializeTableView() {
        IDcolumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        UsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        FeeColumn.setCellValueFactory(new PropertyValueFactory<>("fees"));
    }

    public void initializeTextFields() {
        selectedUser.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                userIDTextField.setText(String.valueOf(newValue.getUserID()));
                firstNameTextField.setText(newValue.getFirstName());
                lastNameTextField.setText(newValue.getLastName());
                usernameTextField.setText(newValue.getUsername());
                emailTextField.setText(newValue.getEmail());
                phoneNumberTextField.setText(String.valueOf(newValue.getPhoneNumber()));
                feesTextField.setText(String.valueOf(newValue.getFees()));

                originalUsername = newValue.getUsername();
                originalFirstName = newValue.getFirstName();
                originalLastName = newValue.getLastName();
                originalEmail = newValue.getEmail();
                originalPhoneNumber = newValue.getPhoneNumber();
                originalFee = newValue.getFees();
            }
        });
    }

    @FXML
    private void backButtonClicked() throws RemoteException {
        viewHandler.openView("adminMainView");
    }

    @FXML
    private void savePatronChangesButtonClicked() throws RemoteException, SQLException {
        int userID = Integer.parseInt(userIDTextField.getText());
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();

        try {
            UsernameValidator.validate(username);
            EmailValidator.validate(email);
            PhoneValidator.validate(phoneNumber);
            NameValidator.validate(firstName);
            NameValidator.validate(lastName);
        } catch (Exception e) {
            Alert validationAlert = new Alert(Alert.AlertType.WARNING);
            validationAlert.setTitle("Validation Error");
            validationAlert.setHeaderText("Invalid input");
            validationAlert.setContentText(e.getMessage());
            validationAlert.showAndWait();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Changes");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to save changes to the patron: " + username + "?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

        confirmationAlert.showAndWait().ifPresent(type -> {
            if (type == yesButton) {
                try {
                    if (!username.equals(originalUsername)) {
                        viewModel.updateUsername(username, userID);
                    }
                    if (!firstName.equals(originalFirstName)) {
                        viewModel.updateFirstName(firstName, userID);
                    }
                    if (!lastName.equals(originalLastName)) {
                        viewModel.updateLastName(lastName, userID);
                    }
                    if (!email.equals(originalEmail)) {
                        viewModel.updateEmail(email, userID);
                    }
                    if (!phoneNumber.equals(originalPhoneNumber)) {
                        viewModel.updatePhoneNumber(phoneNumber, userID);
                    }

                    viewModel.loadPatrons();
                    UserTableView.refresh();
                    reset();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Save changes");
                    alert.setHeaderText("Changes to the account have been saved");
                    alert.show();
                } catch (RemoteException | SQLException e) {
                    Alert alert3 = new Alert(Alert.AlertType.WARNING);
                    alert3.setTitle("Oops! Something went wrong...");
                    alert3.setHeaderText(e.getMessage());
                    alert3.show();
                }
            }
        });
    }



    @FXML
    private void saveButtonClicked() throws RemoteException {
        try {
            int fees = Integer.parseInt(feesTextField.getText());
            int userID = Integer.parseInt(userIDTextField.getText());

            if (fees < 0) {
                throw new IllegalArgumentException("Fee must be higher or equal to zero!");
            }

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Changes");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to save changes to the fees for user ID: " + userID + "?");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

            confirmationAlert.showAndWait().ifPresent(type -> {
                if (type == yesButton) {
                    try {
                        viewModel.updateFees(userID, fees);

                        viewModel.loadPatrons();
                        UserTableView.refresh();
                        reset();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Save changes");
                        alert.setHeaderText("Changes to the fees and borrowing time have been saved");
                        alert.showAndWait();
                    } catch (RemoteException | SQLException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.WARNING);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("An error occurred while saving changes.");
                        errorAlert.setContentText(e.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Fee must be a number.");
            alert.setContentText("Please enter a valid fee.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Fees");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Please enter a valid fee.");
            alert.showAndWait();
        }
    }


    @FXML
    private void deleteButtonClicked() throws RemoteException {
        Patron selectedPatron = UserTableView.getSelectionModel().getSelectedItem();
        if (selectedPatron != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete the patron: " + selectedPatron.getFirstName() + " " + selectedPatron.getLastName() + "?");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

            confirmationAlert.showAndWait().ifPresent(type -> {
                if (type == yesButton) {
                    try {
                        viewModel.deletePatron(selectedPatron.getUserID());
                        viewModel.loadPatrons();
                        reset();
                    } catch (SQLException | RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Patron Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a patron to delete.");
            alert.showAndWait();
        }
    }


    @FXML
    private void saveUserButtonClicked() {
        //TODO: Implement working save user data after editing
    }

    public void reset() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        usernameTextField.clear();
        emailTextField.clear();
        phoneNumberTextField.clear();
        feesTextField.clear();
        userIDTextField.clear();
        UserTableView.getSelectionModel().clearSelection();
    }

    public Region getRoot() {
        return root;
    }

    @Override
    public void propertyChange(RemotePropertyChangeEvent remotePropertyChangeEvent) throws RemoteException {
        // Implement this method if needed for remote property change events
    }
}
