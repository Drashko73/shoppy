package shoppyapp.beans;

import java.io.Serializable;
import java.util.Date;

public class OrderBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private Long userId;
  private Date orderDate;
  private double totalPrice;

  public OrderBean() {
  }

  public OrderBean(Long id, Long userId, Date orderDate, double totalPrice) {
    this.id = id;
    this.userId = userId;
    this.orderDate = orderDate;
    this.totalPrice = totalPrice;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  @Override
  public String toString() {
    return "OrderBean{" +
        "id=" + id +
        ", userId=" + userId +
        ", orderDate=" + orderDate +
        ", totalPrice=" + totalPrice +
        '}';
  }

}
