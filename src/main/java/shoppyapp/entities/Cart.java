package shoppyapp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "quantity", nullable = false)
  private int quantity;

  public Cart() {
  }

  public Cart(User user, Product product, int quantity) {
    this.user = user;
    this.product = product;
    this.quantity = quantity;
  }

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

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "Cart{" +
            "id=" + id +
            ", user=" + user +
            ", product=" + product +
            ", quantity=" + quantity +
            '}';
  }

}
