package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sep.viewmodel.LoginViewModel;
import javafx.scene.layout.Region;


public class LoginViewController {
    private ViewHandler viewHandler;
    private LoginViewModel viewModel;
    private Region root;

    @FXML private Button loginButton;
    @FXML private Button loginAsAdminButton;
    @FXML private Button createAccountButton;

    public void init(ViewHandler viewHandler, LoginViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    @FXML private void loginButtonClicked()
    {
        viewHandler.openView("login");
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

    }

    public Region getRoot()
    {
        return root;
    }
}
