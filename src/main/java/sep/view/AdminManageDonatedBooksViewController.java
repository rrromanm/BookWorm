package sep.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import sep.viewmodel.AdminManageDonatedBooksViewModel;
import sep.viewmodel.MainViewModel;

public class AdminManageDonatedBooksViewController
{
    @FXML
    private Button back;
    @FXML
    private Button approve;
    @FXML
    private Button reject;
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book,String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, Integer> isbnColumn;
    @FXML private TableColumn<Book, Integer> pageCountColumn;
    @FXML private TableColumn<Book, Integer> bookIdColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    @FXML private TableColumn<Book, State> stateColumn;
    private Region root;
    private ViewHandler viewHandler;
    private AdminManageDonatedBooksViewModel donatedBooksViewModel;
    private ReadOnlyObjectProperty<Book> selectedBook;

    public void init(ViewHandler viewHandler, AdminManageDonatedBooksViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.donatedBooksViewModel = viewModel;
        this.root = root;
        initializeTableView();
        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty();
        // populate the tableView should also be here
    }

    public void initializeTableView(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
    }
    @FXML
    private void backButtonClicked()
    {
        viewHandler.openView("adminMainView");
    }

    @FXML
    private void onApprove(){
        //TODO implement logic adding a donated book to all the books
    }
    @FXML
    private void onReject(){
        //TODO add logic to remove book from the list
    }
    public void reset()
    {

    }

    public Region getRoot()
    {
        return root;
    }
}
