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
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=book_work_db", "postgres", "via");
    }
    public void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number) throws SQLException {
        try(Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO patrons(first_name, last_name, username, password, email, phone_number) VALUES (?,?,?,?,?,?)");
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, email);
            statement.setString(6, phone_number);
            statement.executeUpdate();
        }
    }

    public boolean login(String username, String password) throws SQLException
    {
        try(Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM patrons WHERE username =? AND password =?;");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        }
    }
}
