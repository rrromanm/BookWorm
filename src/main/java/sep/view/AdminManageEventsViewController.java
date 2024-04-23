package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminManageEventsViewModel;


public class AdminManageEventsViewController
{
    private ViewHandler viewHandler;
    private AdminManageEventsViewModel viewModel;
    private Region root;

//    @FXML
//    private ListView<> listView; //todo implement list of events
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
    private void backButtonClicked()
    {
        viewHandler.openView("");
    } //TODO put the right path for ADMIN MAIN VIEW

    @FXML
    private void addEvent(){
        //TODO create event logic and add them to the list
    }

    @FXML
    private void onSave(){
        //TODO save the values from the fields into a new event
    }

    public void reset()
    {

    }

    public Region getRoot()
    {
        return root;
    }
}
