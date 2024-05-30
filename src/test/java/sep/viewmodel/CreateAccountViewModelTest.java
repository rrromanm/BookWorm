package sep.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Model;
import java.rmi.RemoteException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateAccountViewModelTest {

    private Model model;
    private CreateAccountViewModel viewModel;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        viewModel = new CreateAccountViewModel(model);
    }

    @Test
    public void testConstructorInitialization() {
        assertNotNull(viewModel);
        assertEquals("", viewModel.email.get());
        assertEquals("", viewModel.username.get());
        assertEquals("", viewModel.password.get());
        assertEquals("", viewModel.repeatPassword.get());
        assertEquals("", viewModel.first_name.get());
        assertEquals("", viewModel.last_name.get());
        assertEquals("", viewModel.phone_number.get());
    }

    @Test
    public void testCreatePatronSuccess() throws RemoteException {
        viewModel.username.set("testUser");
        viewModel.password.set("testPass");
        viewModel.repeatPassword.set("testPass");
        viewModel.first_name.set("John");
        viewModel.last_name.set("Doe");
        viewModel.email.set("john.doe@example.com");
        viewModel.phone_number.set("1234567890");

        viewModel.createPatron();

        verify(model, times(1)).createPatron(
                "testUser", "testPass", "John", "Doe",
                "john.doe@example.com", "1234567890", 0
        );
    }

    @Test
    public void testCreatePatronPasswordMismatch() {
        viewModel.password.set("testPass");
        viewModel.repeatPassword.set("differentPass");

        Exception exception = assertThrows(RemoteException.class, () -> {
            viewModel.createPatron();
        });

        String expectedMessage = "Passwords must match!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testBindProperties() {
        StringProperty emailProperty = new SimpleStringProperty();
        StringProperty usernameProperty = new SimpleStringProperty();
        StringProperty passwordProperty = new SimpleStringProperty();
        StringProperty repeatPasswordProperty = new SimpleStringProperty();
        StringProperty firstNameProperty = new SimpleStringProperty();
        StringProperty lastNameProperty = new SimpleStringProperty();
        StringProperty phoneNumberProperty = new SimpleStringProperty();

        viewModel.bindEmail(emailProperty);
        viewModel.bindUsername(usernameProperty);
        viewModel.bindPassword(passwordProperty);
        viewModel.bindRepeatPassword(repeatPasswordProperty);
        viewModel.bindFirstName(firstNameProperty);
        viewModel.bindLastName(lastNameProperty);
        viewModel.bindPhoneNumber(phoneNumberProperty);

        viewModel.email.set("email@example.com");
        viewModel.username.set("testUser");
        viewModel.password.set("testPass");
        viewModel.repeatPassword.set("testPass");
        viewModel.first_name.set("John");
        viewModel.last_name.set("Doe");
        viewModel.phone_number.set("1234567890");

        assertEquals("email@example.com", emailProperty.get());
        assertEquals("testUser", usernameProperty.get());
        assertEquals("testPass", passwordProperty.get());
        assertEquals("testPass", repeatPasswordProperty.get());
        assertEquals("John", firstNameProperty.get());
        assertEquals("Doe", lastNameProperty.get());
        assertEquals("1234567890", phoneNumberProperty.get());
    }

    @Test
    public void testReset() {
        viewModel.email.set("email@example.com");
        viewModel.username.set("testUser");
        viewModel.password.set("testPass");
        viewModel.repeatPassword.set("testPass");
        viewModel.first_name.set("John");
        viewModel.last_name.set("Doe");
        viewModel.phone_number.set("1234567890");

        viewModel.reset();

        assertEquals("", viewModel.email.get());
        assertEquals("", viewModel.username.get());
        assertEquals("", viewModel.password.get());
        assertEquals("", viewModel.repeatPassword.get());
        assertEquals("", viewModel.first_name.get());
        assertEquals("", viewModel.last_name.get());
        assertEquals("", viewModel.phone_number.get());
    }
}
