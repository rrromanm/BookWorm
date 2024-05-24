package sep.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.model.Event;
import sep.model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class EventsViewModel implements PropertyChangeListener
{
    private final ListProperty<Event> eventList;
    private final Model model;
    public EventsViewModel(Model model){
        this.model = model;
        this.eventList = new SimpleListProperty<>(FXCollections.observableArrayList());
        model.addPropertyChangeListener(this);
    }
    public void bindList(ObjectProperty<ObservableList<Event>> property) throws RemoteException {
        property.bindBidirectional(eventList);
    }
    public void resetEventList() throws RemoteException {
        eventList.setAll(model.getAllEvents());
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("received in main model " + evt.getPropertyName());
        Platform.runLater(() -> {
            if ("CreateEvent".equals(evt.getPropertyName())){
                try {
                    resetEventList();
                    System.out.println("refreshed table");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if ("DeleteEvent".equals(evt.getPropertyName())){
                try {
                    resetEventList();
                    System.out.println("refreshed table");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if ("UpdateEvent".equals(evt.getPropertyName())) {
                try {
                    resetEventList();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
