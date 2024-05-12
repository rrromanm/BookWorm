package sep.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Book;
import sep.model.State;
import sep.viewmodel.AdminManageBooksViewModel;
import sep.viewmodel.AdminManageDonatedBooksViewModel;

public class AdminManageBooksViewController
{
   //BUTTONS
    @FXML
    private Button search;
    @FXML
    private Button back;
    @FXML
    private Button clear;
    @FXML
    private Button save;
    @FXML
    private Button add;
    @FXML
    private Button remove;

    //TABLE
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book,String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, Integer> isbnColumn;
    @FXML private TableColumn<Book, Integer> pageCountColumn;
    @FXML private TableColumn<Book, Integer> bookIdColumn;
    @FXML private TableColumn<Book, String> genreColumn;

    //TEXT FIELDS
    @FXML private TextField searchField;
    @FXML private TextField idField;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;
    @FXML private TextField yearField;
    @FXML private TextField pagesField;
    @FXML private TextField genreField;

    private Region root;
    private ViewHandler viewHandler;
    private AdminManageBooksViewModel viewModel;
    private ReadOnlyObjectProperty<Book> selectedBook;
    public void init(ViewHandler viewHandler, AdminManageBooksViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeTableView();
//        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty(); //TODO view wasnt working with this
        // populate the tableView should also be here
    }
    public void initializeTableView(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }
    @FXML
    private void backButtonClicked()
    {
        viewHandler.openView("adminMainView");
    }
    @FXML
    private void onSave()
    {
        //TODO logic to save detail for the books
    }
    @FXML
    private void onAdd()
    {
        //TODO logic to add detail for the books
    }

    @FXML
    private void onRemove()
    {
        //TODO logic to remove a book
    }
    @FXML
    private void onClear()
    {
        searchField.clear();
        idField.clear();
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        yearField.clear();
        pagesField.clear();
        genreField.clear();
    }

    public void reset()
    {

    }

    public Region getRoot()
    {
        return root;
    }
}
