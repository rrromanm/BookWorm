package sep.jdbc;

import sep.model.Patron;
import sep.model.validators.PasswordValidator;

import java.sql.*;


public class PatronDatabaseImplementation {
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
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=book_work_db", "postgres", "VIAVIA");
    }
    //TODO: Implement checking if given username already exists. Can use method I used to update account details.
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number) throws SQLException {
        try(Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO book_worm_db.patron(first_name, last_name, username, password, email, phone_number) VALUES (?,?,?,?,?,?)");
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, email);
            statement.setString(6, phone_number);
            statement.executeUpdate();
        }
    }

    public boolean login(String username, String password) throws SQLException {
        try(Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM book_worm_db.patron WHERE username = ? AND password = ?;");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            System.out.println("Checking for user in db....");
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


}
