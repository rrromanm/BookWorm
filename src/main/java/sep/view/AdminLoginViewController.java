package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminLoginViewModel;

import java.rmi.RemoteException;

/**
 * The AdminLoginViewController class controls the interaction between the admin login view and its corresponding view model.
 * It handles user input, such as entering the username and password, and provides actions for buttons like login and back.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminLoginViewController {
    private ViewHandler viewHandler;
    private AdminLoginViewModel viewModel;
    private Region root;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Button backButton;
    @FXML private Button login;
    @FXML private Label error;

    /**
     * Initializes the view controller with the given view handler, view model, and root region.
     *
     * @param viewHandler The view handler responsible for managing views
     * @param viewModel The view model associated with the view
     * @param root The root region of the view
     */
    public void init(ViewHandler viewHandler, AdminLoginViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        this.viewModel.bindUsername(usernameTextField.textProperty());
        this.viewModel.bindPassword(passwordTextField.textProperty());
        this.viewModel.bindError(error.textProperty());
    }

    /**
     * Handles the action when the back button is clicked.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("login");
    }

    /**
     * Handles the action when the login button is clicked.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    @FXML
    private void loginButtonClicked() throws RemoteException
    {
        if (this.viewModel.login()){
            viewHandler.openView(ViewFactory.ADMINMAINVIEW);
        }
        System.out.println("Login unsuccessful");
    }

    /**
     * Resets the view to its initial state.
     */
    public void reset()
    {
        this.viewModel.reset();
    }

    /**
     * Retrieves the root region of the view.
     *
     * @return The root region of the view
     */
    public Region getRoot()
    {
        return root;
    }
}
