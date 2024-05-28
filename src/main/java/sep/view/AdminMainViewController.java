package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminMainViewModel;

import java.rmi.RemoteException;

/**
 * The AdminMainViewController class controls the interaction between the admin main view and its corresponding view model.
 * It handles user actions, such as clicking buttons to navigate to different views, and provides methods to initialize and reset the view.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminMainViewController {
    private ViewHandler viewHandler;
    private AdminMainViewModel viewModel;
    private Region root;

    @FXML public Button logoutButton;
    @FXML public Button historyViewButton;
    @FXML public Button eventsViewButton;
    @FXML public Button accountsViewButton;
    @FXML public Button booksViewButton;
    @FXML public Button manageDonatedBooksButton;

    /**
     * Initializes the view controller with the given view handler, view model, and root region.
     *
     * @param viewHandler The view handler responsible for managing views
     * @param viewModel The view model associated with the view
     * @param root The root region of the view
     */
    public void init(ViewHandler viewHandler, AdminMainViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    /**
     * Handles the action when the logout button is clicked.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    @FXML
    private void logoutClicked() throws RemoteException
    {
        viewHandler.openView("login");
    }

    /**
     * Handles the action when the history view button is clicked.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    @FXML
    private void historyClicked() throws RemoteException
    {
        viewHandler.openView("adminServerLog");
    }

    /**
     * Handles the action when the books view button is clicked.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    @FXML
    private void booksClicked() throws RemoteException
    {
        viewHandler.openView("adminManageBooksView");
    }

    /**
     * Handles the action when the accounts view button is clicked.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    @FXML
    private void accountsClicked() throws RemoteException
    {
        viewHandler.openView("adminManageAccounts");
    }

    /**
     * Handles the action when the events view button is clicked.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    @FXML
    private void eventsClicked() throws RemoteException
    {
        viewHandler.openView("adminManageEvents");
    }

    /**
     * Handles the action when the manage donated books button is clicked.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    @FXML
    private void manageDonatedBooksClicked() throws RemoteException
    {
        viewHandler.openView("adminManageDonatedBooks");
    }

    /**
     * Resets the view to its initial state.
     */
    public void reset()
    {

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
