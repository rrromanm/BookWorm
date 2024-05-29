package sep.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Model;
import sep.model.Patron;
import java.rmi.RemoteException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginViewModelTest {

    private LoginViewModel viewModel;
    private Model model;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
        viewModel = new LoginViewModel(model);
    }

    @Test
    void login_Successful() throws RemoteException {
        // Given
        String username = "testUser";
        String password = "testPassword";
        Patron patron = new Patron(1, "Test", "User", username, password, "email", "phoneNumber", 1);
        when(model.login(username, password)).thenReturn(patron);

        // When
        viewModel.bindUsername(new SimpleStringProperty(username));
        viewModel.bindPassword(new SimpleStringProperty(password));
        Patron loggedInPatron = viewModel.login();

        // Then
        assertNotNull(loggedInPatron);
        assertEquals(username, loggedInPatron.getUsername());
        assertEquals(password, loggedInPatron.getPassword());
        assertEquals("", viewModel.error.get());
    }

    @Test
    void login_Unsuccessful() throws RemoteException {
        // Given
        String username = "testUser";
        String password = "wrongPassword";
        when(model.login(username, password)).thenReturn(null);

        // When
        viewModel.bindUsername(new SimpleStringProperty(username));
        viewModel.bindPassword(new SimpleStringProperty(password));
        Patron loggedInPatron = viewModel.login();

        // Then
        assertNull(loggedInPatron);
        assertEquals("Username or password is incorrect. Try again", viewModel.error.get());
    }

    @Test
    void login_RemoteException() throws RemoteException {
        // Given
        String username = "testUser";
        String password = "testPassword";
        String errorMessage = "Username or password is incorrect. Try again";
        when(model.login(username, password)).thenThrow(new RemoteException(errorMessage));

        // When
        viewModel.bindUsername(new SimpleStringProperty(username));
        viewModel.bindPassword(new SimpleStringProperty(password));
        Patron loggedInPatron = viewModel.login();

        // Then
        assertNull(loggedInPatron);
        assertEquals(errorMessage, viewModel.error.get());
    }

    @Test
    void bindUsername() {
        // Given
        StringProperty property = new SimpleStringProperty();

        // When
        viewModel.bindUsername(property);
        viewModel.username.set("test");

        // Then
        assertEquals("test", property.get());
    }

    @Test
    void bindPassword() {
        // Given
        StringProperty property = new SimpleStringProperty();

        // When
        viewModel.bindPassword(property);
        viewModel.password.set("test");

        // Then
        assertEquals("test", property.get());
    }

    @Test
    void bindError() {
        // Given
        StringProperty property = new SimpleStringProperty();

        // When
        viewModel.bindError(property);
        viewModel.error.set("test");

        // Then
        assertEquals("test", property.get());
    }

    @Test
    void reset() {
        // Given
        viewModel.username.set("test");
        viewModel.password.set("test");

        // When
        viewModel.reset();

        // Then
        assertEquals("", viewModel.username.get());
        assertEquals("", viewModel.password.get());
    }
}
