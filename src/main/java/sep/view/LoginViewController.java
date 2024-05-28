package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sep.viewmodel.LoginViewModel;
import javafx.scene.layout.Region;

import java.rmi.RemoteException;

/**
 * This class is the controller for the login view. It handles user interactions for the login process,
 * including login as a regular user, login as an admin, creating a new account, and exiting the application.
 * It also manages the display of password visibility and binds the view elements to the view model.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class LoginViewController {
    private ViewHandler viewHandler;
    private LoginViewModel viewModel;
    private Region root;

    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Tooltip passwordTip;

    @FXML private Button loginButton;
    @FXML private Button loginAsAdminButton;
    @FXML private Button createAccountButton;
    @FXML private Button exitButton;

    @FXML private CheckBox showPasswordCheckBox;
    @FXML private TextField passwordVisibleField;

    @FXML private Label error;

    /**
     * Initializes the view with the provided view handler, view model, and root region.
     *
     * @param viewHandler The view handler for managing views.
     * @param viewModel   The view model for binding and managing data.
     * @param root        The root region of the view.
     */
    public void init(ViewHandler viewHandler, LoginViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        this.viewModel.bindUsername(usernameTextField.textProperty());
        this.viewModel.bindPassword(passwordField.textProperty());
        this.viewModel.bindError(error.textProperty());

        passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());

        passwordVisibleField.setVisible(false);
        passwordVisibleField.managedProperty().bind(passwordVisibleField.visibleProperty());
    }

    /**
     * Handles the login button click event.
     * Attempts to log in with the provided credentials and navigates to the main user view if successful.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    @FXML private void loginButtonClicked() throws RemoteException
    {
        if(this.viewModel.login() != null){
            viewHandler.openView(ViewFactory.USERMAIN);
            System.out.println("Login successful");
        } else {
            System.out.println("Login unsuccessful");
        }
    }

    /**
     * Toggles the visibility of the password.
     * Shows the password in a text field if the checkbox is selected, otherwise shows it in a password field.
     */
    @FXML
    private void onShowPassword() {
        if (showPasswordCheckBox.isSelected()) {
            passwordVisibleField.setVisible(true);
            passwordField.setVisible(false);
        } else {
            passwordVisibleField.setVisible(false);
            passwordField.setVisible(true);
        }
    }

    /**
     * Handles the create account button click event.
     * Navigates to the create account view.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    @FXML private void createButtonClicked() throws RemoteException
    {
        viewHandler.openView(ViewFactory.CREATEACCOUNT);
    }

    /**
     * Handles the login as admin button click event.
     * Navigates to the admin login view.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    @FXML private void loginAsAdminButtonClicked() throws RemoteException
    {
        viewHandler.openView("adminLogin");
    }

    /**
     * Handles the exit button click event.
     * Closes the application.
     */
    @FXML private void onExit()
    {
        viewHandler.closeView();
    }

    /**
     * Resets the view model.
     */
    public void reset()
    {
        this.viewModel.reset();
    }

    /**
     * Returns the root region of the view.
     *
     * @return the root region.
     */
    public Region getRoot()
    {
        return root;
    }
}
