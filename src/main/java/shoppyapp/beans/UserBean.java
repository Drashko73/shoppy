package shoppyapp.beans;

import java.io.Serializable;

public class UserBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private boolean isAdmin;
  private boolean isBanned;
  private String sessionID;

  public UserBean() {
  }

  public UserBean(long id, String username, String password,
                  String email, String firstName, String lastName,
                  boolean isAdmin, boolean isBanned, String sessionID
  ) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.isAdmin = isAdmin;
    this.isBanned = isBanned;
    this.sessionID = sessionID;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public boolean isBanned() {
    return isBanned;
  }

  public void setBanned(boolean banned) {
    isBanned = banned;
  }

  public String getSessionID() {
    return sessionID;
  }

  public void setSessionID(String sessionID) {
    this.sessionID = sessionID;
  }

  @Override
  public String toString() {
    return "UserBean{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", isAdmin=" + isAdmin +
            ", isBanned=" + isBanned +
            ", sessionID='" + sessionID + '\'' +
            '}';
  }
}
