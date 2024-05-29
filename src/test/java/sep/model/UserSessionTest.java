package sep.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserSessionTest {

    private Patron patron1;
    private Patron patron2;

    @BeforeEach
    void setUp() {
        resetSingleton(UserSession.class, "instance");

        patron1 = new Patron(1, "John", "Doe", "johndoe", "password123", "john.doe@example.com", "1234567890", 0);
        patron2 = new Patron(2, "Jane", "Smith", "janesmith", "password456", "jane.smith@example.com", "0987654321", 100);
    }

    @Test
    void testSingletonInstance() {
        UserSession instance1 = UserSession.getInstance();
        UserSession instance2 = UserSession.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void testSetAndGetLoggedInUser() {
        UserSession session = UserSession.getInstance();
        session.setLoggedInUser(patron1);
        assertEquals(patron1, session.getLoggedInUser());

        session.setLoggedInUser(patron2);
        assertEquals(patron2, session.getLoggedInUser());
    }

    @Test
    void testLoggedInUserInitiallyNull() {
        UserSession session = UserSession.getInstance();
        assertNull(session.getLoggedInUser());
    }

    /**
     * Utility method to reset a singleton instance using reflection.
     * This method is needed because the singleton instance is private and static,
     * and we want to reset it before each test.
     */
    private void resetSingleton(Class<?> clazz, String fieldName) {
        try {
            java.lang.reflect.Field instance = clazz.getDeclaredField(fieldName);
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
