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
    private Button save;
    @FXML
    private TextField title;
    @FXML
    private DatePicker date;
    @FXML
    private TextField description;

    public void init(ViewHandler viewHandler, AdminManageEventsViewModel viewModel, Region root) throws RemoteException {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeTableView();
        this.viewModel.bindList(eventsTable.itemsProperty());
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

    }

    @FXML
    private void onSave(){
        //TODO save the values from the fields into a new event
    }

    public void reset()
    {
//        viewModel.reset();
    }

    public Region getRoot()
    {
        return root;
    }
}
