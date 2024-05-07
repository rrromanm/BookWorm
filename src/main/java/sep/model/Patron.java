package sep.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Patron implements Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private String password; //TODO make password validator
    private String email;
    private long phoneNumber;
    private int userID;

    public Patron(String firstName, String lastName, String username, String password, String email, long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        generateId();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void generateId() // Idk if this right approach. I tested it the ID is unique but the number is always negative for some reason. It might cause some troubles so we need to discuss it.
    {
        UUID uuid = UUID.randomUUID();
        userID = uuid.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patron patron = (Patron) o;
        return phoneNumber == patron.phoneNumber && userID == patron.userID && Objects.equals(firstName, patron.firstName) && Objects.equals(lastName, patron.lastName) && Objects.equals(username, patron.username) && Objects.equals(password, patron.password) && Objects.equals(email, patron.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, password, email, phoneNumber, userID);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", userID=" + userID +
                '}';
    }
}
