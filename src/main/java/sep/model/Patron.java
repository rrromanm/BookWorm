package sep.model;

import java.io.Serializable;
/**
 * Represents a patron with id, first name, last name, username, password, phone number and fees.
 * Implements Serializable interface to allow the object to be serialized.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class Patron implements Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private int userId;
    private int fees;

    /**
     * Constructs a new Patron with the specified details.
     *
     * @param userId      the unique identifier for the patron
     * @param firstName   the first name of the patron
     * @param lastName    the last name of the patron
     * @param username    the username of the patron
     * @param password    the password of the patron
     * @param email       the email address of the patron
     * @param phoneNumber the phone number of the patron
     * @param fees        the fees owed by the patron
     */
    public Patron(int userId, String firstName, String lastName, String username, String password, String email, String phoneNumber, int fees) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fees = fees;
    }
//    public Patron(String firstName, String lastName, String username, String password, String email, String phoneNumber, int fees) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.fees = fees;
//    }

    /**
     * Returns the first name of the patron.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the patron.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the patron.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the patron.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the username of the patron.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the patron.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the patron.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the patron.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the email of the patron.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the patron.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the patron.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the id of the patron.
     *
     * @return the user id
     */
    public int getUserID() {
        return userId;
    }

    /**
     * Sets the id of the patron.
     *
     * @param userID the user id to set
     */
    public void setUserID(int userID) {
        this.userId = userID;
    }

    /**
     * Returns the fees of the patron.
     *
     * @return the fees
     */
    public int getFees() {
        return fees;
    }

    /**
     * Sets the fees of the patron.
     *
     * @param fees the fees to set
     */
    public void setFees(int fees) {
        this.fees = fees;
    }

    /**
     * Compares this patron to the specified object. The result is true if and only if the argument is not null and is a Patron object that has the same user ID as this object.
     *
     * @param obj the object to compare this Patron against
     * @return true if the given object represents a Patron equivalent to this Patron, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Patron patron = (Patron) obj;
        return this.userId == patron.userId;
    }

    /**
     * Returns first name, last name, username, password, email, phone number, id, fees of the Patron.
     *
     * @return a first name, last name, username, password, email, phone number, id, fees of the Patron
     */
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber + '\'' +
                ", fees=" + fees + '\'' +
                ", userID=" + userId +
                '}';
    }
}
