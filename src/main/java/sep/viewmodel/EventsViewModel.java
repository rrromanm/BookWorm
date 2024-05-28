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

/**
 * The EventsViewModel class provides the view model for managing events in the system.
 * It interacts with the model to retrieve and update events, and listens for changes to events.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class EventsViewModel implements PropertyChangeListener
{
    private final ListProperty<Event> eventList;
    private final Model model;

    /**
     * Constructs an EventsViewModel with the specified model.
     *
     * @param model The model to interact with for managing events
     */
    public EventsViewModel(Model model){
        this.model = model;
        this.eventList = new SimpleListProperty<>(FXCollections.observableArrayList());
        model.addPropertyChangeListener(this);
    }

    /**
     * Binds the provided property to the eventList property in the view model.
     *
     * @param property The property to bind to the eventList
     * @throws RemoteException If a remote communication error occurs
     */
    public void bindList(ObjectProperty<ObservableList<Event>> property) throws RemoteException {
        property.bindBidirectional(eventList);
    }

    /**
     * Resets the event list by fetching all events from the model.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    public void resetEventList() throws RemoteException {
        eventList.setAll(model.getAllEvents());
    }

    /**
     * Listens for property change events and handles them accordingly.
     * This method is called when a property change event is fired by the model.
     *
     * @param evt The property change event
     */
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
