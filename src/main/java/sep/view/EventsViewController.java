package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import sep.model.Book;
import sep.viewmodel.AdminManageDonatedBooksViewModel;
import sep.viewmodel.EventsViewModel;

import java.rmi.RemoteException;

public class EventsViewController
{
    @FXML
    private Button back;
    //TODO create a class event so its possible to populate de table
//    @FXML private TableView<Event> eventTableView;
//    @FXML private TableColumn<Event,String> titleColumn;
//    @FXML private TableColumn<Event,Date> dateColumn;
//    @FXML private TableColumn<Event,String> descriptionTable;

    private Region root;
    private ViewHandler viewHandler;
    private EventsViewModel eventsViewModel;

    public void init(ViewHandler viewHandler, EventsViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.eventsViewModel = viewModel;
        this.root = root;
        // TODO create a initialize method for thew table;
        // populate the tableView should also be here
    }
    @FXML
    private void backButtonClicked() throws RemoteException
    {
        viewHandler.openView("userMain");
    }
    public void reset()
    {

    }

    public Region getRoot()
    {
        return root;
    }


}
