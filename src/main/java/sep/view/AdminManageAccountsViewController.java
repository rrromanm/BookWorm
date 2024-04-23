package sep.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Book;
import sep.model.User;
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
    @FXML
    private TableView<User> UserTableView;
    @FXML private TableColumn<User, Integer> IDcolumn;
    @FXML private TableColumn<User, String> FirstNameColumn;
    @FXML private TableColumn<User, String> LastNameColumn;
    @FXML private TableColumn<User, String> UsernameColumn;
    @FXML private TableColumn<User, String> EmailColumn;
    @FXML private TableColumn<User, Integer> phoneNumberColumn;
    private ReadOnlyObjectProperty<User> selectedUser;

    public void init(ViewHandler viewHandler, AdminManageAccountsViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeTableView();
        this.selectedUser = UserTableView.getSelectionModel().selectedItemProperty();
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
