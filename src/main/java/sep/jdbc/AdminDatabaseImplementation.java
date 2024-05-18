package sep.jdbc;


import java.sql.*;

public class AdminDatabaseImplementation implements AdminDatabaseInterface
{
    private static AdminDatabaseImplementation instance;
    private AdminDatabaseImplementation() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized AdminDatabaseImplementation getInstance() throws SQLException {
        if (instance == null) {
            instance = new AdminDatabaseImplementation();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=book_work_db", "postgres", "343460"); //TODO: YOU NEED TO CHANGE THIS PASSWORD ON WHO IS WORKING ON CODE RN

    }
}
