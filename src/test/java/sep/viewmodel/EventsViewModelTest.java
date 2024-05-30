package sep.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Event;
import sep.model.Model;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventsViewModelTest {

    private EventsViewModel viewModel;
    private Model model;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
        viewModel = new EventsViewModel(model);
        new JFXPanel();
    }

    @Test
    void bindList() throws RemoteException {
        // Given
        ObjectProperty<ObservableList<Event>> property = new SimpleObjectProperty<>();

        // When
        viewModel.bindList(property);

        // Then
        assertEquals(viewModel.eventList, property.get());
    }

    @Test
    void resetEventList() throws RemoteException {
        // Given
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(1, "Event 1", "Description 1", "2024-06-01"));
        events.add(new Event(2, "Event 2", "Description 2", "2024-06-02"));
        when(model.getAllEvents()).thenReturn(events);

        // When
        viewModel.resetEventList();

        // Then
        assertEquals(events, viewModel.eventList.get());
    }

    @Test
    void propertyChange_CreateEvent() throws RemoteException {
        // Given
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(1, "Event 1", "Description 1", "2024-06-01"));
        events.add(new Event(2, "Event 2", "Description 2", "2024-06-02"));
        when(model.getAllEvents()).thenReturn(events);

        // When
        PropertyChangeEvent event = new PropertyChangeEvent(this, "CreateEvent", null, null);
        Platform.runLater(() -> viewModel.propertyChange(event));


        Platform.runLater(() -> assertEquals(events, viewModel.eventList.get()));
    }

    @Test
    void propertyChange_DeleteEvent() throws RemoteException {
        // Given
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(1, "Event 1", "Description 1", "2024-06-01"));
        events.add(new Event(2, "Event 2", "Description 2", "2024-06-02"));
        when(model.getAllEvents()).thenReturn(events);

        // When
        PropertyChangeEvent event = new PropertyChangeEvent(this, "DeleteEvent", null, null);
        Platform.runLater(() -> viewModel.propertyChange(event));


        Platform.runLater(() -> assertEquals(events, viewModel.eventList.get()));
    }

    @Test
    void propertyChange_UpdateEvent() throws RemoteException {
        // Given
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(1, "Event 1", "Description 1", "2024-06-01"));
        events.add(new Event(2, "Event 2", "Description 2", "2024-06-02"));
        when(model.getAllEvents()).thenReturn(events);

        // When
        PropertyChangeEvent event = new PropertyChangeEvent(this, "UpdateEvent", null, null);
        Platform.runLater(() -> viewModel.propertyChange(event));


        Platform.runLater(() -> assertEquals(events, viewModel.eventList.get()));
    }

    @Test
    void propertyChange_CreateEvent_RemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "CreateEvent", null, null);
        doThrow(new RemoteException()).when(model).getAllEvents();

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllEvents();

    }

    @Test
    void propertyChange_DeleteEvent_RemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "DeleteEvent", null, null);
        doThrow(new RemoteException()).when(model).getAllEvents();

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllEvents();

    }

    @Test
    void propertyChange_UpdateEvent_RemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "UpdateEvent", null, null);
        doThrow(new RemoteException()).when(model).getAllEvents();

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllEvents();
    }

}
