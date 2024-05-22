package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sep.viewmodel.LoginViewModel;
import javafx.scene.layout.Region;

import java.rmi.RemoteException;

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

    @FXML private void loginButtonClicked() throws RemoteException
    {
        if(this.viewModel.login()!=null){
            viewHandler.openView(ViewFactory.USERMAIN);
            System.out.println("Login successful");
        }
        else {
            System.out.println("Login unsuccessful");
        }
    }

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

    @FXML private void createButtonClicked() throws RemoteException
    {
        viewHandler.openView(ViewFactory.CREATEACCOUNT);
    }

    @FXML private void loginAsAdminButtonClicked() throws RemoteException
    {
        viewHandler.openView("adminLogin");
    }

    @FXML private void onExit()
    {
        viewHandler.closeView();
    }

    public void reset()
    {
        this.viewModel.reset();
    }

    public Region getRoot()
    {
        return root;
    }
}
