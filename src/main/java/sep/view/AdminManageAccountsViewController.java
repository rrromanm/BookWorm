package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminManageAccountsViewModel;
import sep.viewmodel.DonateViewModel;

public class AdminManageAccountsViewController {
    private ViewHandler viewHandler;
    private AdminManageAccountsViewModel viewModel;
    private Region root;
    @FXML
    private Button backButton;
    @FXML
    private Button SaveButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button SaveUserButton;

    public void init(ViewHandler viewHandler, AdminManageAccountsViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }
    @FXML
    private void backButtonClicked(){
        viewHandler.openView("login"); //TODO: Change it to main view
    }
    @FXML private void saveButtonClicked(){
        //TODO: Implement working save
    }
    @FXML private void deleteButtonClicked(){
        //TODO: Implement working delete
    }
    @FXML private void saveUserButtonClicked(){
        //TODO: Implement working save user data after editing
    }
    public void reset(){
        // TODO: reset view
    }
    public Region getRoot(){
        return root;
    }
}
