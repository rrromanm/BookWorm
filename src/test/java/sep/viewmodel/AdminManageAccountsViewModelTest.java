package sep.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sep.jdbc.PatronDatabaseImplementation;
import sep.model.Model;
import sep.model.Patron;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminManageAccountsViewModelTest {
    private Model model;
    private AdminManageAccountsViewModel adminManageAccountsViewModel;
    private PropertyChangeListener listener;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        adminManageAccountsViewModel = new AdminManageAccountsViewModel(model);
        listener = mock(PropertyChangeListener.class);
    }

    @Test
    public void testBindList() throws RemoteException {
        ObjectProperty<ObservableList<Patron>> property = new SimpleObjectProperty<>(FXCollections.observableArrayList());

        assertDoesNotThrow(() -> adminManageAccountsViewModel.bindList(property));

        assertNotNull(property.getValue());
    }

    @Test
    public void testBindSelectedBook() throws RemoteException {
        Patron patron1 = new Patron(1, "John", "Doe", "johndoe", "password123", "john.doe@example.com", "1234567890", 0);
        ReadOnlyObjectProperty<Patron> property = new SimpleObjectProperty<>(patron1);

        assertDoesNotThrow(() -> adminManageAccountsViewModel.bindSelectedBook(property));

        assertNotNull(property.getValue());
    }

    @Test
    public void testUpdateUsername() throws RemoteException {
        int userID = 1;
        String newUsername = "newUsername";

        assertDoesNotThrow(() -> adminManageAccountsViewModel.updateUsername(newUsername, userID));

        verify(model, times(1)).updateUsername(userID, newUsername);
    }

    @Test
    public void testUpdateUsernameThrowsException() throws RemoteException {
        doThrow(new IllegalStateException("Error occurred")).when(model).updateUsername(anyInt(), anyString());

        assertThrows(IllegalStateException.class, () -> {
            adminManageAccountsViewModel.updateUsername(anyString(), anyInt());
        });
    }

    @Test
    public void testUpdateEmail() throws RemoteException {
        int userID = 1;
        String newEmail = "newEmail@example.com";

        assertDoesNotThrow(() -> adminManageAccountsViewModel.updateEmail(newEmail, userID));

        verify(model, times(1)).updateEmail(userID, newEmail);
    }

    @Test
    public void testUpdateEmailThrowsException() throws RemoteException {
        doThrow(new IllegalStateException("Error occurred")).when(model).updateEmail(anyInt(), anyString());

        assertThrows(IllegalStateException.class, () -> {
            adminManageAccountsViewModel.updateEmail(anyString(), anyInt());
        });
    }

    @Test
    public void testUpdatePhoneNumber() throws RemoteException {
        int userID = 1;
        String newPhoneNumber = "1234567890";

        assertDoesNotThrow(() -> adminManageAccountsViewModel.updatePhoneNumber(newPhoneNumber, userID));

        verify(model, times(1)).updatePhoneNumber(userID, newPhoneNumber);
    }

    @Test
    public void testUpdatePhoneNumberThrowsException() throws RemoteException {
        doThrow(new IllegalStateException("Error occurred")).when(model).updatePhoneNumber(anyInt(), anyString());

        assertThrows(IllegalStateException.class, () -> {
            adminManageAccountsViewModel.updatePhoneNumber(anyString(), anyInt());
        });
    }

    @Test
    public void testUpdateFirstName() throws RemoteException {
        int userID = 1;
        String newFirstName = "John";

        assertDoesNotThrow(() -> adminManageAccountsViewModel.updateFirstName(newFirstName, userID));

        verify(model, times(1)).updateFirstName(userID, newFirstName);
    }

    @Test
    public void testUpdateFirstNameThrowsException() throws RemoteException {
        doThrow(new IllegalStateException("Error occurred")).when(model).updateFirstName(anyInt(), anyString());

        assertThrows(IllegalStateException.class, () -> {
            adminManageAccountsViewModel.updateFirstName(anyString(), anyInt());
        });
    }

    @Test
    public void testUpdateLastName() throws RemoteException {
        int userID = 1;
        String newLastName = "Doe";

        assertDoesNotThrow(() -> adminManageAccountsViewModel.updateLastName(newLastName, userID));

        verify(model, times(1)).updateLastName(userID, newLastName);
    }

    @Test
    public void testUpdateLastNameThrowsException() throws RemoteException {
        doThrow(new IllegalStateException("Error occurred")).when(model).updateLastName(anyInt(), anyString());

        assertThrows(IllegalStateException.class, () -> {
            adminManageAccountsViewModel.updateLastName(anyString(), anyInt());
        });
    }

    @Test
    public void testUpdatePassword() throws RemoteException {
        int userID = 1;
        String newPassword = "newPassword";

        assertDoesNotThrow(() -> adminManageAccountsViewModel.updatePassword(userID, newPassword));

        verify(model, times(1)).updatePassword(userID, newPassword);
    }

    @Test
    public void testUpdateThrowsException() throws RemoteException {
        doThrow(new IllegalStateException("Error occurred")).when(model).updatePassword(anyInt(), anyString());

        assertThrows(IllegalStateException.class, () -> {
            adminManageAccountsViewModel.updatePassword(anyInt(), anyString());
        });
    }

    @Test
    public void testUpdateFees() throws RemoteException {
        int userID = 1;
        int newFees = 10;

        assertDoesNotThrow(() -> adminManageAccountsViewModel.updateFees(userID, newFees));

        verify(model, times(1)).updateFees(userID, newFees);
    }

    @Test
    public void testUpdateFeesThrowsException() throws RemoteException {
        doThrow(new IllegalStateException("Error occurred")).when(model).updateFees(anyInt(), anyInt());

        assertThrows(IllegalStateException.class, () -> {
            adminManageAccountsViewModel.updateFees(anyInt(), anyInt());
        });
    }

    @Test
    public void testDeletePatron() throws RemoteException, SQLException {
        int patronID = 1;

        adminManageAccountsViewModel.deletePatron(patronID);

        verify(model, times(1)).deletePatron(patronID);
    }

    @Test
    public void testDeletePatronThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(model).deletePatron(anyInt());

        assertThrows(RemoteException.class, () -> {
            adminManageAccountsViewModel.deletePatron(anyInt());
        });
    }
}
