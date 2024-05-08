package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sep.viewmodel.LoginViewModel;
import javafx.scene.layout.Region;


public class LoginViewController {
    private ViewHandler viewHandler;
    private LoginViewModel viewModel;
    private Region root;

    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;

    @FXML private Button loginButton;
    @FXML private Button loginAsAdminButton;
    @FXML private Button createAccountButton;

    public void init(ViewHandler viewHandler, LoginViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        this.viewModel.bindUsername(usernameTextField.textProperty());
        this.viewModel.bindPassword(passwordTextField.textProperty());
    }

    @FXML private void loginButtonClicked()
    {
        if(this.viewModel.login()){
            viewHandler.openView(ViewFactory.USERMAIN);
        }
        System.out.println("Login unsuccessful");
    }

    @FXML private void createButtonClicked()
    {
        viewHandler.openView("createAccount");
    }

    @FXML private void loginAsAdminButtonClicked()
    {
        viewHandler.openView("adminLogin");
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
