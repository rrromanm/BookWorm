package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sep.viewmodel.AdminLoginViewModel;
import javafx.scene.layout.Region;

public class AdminLoginViewController {
    private ViewHandler viewHandler;
    private AdminLoginViewModel viewModel;
    private Region root;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Button backButton;
    @FXML private Button login;

    public void init(ViewHandler viewHandler, AdminLoginViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        this.viewModel.bindUsername(usernameTextField.textProperty());
        this.viewModel.bindPassword(passwordTextField.textProperty());
    }

    @FXML
    private void backButtonClicked()
    {
        viewHandler.openView("login");
    }

    @FXML
    private void loginButtonClicked(){
        if (this.viewModel.login()){
            viewHandler.openView(ViewFactory.ADMINMAINVIEW);
        }
        System.out.println("Login unsuccessful");
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
