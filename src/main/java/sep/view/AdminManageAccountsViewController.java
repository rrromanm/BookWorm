package sep.view;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Patron;
import sep.viewmodel.AdminManageAccountsViewModel;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class AdminManageAccountsViewController implements RemotePropertyChangeListener {
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
    @FXML
    private TableView<Patron> UserTableView;
    @FXML private TableColumn<Patron, Integer> IDcolumn;
    @FXML private TableColumn<Patron, String> FirstNameColumn;
    @FXML private TableColumn<Patron, String> LastNameColumn;
    @FXML private TableColumn<Patron, String> UsernameColumn;
    @FXML private TableColumn<Patron, String> EmailColumn;
    @FXML private TableColumn<Patron, Integer> phoneNumberColumn;
    private ReadOnlyObjectProperty<Patron> selectedUser;

    public void init(ViewHandler viewHandler, AdminManageAccountsViewModel viewModel, Region root) throws RemoteException {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeTableView();
        this.selectedUser = UserTableView.getSelectionModel().selectedItemProperty();
        this.viewModel.bindList(UserTableView.itemsProperty());
        loadTableData();
    }
    private void loadTableData() {
        try {
            viewModel.loadPatrons();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initializeTableView() {
        IDcolumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        UsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }
    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("adminMainView");
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

    @Override
    public void propertyChange(RemotePropertyChangeEvent remotePropertyChangeEvent) throws RemoteException {

    }
}
