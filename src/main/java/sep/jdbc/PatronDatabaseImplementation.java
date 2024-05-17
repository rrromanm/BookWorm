package sep.jdbc;

import sep.model.Patron;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PatronDatabaseImplementation implements PatronDatabaseInterface {
    private static PatronDatabaseImplementation instance;


    private PatronDatabaseImplementation() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }
    public static synchronized PatronDatabaseImplementation getInstance() throws SQLException {
        if (instance == null) {
            instance = new PatronDatabaseImplementation();
        }
        return instance;
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=book_work_db", "postgres", "343460");
    }

    //TODO: Implement checking if given username already exists. Can use method I used to update account details.
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number) throws SQLException {  // the fees are missing and therefore the patron cant be created
        try(Connection conn = getConnection()) {
            if (usernameExists(username)) {
                throw new SQLException("This username already exists: " + username);
            }
            if (emailExists(email)) {
                throw new SQLException("This email is already registered: " + email);
            }
            if (phoneExists(phone_number)) {
                throw new SQLException("This phone number is already registered: " + phone_number);
            }
            PreparedStatement statement = conn.prepareStatement("INSERT INTO book_worm_db.patron(first_name, last_name, username, password, email, phone_number, fees) VALUES (?,?,?,?,?,?,?)");
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, email);
            statement.setString(6, phone_number);
            statement.setInt(7,0);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error creating patron: " + e.getMessage());
        }
    }


    public boolean usernameExists(String username) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM book_worm_db.patron WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

    private boolean emailExists(String email) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM book_worm_db.patron WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

    private boolean phoneExists(String phone_number) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM book_worm_db.patron WHERE phone_number = ?");
            statement.setString(1, phone_number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }


    public Patron login(String username, String password) throws SQLException {
        try(Connection conn = getConnection()) {

            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM book_worm_db.patron WHERE username = ? AND password = ?;");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Checking for user in db....");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 1) {
                    PreparedStatement statement2 = conn.prepareStatement("SELECT * FROM book_worm_db.patron WHERE username = ? AND password = ?;"); // creating the new patron object in order to pass it to the next GUI windows
                    statement2.setString(1, username);
                    statement2.setString(2, password);
                    ResultSet resultSet2 = statement2.executeQuery();
                    Patron user = null;
                    while(resultSet2.next()){
                        int userID = resultSet2.getInt("id");
                        String username2 = resultSet2.getString("username");
                        String password2 = resultSet2.getString("password");
                        String firstname = resultSet2.getString("first_name");
                        String lastname = resultSet2.getString("last_name");
                        String email = resultSet2.getString("email");
                        String phoneNumber = resultSet2.getString("phone_number");
                        int fees = resultSet2.getInt("fees");
                        System.out.println("Logging in successful!");
                        user = new Patron(userID,firstname,lastname,username2,password2,email,phoneNumber,fees);
                    }
                    return user;

                } else {
                    System.out.println("No such user found or multiple entries found.");
                    return null;
                }
            } else {
                System.out.println("Logging in failed!");
                return null;
            }
        }
    }

    public boolean loginAsAdmin(String username, String password) throws SQLException {
        try(Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM book_worm_db.librarian WHERE username = ? AND password = ?;");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            System.out.println("Checking for admin in db....");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 1) {
                    System.out.println("Logging in successful!");
                    return true;
                } else {
                    System.out.println("No such user found or multiple entries found.");
                    return false;
                }
            } else {
                System.out.println("Logging in failed!");
                return false;
            }
        }
    }


    public void updateUsername(String oldUsername, String newUsername) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM book_worm_db.patron WHERE username = ?");
            checkStmt.setString(1, newUsername);
            ResultSet rs = checkStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            if (count > 0) {
                throw new SQLException("Username already exists.");
            } else {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE book_worm_db.patron SET username = ? WHERE username = ?");
                updateStmt.setString(1, newUsername);
                updateStmt.setString(2, oldUsername);
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("No user found with the username: " + oldUsername);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void updatePassword(String oldPassword, String newPassword) throws SQLException {
        try (Connection conn = getConnection()) {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE book_worm_db.patron SET password = ? WHERE password = ?");
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, oldPassword);
                updateStmt.executeUpdate();
        }
    }

    public void updateEmail(String oldEmail, String newEmail) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM book_worm_db.patron WHERE email = ?");
            checkStmt.setString(1, newEmail);
            ResultSet rs = checkStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            if (count > 0) {
                throw new SQLException("Email is already registered to different account.");
            } else {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE book_worm_db.patron SET email = ? WHERE email = ?");
                updateStmt.setString(1, newEmail);
                updateStmt.setString(2, oldEmail);
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("No user found with the email: " + oldEmail);
                }
            }
        }
    }
    public void updatePhone(String oldPhone, String newPhone) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM book_worm_db.patron WHERE phone_number = ?");
            checkStmt.setString(1, newPhone);
            ResultSet rs = checkStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            if (count > 0) {
                throw new SQLException("Phone number is already registered to different account.");
            } else {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE book_worm_db.patron SET phone_number = ? WHERE phone_number = ?");
                updateStmt.setString(1, newPhone);
                updateStmt.setString(2, oldPhone);
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("No user found with the phone number: " + oldPhone);
                }
            }
        }
    }

    public void updateFirstName(String oldFirst, String newFirst) throws SQLException{
        try(Connection conn = getConnection()) {
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE book_worm_db.patron SET first_name =? WHERE first_name = ?");
            updateStmt.setString(1, newFirst);
            updateStmt.setString(2, oldFirst);
            updateStmt.executeUpdate();
        }
    }
    public void updateLastName(String oldLast, String newLast) throws SQLException{
        try(Connection conn = getConnection()) {
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE book_worm_db.patron SET last_name =? WHERE last_name = ?");
            updateStmt.setString(1, newLast);
            updateStmt.setString(2, oldLast);
            updateStmt.executeUpdate();
        }
    }

    public List<Patron> getAllPatrons() throws SQLException {
        List<Patron> patrons = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM book_worm_db.patron");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userID = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                int fees = resultSet.getInt("fees");
                Patron patron = new Patron(userID, firstName, lastName, username, null, email, phoneNumber, fees);
                patrons.add(patron);
            }
        }
        return patrons;
    }

}
