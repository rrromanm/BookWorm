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
import sep.model.*;
import sep.model.validators.BookValidator;
import sep.viewmodel.AdminManageBooksViewModel;
import sep.viewmodel.AdminManageDonatedBooksViewModel;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Controller class for the libarian interface to manage books.
 * This class handles user interactions and updates the view based on changes in the underlying data.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */

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


  /**
   * Initializes the controller with necessary objects and sets up event listeners.
   *
   * @param viewHandler The ViewHandler instance for navigating between views.
   * @param viewModel   The AdminManageBooksViewModel instance for handling book-related operations.
   * @param root        The root region of the view.
   * @throws RemoteException If a remote communication error occurs.
   * @throws SQLException    If a SQL-related error occurs.
   */
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
            if("ReturnBook".equals(evt.getPropertyName())) {
                bookTableView.refresh();
                try {
                    viewModel.loadBooks();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if("BorrowBook".equals(evt.getPropertyName())){
                bookTableView.refresh();
                try {
                    viewModel.loadBooks();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

            if (evt.getPropertyName().equals("Wishlist"))
            {
                bookTableView.refresh();
                try {
                    viewModel.loadBooks();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if (evt.getPropertyName().equals("BookDonate"))
            {
                bookTableView.refresh();
                try {
                    viewModel.loadBooks();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if (evt.getPropertyName().equals("DonatedBookApproved"))
            {
                bookTableView.refresh();
                try {
                    viewModel.loadBooks();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if(evt.getPropertyName().equals("createBook")){
                bookTableView.refresh();
                try {
                    viewModel.loadBooks();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

  /**
   * Initializes the table view by setting up the cell value factories for each column.
   * This method binds the cell value factories to the corresponding properties of the Book object.
   */
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

  /**
   * Handles the event when an item is selected in the table view.
   * This method calls the initializeTextFields
   * method to populate the text fields
   * with the details of the selected book.
   */
    @FXML
    public void onSelect(){
        initializeTextFields();
    }

  /**
   * Initializes the genre combo box by adding all available genres retrieved from the database.
   * The combo box is populated with genres, with the default selection set to "All".
   *
   * @throws SQLException if an SQL exception occurs while reading genres from the database.
   */
    public void initializeGenreComboBox() throws SQLException
    {
        genreField.getItems().add("All");
        genreField.getItems().addAll(BookDatabaseImplementation.getInstance()
                .readGenres());
        genreField.getSelectionModel().selectFirst();
    }
  /**
   * Initializes the text fields with the data of the currently selected book.
   * Retrieves the title, author, year, ISBN, page count, genre, book ID, and publisher
   * of the selected book and sets the corresponding values in the text fields.
   *
   * Note: This method assumes that a book is selected in the table view.
   *
   * If no book is selected, this method will throw a NullPointerException.
   */
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

  /**
   * Handles the action when the back button is clicked.
   *
   * This method invokes the `openView` method of the `viewHandler` object
   * to navigate to the admin main view.
   *
   * @throws RemoteException If a remote communication error occurs during the navigation process.
   */
    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("adminMainView");
    }

  /**
   * Handles the action when the save button is clicked.
   *
   * This method first checks if all required fields are filled out.
   * If any field is empty, it displays a warning alert prompting the user to fill out all fields.
   *
   * If all fields are filled out, it validates the book details using the `BookValidator` class.
   * If any detail is invalid, it displays a warning alert with the specific error message.
   *
   * If all details are valid, it invokes the `updateBook` method of the `viewModel` object
   * to update the book details based on the entered information.
   *
   * @throws RuntimeException If an unexpected error occurs during the update process.
   */
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
                    reset();
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
    }

  /**
   * Handles the action when the add button is clicked to add a new book.
   *
   * Displays an information alert notifying the user about auto-generated book ID.
   *
   * Checks if all required fields are filled out. If any field is empty,
   * it displays a warning alert prompting the user to fill out all fields.
   *
   * If all fields are filled out, it validates the book details using the `BookValidator` class.
   * If any detail is invalid, it displays a warning alert with the specific error message.
   *
   * If all details are valid, it invokes the `createBook` method of the `viewModel` object
   * to create a new book based on the entered information.
   *
   * @throws RuntimeException If an unexpected error occurs during the book creation process.
   */
    @FXML
    private void onAdd() {
        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("WAIT A MINUTE!");
        alert3.setHeaderText("You are about to add a new book!");
        alert3.setContentText("Please mind that book ID is AUTO-GENERATED! That's why ID field is empty!");
        alert3.showAndWait();
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
                    reset();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

  /**
   * Handles the action when the search button is clicked to filter books based on the search criteria.
   *
   * Retrieves the search criteria from the search field.
   * Invokes the `showFiltered` method of the `viewModel` object to filter books based on the search criteria.
   *
   * @throws RuntimeException If an unexpected error occurs during the filtering process.
   */
    @FXML
    private void onSearch(){
        try {
            viewModel.showFiltered("All","All",searchField.getText());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

  /**
   * Handles the action when the remove button is clicked to delete a book from the system.
   *
   * Checks if the selected book is currently borrowed.
   * If the book is borrowed, displays a warning alert indicating that the book cannot be removed.
   * If the book is not borrowed, displays a confirmation alert to confirm the deletion.
   * Deletes the book from the system if confirmed, then refreshes the book table and resets input fields.
   *
   * @throws RuntimeException If an unexpected error occurs during the deletion process.
   */
    @FXML
    private void onRemove() throws SQLException {
        if (selectedBook.get().getState() instanceof Borrowed) {
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
                    reset();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

  /**
   * Handles the action when the clear button is clicked to reset input fields and clear the selection in the book table.
   */
    @FXML
    private void onClear()
    {
        reset();
    }

  /**
   * Resets the input fields and clears the selection in the book table.
   */
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
        bookTableView.getSelectionModel().clearSelection();
    }

  /**
   * Retrieves the root region of the view.
   *
   * @return The root region of the view.
   */
    public Region getRoot()
    {
        return root;
    }

  /**
   * Handles changes in remote properties.
   *
   * @param evt The event representing the change in the remote property.
   * @throws RemoteException If there is a problem with the remote communication.
   */
    @Override
    public void propertyChange(RemotePropertyChangeEvent evt) throws RemoteException {
      // Implementation for handling changes in remote properties
    }
}
