package shoppyapp.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private double price;

  @Column(nullable = false)
  private int stock;

  @Column(nullable = false, length = 10000)
  private String description;

  @Column(nullable = true)
  private String image;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<Cart> carts;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<OrderItem> orderItems;

  // Default constructor
  public Product() {
  }

  // Constructor
  public Product(String name, double price, int stock, String description, String image, Category category) {
    this.name = name;
    this.price = price;
    this.stock = stock;
    this.description = description;
    this.image = image;
    this.category = category;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public List<Cart> getCarts() {
    return carts;
  }

  public void setCarts(List<Cart> carts) {
    this.carts = carts;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  @Override
  public String toString() {
    return "Product{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", price=" + price +
      ", stock=" + stock +
      ", description='" + description + '\'' +
      ", image='" + image + '\'' +
      ", category=" + category +
      '}';
  }

}
