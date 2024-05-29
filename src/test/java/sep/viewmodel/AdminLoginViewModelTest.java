package sep.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Model;
import sep.viewmodel.AdminLoginViewModel;
import java.rmi.RemoteException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminLoginViewModelTest {
    private AdminLoginViewModel adminLoginViewModel;
    private Model model;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        adminLoginViewModel = new AdminLoginViewModel(model);
    }

    @Test
    public void testLogin_Successful() throws RemoteException {
        StringProperty username = new SimpleStringProperty("admin");
        StringProperty password = new SimpleStringProperty("password");

        when(model.loginAsAdmin("admin", "password")).thenReturn(true);

        adminLoginViewModel.bindUsername(username);
        adminLoginViewModel.bindPassword(password);

        assertTrue(adminLoginViewModel.login());
        verify(model, times(1)).loginAsAdmin("admin", "password");
    }

    @Test
    public void testLogin_Unsuccessful() throws RemoteException {
        StringProperty username = new SimpleStringProperty("admin");
        StringProperty password = new SimpleStringProperty("wrongpassword");

        when(model.loginAsAdmin("admin", "wrongpassword")).thenReturn(false);

        adminLoginViewModel.bindUsername(username);
        adminLoginViewModel.bindPassword(password);

        assertFalse(adminLoginViewModel.login());
        verify(model, times(1)).loginAsAdmin("admin", "wrongpassword");
    }

    @Test
    public void testLogin_RemoteException() throws RemoteException {
        StringProperty username = new SimpleStringProperty("admin");
        StringProperty password = new SimpleStringProperty("password");
        StringProperty error = new SimpleStringProperty("");

        when(model.loginAsAdmin("admin", "password")).thenThrow(new RemoteException("Connection failed"));

        adminLoginViewModel.bindUsername(username);
        adminLoginViewModel.bindPassword(password);
        adminLoginViewModel.bindError(error);

        assertFalse(adminLoginViewModel.login());
        verify(model, times(1)).loginAsAdmin("admin", "password");
    }

    @Test
    public void testReset() {
        StringProperty username = new SimpleStringProperty("admin");
        StringProperty password = new SimpleStringProperty("password");

        adminLoginViewModel.bindUsername(username);
        adminLoginViewModel.bindPassword(password);

        adminLoginViewModel.reset();
    }
}
