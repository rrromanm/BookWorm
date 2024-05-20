package sep.jdbc;

import sep.model.Patron;

import java.sql.SQLException;
import java.util.List;

public interface PatronDatabaseInterface
{
  void createPatron(String username, String password, String first_name, String last_name, String email, String phone_number) throws SQLException;
  Patron login(String username, String password) throws SQLException;
  boolean loginAsAdmin(String username, String password) throws SQLException;
  boolean usernameExists(String username) throws SQLException;
  void updateUsername(int userID, String newUsername) throws SQLException;
  void updatePassword(int userID, String newPassword) throws SQLException;
  void updateEmail(int userID, String newEmail) throws SQLException;
  void updatePhone(int userID, String newPhone) throws SQLException;
  void updateFirstName(int userID, String newFirst) throws SQLException;
  void updateLastName(int userID, String newLast) throws SQLException;
  void updateFees(int userID, int newFees) throws SQLException;
  List<Patron> getAllPatrons() throws SQLException;

}
