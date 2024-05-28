package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminServerLogViewModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Controller class for the admin interface to view server logs.
 * This class handles user interactions and updates the view based on changes in the underlying data.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminServerLogViewController {

    private ViewHandler viewHandler;
    private AdminServerLogViewModel viewModel;
    private Region root;
    private final File file = new File("src/main/java/sep/file/LibraryLog");

    @FXML
    private Button backButton;
    @FXML
    private TextArea logTextArea;

    /**
     * Initializes the view with the provided view handler, view model, and root region.
     *
     * @param viewHandler The view handler for managing views.
     * @param viewModel   The view model for the server logs.
     * @param root        The root region of the view.
     */
    public void init(ViewHandler viewHandler, AdminServerLogViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeArea();
    }

    /**
     * Initializes the text area for displaying server logs.
     * This method reads the log file and populates the text area with its content.
     */
    public void initializeArea() {
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                logTextArea.setText(content.toString());
            } catch (IOException e) {
                logTextArea.setText("Failed to load log file: " + e.getMessage());
            }
        } else {
            logTextArea.setText("Log file not found.");
        }
    }

    /**
     * Handles the event when the back button is clicked.
     * This method is triggered when the back button in the user interface is clicked.
     * It navigates back to the admin main view.
     *
     * @throws RemoteException if a remote communication-related exception occurs
     */
    @FXML
    private void backButtonClicked() throws RemoteException {
        viewHandler.openView("adminMainView");
    }

    /**
     * Resets the view to its initial state.
     * This method clears the text area and performs any necessary reset actions.
     */
    public void reset() {

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
}
