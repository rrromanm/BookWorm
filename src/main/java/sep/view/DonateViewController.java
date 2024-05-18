package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import sep.jdbc.BookDatabaseImplementation;
import sep.model.UserSession;
import sep.viewmodel.CreateAccountViewModel;
import sep.viewmodel.DonateViewModel;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class DonateViewController {
    private ViewHandler viewHandler;
    private DonateViewModel viewModel;
    private Region root;
    @FXML private Button backButton;
    @FXML private Button SubmitButton;
    @FXML private TextField bookTitle;
    @FXML private TextField bookAuthor;
    @FXML private TextField bookISBN;
    @FXML private TextField bookYear;
    @FXML private TextField bookPublisher;
    @FXML private TextField bookPages;
    @FXML private ComboBox<String> genreComboBox;

    public void init(ViewHandler viewHandler, DonateViewModel viewModel, Region root) throws SQLException {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeGenreComboBox();
    }

    @FXML
    public void backButtonClicked() throws RemoteException
    {
        viewHandler.openView(ViewFactory.USERMAIN);
    }

    @FXML
    private void submitButtonClicked() {
        try {
            String title = bookTitle.getText();
            String author = bookAuthor.getText();
            String isbn = bookISBN.getText();
            String yearStr = bookYear.getText();
            String publisher = bookPublisher.getText();
            String pagesStr = bookPages.getText();
            String genre = genreComboBox.getSelectionModel().getSelectedItem();

            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || yearStr.isEmpty() || publisher.isEmpty() || pagesStr.isEmpty() || genre == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("All fields must be filled out.");
                alert.showAndWait();
                return;
            }

            long isbnLong;
            try {
                isbnLong = Long.parseLong(isbn);
                if (isbn.length() != 13) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("ISBN must be a 13-digit number.");
                alert.showAndWait();
                return;
            }

            int year;
            try {
                year = Integer.parseInt(yearStr);
                if (year < 1000 || year > 2025) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Year must be between 1000 and 2025.");
                alert.showAndWait();
                return;
            }

            int pages;
            try {
                pages = Integer.parseInt(pagesStr);
                if (pages <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Page count must be a positive number.");
                alert.showAndWait();
                return;
            }

            viewModel.donateBook(title, author, isbnLong, year, publisher, pages, genre, UserSession.getInstance().getLoggedInUser());
            reset();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Book donated successfully! \nPS: You need to deliver the book to library and it has to be approved!");
            alert.showAndWait();

        } catch (SQLException | RemoteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Submission Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while donating the book.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void initializeGenreComboBox() throws SQLException
    {
        genreComboBox.getItems().add("All");
        genreComboBox.getItems().addAll(BookDatabaseImplementation.getInstance()
                .readGenres());
        genreComboBox.getSelectionModel().selectFirst();
    }

    public void reset() {
        bookTitle.clear();
        bookAuthor.clear();
        bookISBN.clear();
        bookYear.clear();
        bookPublisher.clear();
        bookPages.clear();
        genreComboBox.getSelectionModel().selectFirst();
    }

    public Region getRoot(){
        return root;
    }
}
