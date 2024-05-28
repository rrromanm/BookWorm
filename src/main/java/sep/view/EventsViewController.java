package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Event;
import sep.viewmodel.EventsViewModel;

import java.rmi.RemoteException;

/**
 * Controller class for the events view.
 * Handles user interactions and updates the view based on changes in the underlying data.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class EventsViewController {

    @FXML private Button backButton;
    @FXML private TableView<Event> eventTableView;
    @FXML private TableColumn<Event, String> titleColumn;
    @FXML private TableColumn<Event, String> descriptionColumn;
    @FXML private TableColumn<Event, String> dateColumn;

    private Region root;
    private ViewHandler viewHandler;
    private EventsViewModel eventsViewModel;

    /**
     * Initializes the view with the provided view handler, view model, and root region.
     *
     * @param viewHandler The view handler for managing views.
     * @param viewModel   The view model for events.
     * @param root        The root region of the view.
     * @throws RemoteException if a remote communication-related exception occurs
     */
    public void init(ViewHandler viewHandler, EventsViewModel viewModel, Region root) throws RemoteException {
        this.viewHandler = viewHandler;
        this.eventsViewModel = viewModel;
        this.root = root;
        initializeTableView();

        this.eventsViewModel.bindList(eventTableView.itemsProperty());
        viewModel.resetEventList();
    }

    /**
     * Initializes the table view with columns for event data.
     * Sets up the columns to display the title, description, and date of events.
     */
    private void initializeTableView() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
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
        viewHandler.openView("userMain");
    }

    /**
     * Resets the view to its initial state.
     * This method clears any data or selections in the view, allowing the user to start fresh.
     */
    public void reset() {
        // Implement reset logic if needed
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
