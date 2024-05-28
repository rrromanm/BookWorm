package sep.view;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Event;
import sep.viewmodel.AdminManageEventsViewModel;

import java.rmi.RemoteException;

/**
 * Controller class for the admin interface to manage events.
 * This class handles user interactions and updates the view based on changes in the underlying data.
 * It implements the RemotePropertyChangeListener interface to listen for remote property changes.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminManageEventsViewController implements RemotePropertyChangeListener {

    private ViewHandler viewHandler;
    private AdminManageEventsViewModel viewModel;
    private Region root;

    @FXML
    private TableView<Event> eventsTable;
    @FXML private TableColumn<Event, String> titleColumn;
    @FXML private TableColumn<Event, String> descriptionColumn;
    @FXML private TableColumn<Event, String> dateColumn;
    @FXML
    private Button backButton;
    @FXML
    private Button addEvent;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField titleTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField descriptionTextField;
    private ReadOnlyObjectProperty<Event> selectedEvent;

    /**
     * Initializes the view with the provided view handler, view model, and root region.
     *
     * @param viewHandler The view handler for managing views.
     * @param viewModel   The view model for managing events.
     * @param root        The root region of the view.
     * @throws RemoteException If there is a problem with remote communication.
     */
    public void init(ViewHandler viewHandler, AdminManageEventsViewModel viewModel, Region root) throws RemoteException {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeTableView();
        populateTextFields();

        this.viewModel.bindList(eventsTable.itemsProperty());
        this.viewModel.bindTitle(titleTextField.textProperty());
        this.viewModel.bindDescription(descriptionTextField.textProperty());
        this.viewModel.bindDate(datePicker.getEditor().textProperty());
        viewModel.resetEventList();

        datePicker.getEditor().setDisable(true);
        saveButton.setVisible(false);

        selectedEvent = eventsTable.getSelectionModel().selectedItemProperty();
        selectedEvent.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                titleTextField.setText(newValue.getTitle());
                descriptionTextField.setText(newValue.getDescription());
                datePicker.getEditor().setText(newValue.getEventDate());

                clearButton.setVisible(true);
                addEvent.setVisible(false);
                saveButton.setVisible(true);
            } else {
                clearButton.setVisible(false);
                addEvent.setVisible(true);
                saveButton.setVisible(false);
            }
        });

        viewModel.addPropertyChangeListener(evt -> {
            if ("CreateEvent".equals(evt.getPropertyName())) {
                eventsTable.refresh();
                initializeTableView();
            }
            if ("DeleteEvent".equals(evt.getPropertyName())) {
                eventsTable.refresh();
                try {
                    viewModel.resetEventList();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if("UpdateEvent".equals(evt.getPropertyName())) {
                eventsTable.refresh();
                try {
                    viewModel.resetEventList();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Initializes the table view for displaying events.
     * This method sets up the cell value factories for each column in the table view.
     * Each column is bound to a property of the Event object.
     */
    public void initializeTableView(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
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
     * Populates the text fields with the details of the selected event.
     * This method binds the text fields to the selected event's properties.
     */
    public void populateTextFields() {
        selectedEvent = eventsTable.getSelectionModel().selectedItemProperty();
        selectedEvent.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                titleTextField.setText(newValue.getTitle());
                descriptionTextField.setText(newValue.getDescription());
                datePicker.getEditor().setText(newValue.getEventDate());

                clearButton.setVisible(true);
                addEvent.setVisible(false);
                saveButton.setVisible(true);
            } else {
                clearButton.setVisible(false);
                addEvent.setVisible(true);
                saveButton.setVisible(false);
            }
        });
    }

    /**
     * Checks if the text fields are empty.
     * This method checks if the title, description, or date fields are empty.
     *
     * @return true if any of the text fields are empty, false otherwise
     */
    public boolean emptyTextFields() {
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String eventDate = datePicker.getEditor().getText();
        return title.isEmpty() || description.isEmpty() || eventDate.isEmpty();
    }

    /**
     * Handles the event when the add event button is clicked.
     * This method is triggered when the add event button in the user interface is clicked.
     * It calls the viewModel to create a new event with the details from the text fields.
     */
    @FXML
    private void addEvent() {
        if (emptyTextFields()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Fields");
            alert.setHeaderText(null);
            alert.setContentText("Title, description or date cannot be empty.");
            alert.showAndWait();
            return;
        }

        Alert lengthAlert = new Alert(Alert.AlertType.WARNING);
        if (titleTextField.getText().length() > 50) {
            lengthAlert.setTitle("Title too long");
            lengthAlert.setHeaderText(null);
            lengthAlert.setContentText("The title must be less than 50 characters.");
            lengthAlert.showAndWait();
            return;
        }

        if (descriptionTextField.getText().length() > 200) {
            lengthAlert.setTitle("Description too long");
            lengthAlert.setHeaderText(null);
            lengthAlert.setContentText("The description must be less than 200 characters.");
            lengthAlert.showAndWait();
            return;
        }

        try {
            this.viewModel.createEvent();
            viewModel.resetEventList();
            reset();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the event when the delete button is clicked.
     * This method is triggered when the delete button in the user interface is clicked.
     * It calls the viewModel to delete the selected event.
     */
    @FXML
    private void onDelete() {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Event");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete this event?");

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

            confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == yesButton) {
                    try {
                        viewModel.deleteEvent(selectedEvent.getId());
                        viewModel.resetEventList();
                        reset();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Event Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an event to delete.");
            alert.showAndWait();
        }
    }

    /**
     * Handles the event when the clear button is clicked.
     * This method is triggered when the clear button in the user interface is clicked.
     * It clears the selection and the text fields.
     */
    @FXML
    private void onClear() {
        eventsTable.getSelectionModel().clearSelection();
        titleTextField.clear();
        descriptionTextField.clear();
        datePicker.getEditor().clear();
        titleTextField.setDisable(false);
        descriptionTextField.setDisable(false);
        datePicker.setDisable(false);

        clearButton.setVisible(false);
        addEvent.setVisible(true);
    }

    /**
     * Handles the event when the save button is clicked.
     * This method is triggered when the save button in the user interface is clicked.
     * It calls the viewModel to update the selected event with the details from the text fields.
     *
     * @throws RemoteException If there is a problem with remote communication.
     */
    @FXML
    private void onSave() throws RemoteException {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();

        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String eventDate = datePicker.getEditor().getText();

        if (emptyTextFields()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Fields");
            alert.setHeaderText(null);
            alert.setContentText("Title, description or date cannot be empty.");
            alert.showAndWait();
            return;
        }

        if(title.equals(selectedEvent.getTitle())
                && description.equals(selectedEvent.getDescription())
                && eventDate.equals(selectedEvent.getEventDate())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Changes Made");
            alert.setHeaderText(null);
            alert.setContentText("No changes have been made to the event.");
            alert.showAndWait();
            return;
        }

        viewModel.updateEvent(selectedEvent.getId(), title, description, eventDate);
        reset();

        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Changes Saved");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Changes to the event have been saved.");
        confirmationAlert.showAndWait();
    }

    /**
     * Resets the input fields and selection in the user interface.
     * This method clears the input fields and selection in the user interface,
     * allowing the user to reset the form or selection.
     */
    public void reset() {
        titleTextField.clear();
        descriptionTextField.clear();
        datePicker.getEditor().clear();
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
     * Handles property change events from remote sources.
     * This method is called when a remote property change event is received.
     *
     * @param remotePropertyChangeEvent the remote property change event
     * @throws RemoteException if a remote communication-related exception occurs
     */
    @Override
    public void propertyChange(RemotePropertyChangeEvent remotePropertyChangeEvent) throws RemoteException {
        // Handle remote property change event
    }
}
