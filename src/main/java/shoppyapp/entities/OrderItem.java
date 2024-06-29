package shoppyapp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;

  private int quantity;
  private double price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private Order order;

  // Default constructor
  public OrderItem() {
  }

  // Constructor
  public OrderItem(Product product, int quantity, double price, Order order) {
    this.product = product;
    this.quantity = quantity;
    this.price = price;
    this.order = order;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "id=" + id +
        ", product=" + product +
        ", quantity=" + quantity +
        ", price=" + price +
        ", order=" + order +
        '}';
  }

}