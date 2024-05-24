package sep.view;

import dk.via.remote.observer.RemotePropertyChangeSupport;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sep.model.Book;
import sep.model.Event;
import sep.model.Patron;
import sep.viewmodel.AdminManageDonatedBooksViewModel;
import sep.viewmodel.EventsViewModel;

import java.rmi.RemoteException;

public class EventsViewController
{
    @FXML
    private Button back;
    @FXML private TableView<Event> eventTableView;
    @FXML private TableColumn<Event,String> titleColumn;
    @FXML private TableColumn<Event,String> descriptionColumn;
    @FXML private TableColumn<Event,String> dateColumn;

    private Region root;
    private ViewHandler viewHandler;
    private EventsViewModel eventsViewModel;

    public void init(ViewHandler viewHandler, EventsViewModel viewModel, Region root) throws RemoteException {
        this.viewHandler = viewHandler;
        this.eventsViewModel = viewModel;
        this.root = root;
        initializeTableView();

        this.eventsViewModel.bindList(eventTableView.itemsProperty());
        viewModel.resetEventList();
    }

    public void initializeTableView(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
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
