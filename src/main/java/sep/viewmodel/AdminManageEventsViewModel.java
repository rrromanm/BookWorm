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

public class AdminManageEventsViewModel implements PropertyChangeListener
{
    private final Model model;
    private final ListProperty<Event> eventList;
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty date;
    private PropertyChangeSupport support;
    public AdminManageEventsViewModel(Model model){
        this.model = model;
        this.eventList = new SimpleListProperty<>(FXCollections.observableArrayList());

        this.title = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.date = new SimpleStringProperty("");

        this.support = new PropertyChangeSupport(this);
        model.addPropertyChangeListener(this);
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
        try{
            List<Event> events = AdminDatabaseImplementation.getInstance().getAllEvents();
            eventList.setAll(events);
            support.firePropertyChange("CreateEvent", null, eventList);
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }
    public void createEvent() throws RemoteException{
        try{
            model.createEvent(title.get(), description.get(), date.get());
            support.firePropertyChange("CreateEvent", null, eventList);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteEvent(int id) throws RemoteException {
        try {
            model.deleteEvent(id);
            support.firePropertyChange("DeleteEvent", false, true);
            resetEventList();
        } catch (Exception e) {
            throw new RemoteException("Failed to delete event: " + e.getMessage());
        }
    }

    public void updateEvent(int id, String title, String description, String eventDate) throws RemoteException{
        try {
            model.updateEvent(id, title, description, eventDate);
            support.firePropertyChange("UpdateEvent", false, true);
            resetEventList();
        } catch (Exception e) {
            throw new RemoteException("Failed to edit event: " + e.getMessage());
        }

    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
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
