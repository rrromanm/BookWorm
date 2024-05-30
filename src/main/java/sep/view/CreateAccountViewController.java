package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sep.viewmodel.CreateAccountViewModel;
import javafx.scene.layout.Region;

import java.rmi.RemoteException;

/**
 * Controller class for the interface to create a new account.
 * This class handles user interactions and updates the view based on changes in the underlying data.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class CreateAccountViewController {
    private ViewHandler viewHandler;
    private CreateAccountViewModel viewModel;
    private Region root;

    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextField repeatPasswordTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private Button backButton;
    @FXML private Button createAccountButton;
    @FXML private Label error;

    /**
     * Initializes the view with the provided view handler, view model, and root region.
     *
     * @param viewHandler The view handler for managing views.
     * @param viewModel   The view model for creating accounts.
     * @param root        The root region of the view.
     */
    public void init(ViewHandler viewHandler, CreateAccountViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        this.viewModel.bindUsername(usernameTextField.textProperty());
        this.viewModel.bindFirstName(firstNameTextField.textProperty());
        this.viewModel.bindLastName(lastNameTextField.textProperty());
        this.viewModel.bindEmail(emailTextField.textProperty());
        this.viewModel.bindPhoneNumber(phoneNumberTextField.textProperty());
        this.viewModel.bindRepeatPassword(repeatPasswordTextField.textProperty());
        this.viewModel.bindPassword(passwordTextField.textProperty());
    }

    /**
     * Handles the event when the back button is clicked.
     * This method is triggered when the back button in the user interface is clicked.
     * It navigates back to the login view.
     *
     * @throws RemoteException if a remote communication-related exception occurs
     */
    @FXML
    private void backButtonClicked() throws RemoteException {
        viewHandler.openView("login");
    }

    /**
     * Handles the event when the create account button is clicked.
     * This method is triggered when the create account button in the user interface is clicked.
     * It calls the viewModel to create a new patron account and handles any exceptions that may occur.
     *
     * @throws RuntimeException if an unexpected exception occurs
     */
    @FXML
    protected void createAccountButtonClicked() throws RuntimeException {
        try {
            this.viewModel.createPatron();
            viewHandler.openView("login");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account created");
            alert.setHeaderText("Accounts got created, now you can login to BookWorm");
            alert.show();
            reset();
        } catch (RemoteException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Oops, there is an issue!");
            alert.setHeaderText("Warning!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Resets the input fields and the view model.
     * This method clears the input fields and resets the view model,
     * allowing the user to start with a fresh form.
     */
    public void reset() {
        viewModel.reset();
    }

    /**
     * Gets the root node of the view.
     * This method returns the root node of the view, which is used to display the entire view.
     *
     * @return the root node of the view
     */
    public Region getRoot() {
        return root;
    }
}
