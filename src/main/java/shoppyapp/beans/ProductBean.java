package shoppyapp.beans;

import java.io.Serializable;

public class ProductBean implements Serializable {

  private static final long serialVersionUID = 1L;  private int id;

  private String name;
  private double price;
  private int stock;
  private String description;
  private String image;
  private int categoryId;

  public ProductBean() {
  }

  public ProductBean(int id, String name, double price, int stock, String description, String image, int categoryId) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.stock = stock;
    this.description = description;
    this.image = image;
    this.categoryId = categoryId;
  }

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

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  @Override
  public String toString() {
    return "ProductBean{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", stock=" + stock +
            ", description='" + description + '\'' +
            ", image='" + image + '\'' +
            ", categoryId=" + categoryId +
            '}';
  }

}
