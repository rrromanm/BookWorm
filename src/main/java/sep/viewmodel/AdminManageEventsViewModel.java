package sep.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.model.Event;
import sep.model.Model;

import java.rmi.RemoteException;

public class AdminManageEventsViewModel
{
    private final Model model;
    private final ListProperty<Event> eventList;
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty date;
    public AdminManageEventsViewModel(Model model){
        this.model = model;
        this.eventList = new SimpleListProperty<>(FXCollections.observableArrayList());

        this.title = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.date = new SimpleStringProperty("");
    }

    public void bindList(ObjectProperty<ObservableList<Event>> property) throws RemoteException {
        property.bindBidirectional(eventList);
    }
    public void bindTitle(StringProperty property){
        this.title.bindBidirectional(property);
    }
    public void bindDescription(StringProperty property){
        this.description.bindBidirectional(property);
    }
    public void bindDate(StringProperty property){
        this.date.bindBidirectional(property);
    }
    public void resetEventList() throws RemoteException {
        eventList.setAll(model.getAllEvents());
    }
    public void createEvent() throws RemoteException{
        try{
            model.createEvent(title.get(), description.get(), date.get());
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
