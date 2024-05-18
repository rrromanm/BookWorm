package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminManageEventsViewModel;

import java.rmi.RemoteException;

public class AdminManageEventsViewController
{
    private ViewHandler viewHandler;
    private AdminManageEventsViewModel viewModel;
    private Region root;

//    @FXML
//    private TableView<Event> eventsView;
//    @FXML private TableColumn<Event,Integer> idColumn;
//    @FXML private TableColumn<Event, String> titleColumn;
//    @FXML private TableColumn<Event, Integer> descriptionColumn;
//    @FXML private TableColumn<Event, Date> dateColumn;
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

    public void init(ViewHandler viewHandler, AdminManageEventsViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("adminMainView");
    }

    @FXML
    private void addEvent(){
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
