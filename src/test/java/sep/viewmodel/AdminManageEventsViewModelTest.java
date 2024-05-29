package sep.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sep.model.Event;
import sep.model.Model;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminManageEventsViewModelTest {
    @Mock
    private Model model;

    private AdminManageEventsViewModel viewModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new AdminManageEventsViewModel(model);
    }

    @Test
    void createEvent() throws RemoteException {
        String title = "New Event";
        String description = "New Event Description";
        String date = "2024-06-15";

        viewModel.title.set(title);
        viewModel.description.set(description);
        viewModel.date.set(date);

        viewModel.createEvent();

        verify(model).createEvent(title, description, date);
    }

    @Test
    void deleteEvent() throws RemoteException {
        int eventId = 1;
        viewModel.deleteEvent(eventId);
        verify(model).deleteEvent(eventId);
    }

    @Test
    void updateEvent() throws RemoteException {
        int id = 1;
        String title = "Updated Event";
        String description = "Updated Event Description";
        String date = "2024-06-20";

        viewModel.updateEvent(id, title, description, date);
        verify(model).updateEvent(id, title, description, date);
    }
}
