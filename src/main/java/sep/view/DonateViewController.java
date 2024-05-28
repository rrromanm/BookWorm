package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import sep.jdbc.BookDatabaseImplementation;
import sep.model.UserSession;
import sep.viewmodel.DonateViewModel;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Controller class for the book donation view.
 * Handles user interactions and updates the view based on changes in the underlying data.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class DonateViewController {
    private ViewHandler viewHandler;
    private DonateViewModel viewModel;
    private Region root;

    @FXML private Button backButton;
    @FXML private Button submitButton;
    @FXML private TextField bookTitle;
    @FXML private TextField bookAuthor;
    @FXML private TextField bookISBN;
    @FXML private TextField bookYear;
    @FXML private TextField bookPublisher;
    @FXML private TextField bookPages;
    @FXML private ComboBox<String> genreComboBox;

    /**
     * Initializes the view with the provided view handler, view model, and root region.
     *
     * @param viewHandler The view handler for managing views.
     * @param viewModel   The view model for donating books.
     * @param root        The root region of the view.
     * @throws SQLException if a database access error occurs
     */
    public void init(ViewHandler viewHandler, DonateViewModel viewModel, Region root) throws SQLException {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeGenreComboBox();
    }

    /**
     * Handles the event when the back button is clicked.
     * This method is triggered when the back button in the user interface is clicked.
     * It navigates back to the user main view.
     *
     * @throws RemoteException if a remote communication-related exception occurs
     */
    @FXML
    public void backButtonClicked() throws RemoteException {
        viewHandler.openView(ViewFactory.USERMAIN);
    }

    /**
     * Handles the event when the submit button is clicked.
     * This method is triggered when the submit button in the user interface is clicked.
     * It collects input data and calls the view model to donate a book.
     * Handles any exceptions that may occur during the process.
     *
     * @throws IOException if an input/output error occurs
     */
    @FXML
    private void submitButtonClicked() throws IOException {
        try {
            String title = bookTitle.getText();
            String author = bookAuthor.getText();
            String isbn = bookISBN.getText();
            String yearStr = bookYear.getText();
            String publisher = bookPublisher.getText();
            String pagesStr = bookPages.getText();
            String genre = genreComboBox.getSelectionModel().getSelectedItem();

            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || yearStr.isEmpty() || publisher.isEmpty() || pagesStr.isEmpty() || genre == null) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "All fields must be filled out.");
                return;
            }

            long isbnLong;
            try {
                isbnLong = Long.parseLong(isbn);
                if (isbn.length() != 13) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "ISBN must be a 13-digit number.");
                return;
            }

            int year;
            try {
                year = Integer.parseInt(yearStr);
                if (year < 1000 || year > 2025) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Year must be between 1000 and 2025.");
                return;
            }

            int pages;
            try {
                pages = Integer.parseInt(pagesStr);
                if (pages <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Page count must be a positive number.");
                return;
            }

            viewModel.donateBook(title, author, isbnLong, year, publisher, pages, genre, UserSession.getInstance().getLoggedInUser());
            reset();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Book donated successfully! \nPS: You need to deliver the book to the library and it has to be approved!");

        } catch (SQLException | RemoteException e) {
            showAlert(Alert.AlertType.ERROR, "Submission Error", "An error occurred while donating the book.");
            e.printStackTrace();
        }
    }

    /**
     * Initializes the genre combo box with genres from the database.
     *
     * @throws SQLException if a database access error occurs
     */
    public void initializeGenreComboBox() throws SQLException {
        genreComboBox.getItems().add("All");
        genreComboBox.getItems().addAll(BookDatabaseImplementation.getInstance().readGenres());
        genreComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Resets the input fields in the view.
     * This method clears the input fields, allowing the user to start with a fresh form.
     */
    public void reset() {
        bookTitle.clear();
        bookAuthor.clear();
        bookISBN.clear();
        bookYear.clear();
        bookPublisher.clear();
        bookPages.clear();
        genreComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Gets the root node of the view.
     * This method returns the root node of the view, which is used to display the entire view.
     *
     * @return the root node of the view
     */
    public Region getRoot() {
        return root;
    }

    /**
     * Shows an alert with the specified type, title, and content.
     *
     * @param alertType The type of the alert.
     * @param title     The title of the alert.
     * @param content   The content of the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
