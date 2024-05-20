package sep.view;

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
    @FXML private TableColumn<Event,Integer> idColumn;
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
    private TextField titleTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField descriptionTextField;

    public void init(ViewHandler viewHandler, AdminManageEventsViewModel viewModel, Region root) throws RemoteException {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeTableView();

        this.viewModel.bindList(eventsTable.itemsProperty());
        this.viewModel.bindTitle(titleTextField.textProperty());
        this.viewModel.bindDescription(descriptionTextField.textProperty());
        this.viewModel.bindDate(datePicker.getEditor().textProperty());
        viewModel.resetEventList();
    }

    public void initializeTableView(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
    }

    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("adminMainView");
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
        //TODO save the values from the fields into a new event
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
