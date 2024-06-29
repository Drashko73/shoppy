package shoppyapp.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id; // primary key

  @Column(nullable = false, unique = true)
  private String username; // unique username

  @Column(nullable = false)
  private String password; // password

  @Column(nullable = false, unique = true)
  private String email; // unique email

  @Column(nullable = false)
  private String firstName; // first names

  @Column(nullable = false)
  private String lastName; // last name

  @Column(nullable = false)
  private boolean isAdmin; // admin status

  @Column(nullable = false)
  private boolean isBanned = false; // banned status (default false)

  @Column(nullable = true)
  private String sessionID; // session ID

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<Cart> carts;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<Order> orders;

  // Default constructor
  public User() {
  }

  public User(String username, String password,
              String email, String firstName, String lastName,
              boolean isAdmin, boolean isBanned
  ) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.isAdmin = isAdmin;
    this.isBanned = isBanned;
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

  public List<Cart> getCarts() {
    return carts;
  }

  public void setCarts(List<Cart> carts) {
    this.carts = carts;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  @Override
  public String toString() {
    return "User{" +
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
