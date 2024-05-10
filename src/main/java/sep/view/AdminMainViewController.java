package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminLoginViewModel;
import sep.viewmodel.AdminMainViewModel;

public class AdminMainViewController {
    private ViewHandler viewHandler;
    private AdminMainViewModel viewModel;
    private Region root;

    @FXML public Button logoutButton;
    @FXML public Button historyViewButton;
    @FXML public Button eventsViewButton;
    @FXML public Button accountsViewButton;
    @FXML public Button booksViewButton;


    public void init(ViewHandler viewHandler, AdminMainViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    @FXML
    private void logoutClicked()
    { //TODO i guess we need somehow to disconnect the admin from the server?
        viewHandler.openView("login");
    }

    @FXML
    private void historyClicked(){
        viewHandler.openView("adminServerLog");
    }
    @FXML
    private void booksClicked(){
        viewHandler.openView("adminManageBooksView");
    }
    @FXML
    private void accountsClicked(){
        viewHandler.openView("adminManageAccounts");
    }
    @FXML
    private void eventsClicked(){
        viewHandler.openView("adminManageEvents");
    }

    public void reset()
    {

    }

    public Region getRoot()
    {
        return root;
    }
}
