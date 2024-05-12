package sep.model;

public class UserSession {
  private static UserSession instance;
  private Patron loggedInUser;

  private UserSession() {}

  public static UserSession getInstance() {
    if (instance == null) {
      instance = new UserSession();
    }
    return instance;
  }

  public void setLoggedInUser(Patron user) {
    this.loggedInUser = user;
  }

  public Patron getLoggedInUser() {
    return loggedInUser;
  }
}

