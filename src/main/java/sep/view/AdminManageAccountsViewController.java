package sep.view;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
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
    @FXML private TextField borrowingTimeTextField;

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
    private void savePatronChangesButtonClicked() throws RemoteException {
        int userID = Integer.parseInt(userIDTextField.getText());
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();

        try{
            UsernameValidator.validate(username);
            EmailValidator.validate(email);
            PhoneValidator.validate(phoneNumber);
            NameValidator.validate(firstName);
            NameValidator.validate(lastName);

            if(!username.equals(originalUsername)){
                viewModel.updateUsername(username,originalUsername);
            }

            if(!firstName.equals(originalFirstName)){
                viewModel.updateFirstName(firstName,originalFirstName);
            }
            if(!lastName.equals(originalLastName)){
                viewModel.updateLastName(lastName,originalLastName);
            }
            if(!email.equals(originalEmail)){
                viewModel.updateEmail(email,originalEmail);
            }
            if(!phoneNumber.equals(originalPhoneNumber)){
                viewModel.updatePhoneNumber(phoneNumber,originalPhoneNumber);
            }

            viewModel.loadPatrons();
            UserTableView.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save changes");
            alert.setHeaderText("Changes to the account has been saved");
            alert.show();

        }catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @FXML private void saveButtonClicked() throws RemoteException {
        int fees = Integer.parseInt(feesTextField.getText());

        try {

            viewModel.updateFees(originalFee, fees);
            //viewModel.updateBorrowingTime(userID, borrowingTime);

            viewModel.loadPatrons();
            UserTableView.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save changes");
            alert.setHeaderText("Changes to the fees and borrowing time have been saved");
            alert.show();

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }


    @FXML
    private void deleteButtonClicked() {
        //TODO: Implement working delete
    }

    @FXML
    private void saveUserButtonClicked() {
        //TODO: Implement working save user data after editing
    }

    public void reset() {
        // TODO: reset view
    }

    public Region getRoot() {
        return root;
    }

    @Override
    public void propertyChange(RemotePropertyChangeEvent remotePropertyChangeEvent) throws RemoteException {
        // Implement this method if needed for remote property change events
    }
}
