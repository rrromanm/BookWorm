package sep.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatronTest {

    private Patron patron1;
    private Patron patron2;

    @BeforeEach
    void setUp() {
        patron1 = new Patron(1, "John", "Doe", "johndoe", "password123", "john.doe@example.com", "1234567890", 0);
        patron2 = new Patron(2, "Jane", "Smith", "janesmith", "password456", "jane.smith@example.com", "0987654321", 100);
    }

    @Test
    void getIdTest() {
        assertEquals(1, patron1.getUserID());
        assertEquals(2, patron2.getUserID());
    }

    @Test
    void getFirstNameTest() {
        assertEquals("John", patron1.getFirstName());
        assertEquals("Jane", patron2.getFirstName());
    }

    @Test
    void setFirstNameTest() {
        patron1.setFirstName("Johnny");
        assertEquals("Johnny", patron1.getFirstName());
    }

    @Test
    void getLastNameTest() {
        assertEquals("Doe", patron1.getLastName());
        assertEquals("Smith", patron2.getLastName());
    }

    @Test
    void setLastNameTest() {
        patron1.setLastName("Doherty");
        assertEquals("Doherty", patron1.getLastName());
    }

    @Test
    void getUsernameTest() {
        assertEquals("johndoe", patron1.getUsername());
        assertEquals("janesmith", patron2.getUsername());
    }

    @Test
    void setUsernameTest() {
        patron1.setUsername("johnnyd");
        assertEquals("johnnyd", patron1.getUsername());
    }

    @Test
    void getPasswordTest() {
        assertEquals("password123", patron1.getPassword());
        assertEquals("password456", patron2.getPassword());
    }

    @Test
    void setPasswordTest() {
        patron1.setPassword("newpassword");
        assertEquals("newpassword", patron1.getPassword());
    }

    @Test
    void getEmailTest() {
        assertEquals("john.doe@example.com", patron1.getEmail());
        assertEquals("jane.smith@example.com", patron2.getEmail());
    }

    @Test
    void setEmailTest() {
        patron1.setEmail("johnny.d@example.com");
        assertEquals("johnny.d@example.com", patron1.getEmail());
    }

    @Test
    void getPhoneNumberTest() {
        assertEquals("1234567890", patron1.getPhoneNumber());
        assertEquals("0987654321", patron2.getPhoneNumber());
    }

    @Test
    void setPhoneNumberTest() {
        patron1.setPhoneNumber("1122334455");
        assertEquals("1122334455", patron1.getPhoneNumber());
    }

    @Test
    void getFeesTest() {
        assertEquals(0, patron1.getFees());
        assertEquals(100, patron2.getFees());
    }

    @Test
    void setFeesTest() {
        patron1.setFees(50);
        assertEquals(50, patron1.getFees());
    }

    @Test
    void idTest()
    {
        patron1.setUserID(1);
        assertEquals(1, patron1.getUserID());
    }

    @Test
    void equalsTest() {
        Patron anotherPatron1 = new Patron(1, "Jim", "Beam", "jimbeam", "whiskey", "jim.beam@example.com", "5678901234", 200);
        assertEquals(patron1, anotherPatron1);
    }

    @Test
    void notEqualsTest() {
        assertNotEquals(patron1, patron2);
    }

    @Test
    void equalsNullTest()
    {
        assertNotEquals(patron1, null);
    }

    @Test
    void equalsAnotherObjectTest()
    {
        assertNotEquals(patron1,  new Object());
    }

    @Test
    void equalsWithItselfTest()
    {
        assertEquals(patron1, patron1);
    }

    @Test
    void toStringTest() {
        String expectedPatron1 = "User{firstName='John', lastName='Doe', username='johndoe', password='password123', email='john.doe@example.com', phoneNumber='1234567890', fees=0', userID=1}";
        String expectedPatron2 = "User{firstName='Jane', lastName='Smith', username='janesmith', password='password456', email='jane.smith@example.com', phoneNumber='0987654321', fees=100', userID=2}";
        assertEquals(expectedPatron1, patron1.toString());
        assertEquals(expectedPatron2, patron2.toString());
    }
}
