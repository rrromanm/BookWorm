package sep.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import sep.viewmodel.AdminManageDonatedBooksViewModel;
import sep.viewmodel.MainViewModel;

import java.rmi.RemoteException;
import java.sql.SQLException;

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
    @FXML private TableColumn<Book, String> genreColumn;
    private Region root;
    private ViewHandler viewHandler;
    private AdminManageDonatedBooksViewModel donatedBooksViewModel;
    private ReadOnlyObjectProperty<Book> selectedBook;

    public void init(ViewHandler viewHandler, AdminManageDonatedBooksViewModel viewModel, Region root) throws RemoteException {
        this.viewHandler = viewHandler;
        this.donatedBooksViewModel = viewModel;
        this.root = root;
        initializeTableView();
        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty();
        this.donatedBooksViewModel.bindList(bookTableView.itemsProperty());
        viewModel.resetBookList();
    }

    public void initializeTableView(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }
    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("adminMainView");
    }

    @FXML
    private void onApprove() throws SQLException, RemoteException {
        donatedBooksViewModel.approveDonatedBook(selectedBook.get().getBookId(), selectedBook.get().getTitle(), selectedBook.get().getAuthor(),
                selectedBook.get().getIsbn(), selectedBook.get().getYear(), selectedBook.get().getPublisher(), selectedBook.get().getPageCount(),
                selectedBook.get().getGenre());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Book added to system!");
        alert.showAndWait();
    }

    @FXML
    private void onReject() throws SQLException, RemoteException {
        donatedBooksViewModel.rejectDonatedBook(selectedBook.get().getBookId());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Book rejected!");
        alert.showAndWait();
    }
    public void reset()
    {

    }

    public Region getRoot()
    {
        return root;
    }
}
