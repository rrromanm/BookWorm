package sep.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.model.Event;
import sep.model.Model;

import java.rmi.RemoteException;

public class AdminManageEventsViewModel
{
    private final Model model;
    private final ListProperty<Event> eventList;
    public AdminManageEventsViewModel(Model model){
        this.model = model;
        this.eventList = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public void bindList(ObjectProperty<ObservableList<Event>> property) throws RemoteException {
        property.bindBidirectional(eventList);
    }
    public void resetEventList() throws RemoteException {
        eventList.setAll(model.getAllEvents());
    }
}
