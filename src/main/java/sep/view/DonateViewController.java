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

    @FXML private void submitButtonClicked() throws SQLException, RemoteException {
        viewModel.donateBook(bookTitle.getText(), bookAuthor.getText(), Long.parseLong(bookISBN.getText()), Integer.parseInt(bookYear.getText()),
                bookPublisher.getText(), Integer.parseInt(bookPages.getText()) , genreComboBox.getSelectionModel().getSelectedItem(), UserSession.getInstance().getLoggedInUser());
        reset();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Book donated successfully! \n " +
                "PS: You need to deliver the book to library and it has to be approved!" );
        alert.show();
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
