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
  void updateUsername(String oldUsername, String newUsername) throws SQLException;
  void updatePassword(String oldPassword, String newPassword) throws SQLException;
  void updateEmail(String oldEmail, String newEmail) throws SQLException;
  void updatePhone(String oldPhone, String newPhone) throws SQLException;
  void updateFirstName(String oldFirst, String newFirst) throws SQLException;
  void updateLastName(String oldLast, String newLast) throws SQLException;
  List<Patron> getAllPatrons() throws SQLException;

}
