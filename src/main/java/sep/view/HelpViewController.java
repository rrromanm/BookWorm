package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import sep.viewmodel.HelpViewModel;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Controller class for the Help view.
 * Handles user interactions and updates the view based on changes in the underlying data.
 *
 * @Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class HelpViewController {

    private ViewHandler viewHandler;
    private HelpViewModel viewModel;
    private Region root;

    @FXML
    private Button backButton;
    @FXML
    private Button userGuideButton;

    /**
     * Initializes the view with the provided view handler, view model, and root region.
     *
     * @param viewHandler The view handler for managing views.
     * @param viewModel   The view model for the help view.
     * @param root        The root region of the view.
     */
    public void init(ViewHandler viewHandler, HelpViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    /**
     * Handles the event when the back button is clicked.
     * This method is triggered when the back button in the user interface is clicked.
     * It navigates back to the user main view.
     *
     * @throws RemoteException if a remote communication-related exception occurs
     */
    @FXML
    private void backButtonClicked() throws RemoteException {
        viewHandler.openView(ViewFactory.USERMAIN);
    }

    /**
     * Handles the event when the User Guide button is clicked.
     * This method is triggered when the User Guide button in the user interface is clicked.
     * It opens the user guide PDF.
     *
     * @throws RemoteException if a remote communication-related exception occurs
     */
    @FXML
    private void userGuideButtonClicked() throws RemoteException {
        File pdfFile = new File("src/main/resources/sep/UserGuide.pdf");

        if (pdfFile.exists()) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(pdfFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Desktop is not supported on this system.");
            }
        } else {
            System.out.println("The file does not exist.");
        }
    }

    /**
     * Resets the view to its initial state.
     * This method clears any data or selections in the view, allowing the user to start fresh.
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
