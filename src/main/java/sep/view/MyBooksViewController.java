package sep.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Book;
import sep.model.State;
import sep.viewmodel.MyBooksViewModel;
import sep.viewmodel.ProfileViewModel;

public class MyBooksViewController {
    @FXML private Button returnButton;
    @FXML private Button extendButton;
    @FXML private Button backButton;
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book,String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, Integer> isbnColumn;
    @FXML private TableColumn<Book, Integer> pageCountColumn;
    @FXML private TableColumn<Book, Integer> bookIdColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    private Region root;
    private ViewHandler viewHandler;
    private MyBooksViewModel myBooksViewModel;
    private ReadOnlyObjectProperty<Book> selectedBook;
    public void init(ViewHandler viewHandler, MyBooksViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.myBooksViewModel = viewModel;
        this.root = root;
        initializeTableView();
        populateTableView();
        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty();
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
    }
    public void populateTableView(){
        // we need to get the books that this user borrowed. So if(user.equals(user) && state.equals(BORROWED))
    }
    @FXML public void onReturn(){
        //myBooksViewModel.returnBook(); // it should have this method
    }
    @FXML public void onExtend(){
        // extend the deadline for the book
    }
    @FXML public void onBack(){
        viewHandler.openView(ViewFactory.USERMAIN);
    }
    @FXML public void onSelect(){
        returnButton.setDisable(false);
        /*if(selectedBook.get())
            here we need to implement so the extendButton appears based on the remaining deadline
         */
    }

    public Region getRoot() {
        return root;
    }
    public void reset(){

    }
}
