package sep.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.jdbc.AdminDatabaseImplementation;
import sep.jdbc.BookDatabaseImplementation;
import sep.model.Book;
import sep.model.Event;
import sep.model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The AdminManageEventsViewModel class provides the view model for managing events in the admin view.
 * It handles the logic for creating, updating, deleting, and loading events from the model.
 * This class listens for property changes from the model and updates the view accordingly.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminManageEventsViewModel implements PropertyChangeListener
{
    private final Model model;
    final ListProperty<Event> eventList;
    final StringProperty title;
    final StringProperty description;
    final StringProperty date;
    private PropertyChangeSupport support;

    /**
     * Constructs an AdminManageEventsViewModel with the specified model.
     * Initializes the event list and properties for event details, and sets up a property change listener for the model.
     *
     * @param model The model to interact with for managing events
     */
    public AdminManageEventsViewModel(Model model){
        this.model = model;
        this.eventList = new SimpleListProperty<>(FXCollections.observableArrayList());

        this.title = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.date = new SimpleStringProperty("");

        this.support = new PropertyChangeSupport(this);
        model.addPropertyChangeListener(this);
    }

    /**
     * Binds the provided property to the event list property in the view model.
     *
     * @param property The property to bind to the event list
     * @throws RemoteException If a remote communication error occurs
     */
    public void bindList(ObjectProperty<ObservableList<Event>> property) throws RemoteException {
        property.bindBidirectional(eventList);
    }

    /**
     * Binds the provided property to the title property in the view model.
     *
     * @param property The property to bind to the title
     */
    public void bindTitle(StringProperty property){
        this.title.bindBidirectional(property);
    }

    /**
     * Binds the provided property to the description property in the view model.
     *
     * @param property The property to bind to the description
     */
    public void bindDescription(StringProperty property){
        this.description.bindBidirectional(property);
    }

    /**
     * Binds the provided property to the date property in the view model.
     *
     * @param property The property to bind to the date
     */
    public void bindDate(StringProperty property){
        this.date.bindBidirectional(property);
    }

    /**
     * Resets the event list by fetching all events from the database.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    public void resetEventList() throws RemoteException {
        try{
            List<Event> events = AdminDatabaseImplementation.getInstance().getAllEvents();
            eventList.setAll(events);
            support.firePropertyChange("CreateEvent", null, eventList);
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Creates a new event with the details specified in the title, description, and date properties.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    public void createEvent() throws RemoteException{
        try{
            model.createEvent(title.get(), description.get(), date.get());
            support.firePropertyChange("CreateEvent", null, eventList);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Deletes an event with the specified ID.
     *
     * @param id The ID of the event to delete
     * @throws RemoteException If a remote communication error occurs
     */
    public void deleteEvent(int id) throws RemoteException {
        try {
            model.deleteEvent(id);
            support.firePropertyChange("DeleteEvent", false, true);
            resetEventList();
        } catch (Exception e) {
            throw new RemoteException("Failed to delete event: " + e.getMessage());
        }
    }

    /**
     * Updates an event with the specified details.
     *
     * @param id The ID of the event to update
     * @param title The new title of the event
     * @param description The new description of the event
     * @param eventDate The new date of the event
     * @throws RemoteException If a remote communication error occurs
     */
    public void updateEvent(int id, String title, String description, String eventDate) throws RemoteException{
        try {
            model.updateEvent(id, title, description, eventDate);
            support.firePropertyChange("UpdateEvent", false, true);
            resetEventList();
        } catch (Exception e) {
            throw new RemoteException("Failed to edit event: " + e.getMessage());
        }

    }

    /**
     * Adds a property change listener.
     *
     * @param listener The listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     *
     * @param listener The listener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Handles property change events from the model.
     * Updates the event list based on changes in events.
     *
     * @param evt The property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if (evt.getPropertyName().equals("CreateEvent"))
            {
                this.support.firePropertyChange("CreateEvent", false, true);
            }
            if (evt.getPropertyName().equals("DeleteEvent"))
            {
                this.support.firePropertyChange("DeleteEvent", false, true);
            }
            if (evt.getPropertyName().equals("UpdateEvent"))
            {
                this.support.firePropertyChange("UpdateEvent", false, true);
            }
        });
    }
}
