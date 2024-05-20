package sep.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Event;
import sep.viewmodel.AdminManageEventsViewModel;

import java.rmi.RemoteException;

public class AdminManageEventsViewController
{
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
    private TextField titleTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField descriptionTextField;
    private ReadOnlyObjectProperty<Event> selectedEvent;

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

        selectedEvent = eventsTable.getSelectionModel().selectedItemProperty();
        selectedEvent.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                titleTextField.setText(newValue.getTitle());
                descriptionTextField.setText(newValue.getDescription());
                datePicker.getEditor().setText(newValue.getEventDate());

                // Make the fields non-editable
                titleTextField.setDisable(true);
                descriptionTextField.setDisable(true);
                datePicker.setDisable(true);

                clearButton.setVisible(true);
                addEvent.setVisible(false);
            } else {
                // Make the fields editable if no event is selected
                titleTextField.setDisable(false);
                descriptionTextField.setDisable(false);
                datePicker.setDisable(false);
                clearButton.setVisible(false);
                addEvent.setVisible(true);
            }
        });
    }

    public void initializeTableView(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
    }

    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("adminMainView");
    }

    public void populateTextFields() {
        selectedEvent = eventsTable.getSelectionModel().selectedItemProperty();
        selectedEvent.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                titleTextField.setText(newValue.getTitle());
                descriptionTextField.setText(newValue.getDescription());
                datePicker.getEditor().setText(newValue.getEventDate());

                titleTextField.setDisable(true);
                descriptionTextField.setDisable(true);
                datePicker.setDisable(true);
                clearButton.setVisible(true);
                addEvent.setVisible(false);
            } else {
                titleTextField.setDisable(false);
                descriptionTextField.setDisable(false);
                datePicker.setDisable(false);
                clearButton.setVisible(false);
                addEvent.setVisible(true);
            }
        });
    }


    @FXML
    private void addEvent()
    {
        try {
            this.viewModel.createEvent();
            viewModel.resetEventList();
            reset();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void onDelete(){
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                viewModel.deleteEvent(new Event(selectedEvent.getTitle(), selectedEvent.getDescription(), selectedEvent.getEventDate()));
                viewModel.resetEventList();
                reset();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Event Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an event to delete.");
            alert.showAndWait();
        }
    }
    @FXML
    private void onClear(){
        eventsTable.getSelectionModel().clearSelection();
        titleTextField.clear();
        descriptionTextField.clear();
        datePicker.getEditor().clear();
        titleTextField.setDisable(false);
        descriptionTextField.setDisable(false);
        datePicker.setDisable(false);

        // Hide the deselect button and show the add event button
        clearButton.setVisible(false);
        addEvent.setVisible(true);
    }

    public void reset()
    {
        titleTextField.clear();
        descriptionTextField.clear();
        datePicker.getEditor().clear();
    }

    public Region getRoot()
    {
        return root;
    }
}
