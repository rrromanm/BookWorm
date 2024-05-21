package sep.view;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.jdbc.BookDatabaseImplementation;
import sep.model.Book;
import sep.model.Patron;
import sep.model.State;
import sep.model.validators.BookValidator;
import sep.viewmodel.AdminManageBooksViewModel;
import sep.viewmodel.AdminManageDonatedBooksViewModel;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Optional;

public class AdminManageBooksViewController implements RemotePropertyChangeListener
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
    @FXML private TableColumn<Book, String> publisherColumn;

    //TEXT FIELDS
    @FXML private TextField searchField;
    @FXML private TextField idField;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;
    @FXML private TextField yearField;
    @FXML private TextField pagesField;
    @FXML private ComboBox<String> genreField;
    @FXML private TextField publisherField;

    private Region root;
    private ViewHandler viewHandler;
    private AdminManageBooksViewModel viewModel;
    private ReadOnlyObjectProperty<Book> selectedBook;

    public void init(ViewHandler viewHandler, AdminManageBooksViewModel viewModel, Region root)
            throws RemoteException, SQLException {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeTableView();
        initializeGenreComboBox();
        this.viewModel.bindList(bookTableView.itemsProperty());
        this.selectedBook = bookTableView.getSelectionModel().selectedItemProperty();
        viewModel.loadBooks();

        viewModel.addPropertyChangeListener(evt -> {
            if ("bookList".equals(evt.getPropertyName())) {
                bookTableView.refresh();
                initializeTextFields();
            }
            if ("removeBook".equals(evt.getPropertyName())) {
                bookTableView.refresh();
                try {
                    viewModel.loadBooks();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if("addBook".equals(evt.getPropertyName())) {
                bookTableView.refresh();
                try {
                    viewModel.loadBooks();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if("updateBook".equals(evt.getPropertyName())) {
                bookTableView.refresh();
                try {
                    viewModel.loadBooks();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void initializeTableView(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    }

    @FXML
    public void onSelect(){
        initializeTextFields();
    }

    public void initializeGenreComboBox() throws SQLException
    {
        genreField.getItems().add("All");
        genreField.getItems().addAll(BookDatabaseImplementation.getInstance()
                .readGenres());
        genreField.getSelectionModel().selectFirst();
    }
    public void initializeTextFields() {
                titleField.setText(selectedBook.get().getTitle());
                authorField.setText(selectedBook.get().getAuthor());
                yearField.setText(String.valueOf(selectedBook.get().getYear()));
                isbnField.setText(String.valueOf(selectedBook.get().getIsbn()));
                pagesField.setText(String.valueOf(selectedBook.get().getPageCount()));
                genreField.getSelectionModel().select(selectedBook.get().getGenre());
                idField.setText(String.valueOf(selectedBook.get().getBookId()));
                publisherField.setText(selectedBook.get().getPublisher());
    }



    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("adminMainView");
    }
    @FXML
    private void onSave()
    {
        if (titleField.getText().isEmpty() ||
                authorField.getText().isEmpty() ||
                yearField.getText().isEmpty() ||
                publisherField.getText().isEmpty() ||
                isbnField.getText().isEmpty() ||
                pagesField.getText().isEmpty() ||
                genreField.getSelectionModel().getSelectedItem().equals("All")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText("Incomplete Book Information");
            alert.setContentText("Please fill out all fields to edit the book.");
            alert.showAndWait();
        }else{
            if(!BookValidator.validateBookDetails(isbnField.getText(),
                    yearField.getText(), pagesField.getText())){
                StringBuilder errorMessage = new StringBuilder("Invalid Book Information:\n");

                if(!BookValidator.validateISBN(isbnField.getText()))
                    errorMessage.append("- Please fill out ISBN field correctly to edit the book. ISBN field must be 13 numbers!\n");
                if(!BookValidator.validateYear(yearField.getText()))
                    errorMessage.append("- Please fill out year field correctly to edit the book. Year field must be a number and possible year!\n");
                if(!BookValidator.validatePageCount(pagesField.getText()))
                    errorMessage.append("- Please fill out page count field correctly to edit the book. Page count field must be a number and higher than 0!\n");

                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Incorrect Information");
                alert2.setHeaderText("Invalid Book Information");
                alert2.setContentText(errorMessage.toString());
                alert2.showAndWait();
            }else{
                try{
                    viewModel.updateBook(Integer.valueOf(idField.getText()),titleField.getText(),authorField.getText(),yearField.getText(),
                            publisherField.getText(),isbnField.getText(),pagesField.getText(),genreField.getSelectionModel().getSelectedItem());
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
    private void onAdd() {
        if (titleField.getText().isEmpty() ||
                authorField.getText().isEmpty() ||
                yearField.getText().isEmpty() ||
                publisherField.getText().isEmpty() ||
                isbnField.getText().isEmpty() ||
                pagesField.getText().isEmpty() ||
                genreField.getSelectionModel().getSelectedItem().equals("All")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText("Incomplete Book Information");
            alert.setContentText("Please fill out all fields before adding the book.");
            alert.showAndWait();


        } else {
            if(!BookValidator.validateBookDetails(isbnField.getText(),
                    yearField.getText(), pagesField.getText())){
                StringBuilder errorMessage = new StringBuilder("Invalid Book Information:\n");

                if(!BookValidator.validateISBN(isbnField.getText()))
                    errorMessage.append("- Please fill out ISBN field correctly before adding the book. ISBN field must be 13 numbers!\n");
                if(!BookValidator.validateYear(yearField.getText()))
                    errorMessage.append("- Please fill out year field correctly before adding the book. Year field must be a number and possible year!\n");
                if(!BookValidator.validatePageCount(pagesField.getText()))
                    errorMessage.append("- Please fill out page count field correctly before adding the book. Page count field must be a number and higher than 0!\n");

                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Incorrect Information");
                alert2.setHeaderText("Invalid Book Information");
                alert2.setContentText(errorMessage.toString());
                alert2.showAndWait();
            }
            else {
                try {
                    viewModel.createBook(
                            titleField.getText(),
                            authorField.getText(),
                            yearField.getText(),
                            publisherField.getText(),
                            isbnField.getText(),
                            pagesField.getText(),
                            genreField.getSelectionModel().getSelectedItem()
                    );
                    viewModel.loadBooks();
                    bookTableView.refresh();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    private void onSearch(){
        try {
            viewModel.showFiltered("All","All",searchField.getText());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void onRemove() throws SQLException {
        if (selectedBook.get().getState().equals("Borrowed")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cannot Remove Book");
            alert.setHeaderText("Cannot Remove Book");
            alert.setContentText("This book is borrowed, thus not available to remove.");
            alert.showAndWait();
        } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Are you sure you want to delete this book?");
            confirmationAlert.setContentText("Title: " + selectedBook.get().getTitle() + "\nAuthor: " + selectedBook.get().getAuthor() + "?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    viewModel.deleteBook(selectedBook.get().getBookId(), selectedBook.get().getTitle(), selectedBook.get().getAuthor(),
                            String.valueOf(selectedBook.get().getYear()), selectedBook.get().getPublisher(),
                            String.valueOf(selectedBook.get().getIsbn()), String.valueOf(selectedBook.get().getPageCount()), selectedBook.get().getGenre());
                    viewModel.loadBooks();
                    bookTableView.refresh();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
    private void onClear()
    {
        reset();
    }

    public void reset()
    {
        searchField.clear();
        idField.clear();
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        yearField.clear();
        pagesField.clear();
        genreField.getSelectionModel().selectFirst();
        publisherField.clear();
    }

    public Region getRoot()
    {
        return root;
    }

    @Override
    public void propertyChange(RemotePropertyChangeEvent evt) throws RemoteException {

    }
}
