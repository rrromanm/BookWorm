package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sep.viewmodel.CreateAccountViewModel;
import javafx.scene.layout.Region;

import java.rmi.RemoteException;

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

    @FXML
    private Button backButton;
    @FXML
    private Button createAccountButton;
    @FXML private Label error;

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
        this.viewModel.bindError(error.textProperty());
    }

    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("login");
    }
   @FXML protected void createAccountButtonClicked() {

       try {
            this.viewModel.createPatron();
            viewHandler.openView("login");
           reset();
        } catch (RemoteException e) {
           throw new RuntimeException(e);
       }
    }



    public void reset()
    {
        viewModel.reset();
    }

    public Region getRoot()
    {
        return root;
    }
}
