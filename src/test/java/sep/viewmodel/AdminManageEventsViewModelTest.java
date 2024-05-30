package sep.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import sep.jdbc.AdminDatabaseImplementation;
import sep.model.Event;
import sep.model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AdminManageEventsViewModelTest {

    private Model model;
    private AdminManageEventsViewModel viewModel;
    private PropertyChangeListener listener;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        viewModel = new AdminManageEventsViewModel(model);
        new JFXPanel();
        listener = mock(PropertyChangeListener.class);
    }

    @Test
    public void testConstructorInitialization() {
        assertNotNull(viewModel);
        assertNotNull(viewModel.eventList);
        verify(model, times(1)).addPropertyChangeListener(viewModel);
    }

    @Test
    public void testBindList() throws RemoteException {
        ObjectProperty<ObservableList<Event>> property = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        viewModel.bindList(property);

        assertEquals(viewModel.eventList.get(), property.get());
    }

    @Test
    public void testBindTitle() {
        SimpleStringProperty property = new SimpleStringProperty();
        viewModel.bindTitle(property);

        assertEquals(viewModel.title.get(), property.get());
    }

    @Test
    public void testBindDescription() {
        SimpleStringProperty property = new SimpleStringProperty();
        viewModel.bindDescription(property);

        assertEquals(viewModel.description.get(), property.get());
    }

    @Test
    public void testBindDate() {
        SimpleStringProperty property = new SimpleStringProperty();
        viewModel.bindDate(property);

        assertEquals(viewModel.date.get(), property.get());
    }

    @Test
    public void testCreateEvent() throws RemoteException {
        viewModel.title.set("Event1");
        viewModel.description.set("Description1");
        viewModel.date.set("2024-06-01");

        viewModel.createEvent();

        verify(model, times(1)).createEvent("Event1", "Description1", "2024-06-01");
    }

    @Test
    public void testDeleteEvent() throws RemoteException {
        viewModel.deleteEvent(1);

        verify(model, times(1)).deleteEvent(1);
    }

    @Test
    public void testUpdateEvent() throws RemoteException {
        viewModel.updateEvent(1, "UpdatedTitle", "UpdatedDescription", "2024-06-01");

        verify(model, times(1)).updateEvent(1, "UpdatedTitle", "UpdatedDescription", "2024-06-01");
    }
}
