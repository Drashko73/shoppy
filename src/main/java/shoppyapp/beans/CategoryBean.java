package shoppyapp.beans;

import java.io.Serializable;

public class CategoryBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private int id;
  private String name;
  private String description;

  public CategoryBean() {
  }

  public CategoryBean(int id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "CategoryBean{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", description='" + description + '\'' +
      '}';
  }

}
