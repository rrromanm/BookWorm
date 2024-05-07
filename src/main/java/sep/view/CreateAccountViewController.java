package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sep.viewmodel.CreateAccountViewModel;
import javafx.scene.layout.Region;

import java.rmi.RemoteException;

public class CreateAccountViewController {
    private ViewHandler viewHandler;
    private CreateAccountViewModel viewModel;
    private Region root;

    @FXML
    private Button backButton;
    @FXML
    private Button createAccountButton;

    public void init(ViewHandler viewHandler, CreateAccountViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    @FXML
    private void backButtonClicked()
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
