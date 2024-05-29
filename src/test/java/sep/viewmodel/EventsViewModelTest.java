package sep.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Event;
import sep.model.Model;
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
}
