package shoppyapp.beans;

import java.io.Serializable;

public class CartBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private Long userId;
  private int productId;
  private int quantity;

  public CartBean() {
  }

  public CartBean(Long id, Long userId, int productId, int quantity) {
    this.id = id;
    this.userId = userId;
    this.productId = productId;
    this.quantity = quantity;
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

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "CartBean{" +
            "id=" + id +
            ", userId=" + userId +
            ", productId=" + productId +
            ", quantity=" + quantity +
            '}';
  }

}
