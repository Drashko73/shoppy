package shoppyapp.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Temporal(TemporalType.TIMESTAMP)
  private Date orderDate;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "order_id")
  private List<OrderItem> orderItems;

  private double totalPrice;

  // Default constructor
  public Order() {
  }

  // Constructor
  public Order(User user, Date orderDate, List<OrderItem> orderItems, double totalPrice) {
    this.user = user;
    this.orderDate = orderDate;
    this.orderItems = orderItems;
    this.totalPrice = totalPrice;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  @Override
  public String toString() {
    return "Order{" +
      "id=" + id +
      ", user=" + user +
      ", orderDate=" + orderDate +
      ", orderItems=" + orderItems +
      ", totalPrice=" + totalPrice +
      '}';
  }

}