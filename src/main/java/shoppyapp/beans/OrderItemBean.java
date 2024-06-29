package shoppyapp.beans;

import java.io.Serializable;

public class OrderItemBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private Long productId;
  private int quantity;
  private double price;

  public OrderItemBean() {
  }

  public OrderItemBean(Long id, Long productId, int quantity, double price) {
    this.id = id;
    this.productId = productId;
    this.quantity = quantity;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "OrderItemBean{" +
        "id=" + id +
        ", productId=" + productId +
        ", quantity=" + quantity +
        ", price=" + price +
        '}';
  }

}
