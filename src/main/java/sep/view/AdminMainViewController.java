package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminLoginViewModel;
import sep.viewmodel.AdminMainViewModel;

import java.rmi.RemoteException;

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
    private void logoutClicked() throws RemoteException
    { //TODO i guess we need somehow to disconnect the admin from the server?
        viewHandler.openView("login");
    }

    @FXML
    private void historyClicked() throws RemoteException
    {
        viewHandler.openView("adminServerLog");
    }
    @FXML
    private void booksClicked() throws RemoteException
    {
        viewHandler.openView("adminManageBooksView");
    }
    @FXML
    private void accountsClicked() throws RemoteException
    {
        viewHandler.openView("adminManageAccounts");
    }
    @FXML
    private void eventsClicked() throws RemoteException
    {
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
