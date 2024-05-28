package sep.model;

/**
 * Singleton class representing the user session.
 * Manages the currently logged-in user.
 */

public class UserSession {
  private static UserSession instance;
  private Patron loggedInUser;

  /**
   * Private constructor to prevent instantiation.
   */
  private UserSession() {}

  /**
   * Returns the single instance of the UserSession class.
   * If the instance is null, a new instance is created.
   *
   * @return the single instance of the {@code UserSession}
   */
  public static UserSession getInstance() {
    if (instance == null) {
      instance = new UserSession();
    }
    return instance;
  }

  /**
   * Sets the currently logged-in user.
   *
   * @param user the patron to be set as the logged-in user
   */
  public void setLoggedInUser(Patron user) {
    this.loggedInUser = user;
  }

  /**
   * Returns the currently logged-in user.
   *
   * @return the currently logged-in user
   */
  public Patron getLoggedInUser() {
    return loggedInUser;
  }
}

