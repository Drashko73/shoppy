package shoppyapp.services;

import shoppyapp.beans.ProductBean;
import shoppyapp.config.Common;
import shoppyapp.entities.Category;
import shoppyapp.entities.Product;
import shoppyapp.repositories.CategoryRepository;
import shoppyapp.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {

  private final ProductRepository productRepository;
  private static final int ITEMS_PER_PAGE = Common.NUMBER_OF_PRODUCTS_TO_SHOW;

  public ProductService() {
    this.productRepository = new ProductRepository();
  }

  // Add a new product
  public boolean addProduct(String name, double price, int stock, String description, String imageName, int categoryId) {
    // Fetch the category by its ID
    Optional<Category> categoryOptional = new CategoryRepository().findById(categoryId);

    // Check if the category is present
    if (categoryOptional.isPresent()) {
      // Get the category from the Optional
      Category category = categoryOptional.get();

      // Check if product with the same name already exists
      Optional<Product> productOptional = productRepository.findByName(name);

      // Check if the product is present
      if (productOptional.isPresent()) {
        // Log an error message
//        LoggerUtil.logMessage("Product already exists");
        return false;
      } else {
        Product product = new Product(name, price, stock, description, imageName, category);

        return productRepository.create(product);
      }

    } else {
      // Log an error message
//      LoggerUtil.logMessage("Category not found");
      return false;
    }
  }

  // Update a product
  public boolean updateProduct(int id, String name, double price, int stock, String description, String image, int categoryId) {

    // Fetch the category by its ID
    Optional<Category> categoryOptional = new CategoryRepository().findById(categoryId);

    // Check if the category is present
    if (categoryOptional.isPresent()) {

      // Get the category from the Optional
      Category category = categoryOptional.get();

      // Fetch the product by its ID
      Optional<Product> productOptional = productRepository.findById(id);

      // Check if the product is present
      if (productOptional.isPresent()) {

        // Get the product from the Optional
        Product product = productOptional.get();

        // Check if product with the same name already exists
        Optional<Product> existingProduct = productRepository.findByName(name);

        // If the product already exists, and it is not the same as the product being updated, return false
        if (existingProduct.isPresent() && existingProduct.get().getId() != id) {
          // Log an error message
//          LoggerUtil.logMessage("Product already exists");
          return false;
        }

        // Check the stock of the product
        if (stock < 0) {
          // Log an error message
//          LoggerUtil.logMessage("Stock cannot be negative");
          return false;
        }

        // Update the product with the new details
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setDescription(description);
        if (image == null && product.getImage() != null) {
          image = product.getImage();
        }
        product.setImage(image);
        product.setCategory(category);


        return productRepository.update(product);
      } else {
        // Log an error message
//        LoggerUtil.logMessage("Product not found");
        return false;
      }

    } else {
      // Log an error message
//      LoggerUtil.logMessage("Category not found");
      return false;
    }

  }

  // Delete a product
  public boolean deleteProduct(int id) {
    // Fetch the product by its ID
    Optional<Product> productOptional = productRepository.findById(id);

    // Check if the product is present
    if (productOptional.isPresent()) {
      // Get the product from the Optional
      Product product = productOptional.get();

      // Delete the product
      boolean deleted = productRepository.delete(product);
      if (deleted) {
        // Log a message if the product is deleted successfully
//        LoggerUtil.logMessage("Product deleted with ID: " + id);
      }
      return deleted;
    } else {
      // Log a message if the product is not present
//      LoggerUtil.logMessage("Product not found with ID: " + id);
      return false;
    }
  }

  // Get all products
  public List<ProductBean> getAllProducts() {
    List<Product> products = productRepository.findAll();
    List<ProductBean> productBeans = new ArrayList<>();
    for (Product product : products) {
      productBeans.add(new ProductBean(
              product.getId(),
              product.getName(),
              product.getPrice(),
              product.getStock(),
              product.getDescription(),
              product.getImage(),
              product.getCategory().getId())
      );
    }
    return productBeans;
  }

  // Get a product by its ID
  public ProductBean getProductById(int id) {
    // Fetch the product by its ID
    Optional<Product> productOptional = productRepository.findById(id);

    // Check if the product is present
    if (productOptional.isPresent()) {
      // Get the product from the Optional
      Product product = productOptional.get();
      return new ProductBean(
              product.getId(),
              product.getName(),
              product.getPrice(),
              product.getStock(),
              product.getDescription(),
              product.getImage(),
              product.getCategory().getId()
      );
    } else {
      // Log an error message
//      LoggerUtil.logMessage("Product not found with ID: " + id);
      return null;
    }
  }

  public boolean updateProductStock(int id, int stock) {
    // Fetch the product by its ID
    Optional<Product> productOptional = productRepository.findById(id);

    // Check if the product is present
    if (productOptional.isPresent()) {
      // Get the product from the Optional
      Product product = productOptional.get();

      // Check if the stock is negative
      if (stock < 0) {
        // Log an error message
//        LoggerUtil.logMessage("Stock cannot be negative");
        return false;
      }

      // Update the stock of the product
      product.setStock(stock);

      return productRepository.update(product);
    } else {
      // Log an error message
//      LoggerUtil.logMessage("Product not found with ID: " + id);
      return false;
    }
  }

  public boolean checkStock(int id, int quantity) {
    // Fetch the product by its ID
    Optional<Product> productOptional = productRepository.findById(id);

    // Check if the product is present
    if (productOptional.isPresent()) {
//      LoggerUtil.logMessage("Product found with ID: " + id);

      // Get the product from the Optional
      Product product = productOptional.get();

      // Check if the stock is enough
      if (product.getStock() < quantity) {
        // Log an error message
//        LoggerUtil.logMessage("Stock not enough");
        return false;
      }
//      LoggerUtil.logMessage("Stock enough");

      return true;
    } else {
      // Log an error message
//      LoggerUtil.logMessage("Product not found with ID: " + id);
      return false;
    }
  }

  public int getTotalPagesByCategory(int categoryId) {
    int totalProducts = 0;
    if(categoryId == 0){
      totalProducts = productRepository.findAll().size();
    }
    else{
      totalProducts = productRepository.findByCategory(categoryId).size();
    }
    return (int) Math.ceil((double) totalProducts / ITEMS_PER_PAGE);
  }

  public List<ProductBean> getProductsByPage(int page, int categoryId) {

    List<Product> products = null;
    if(categoryId == 0){
      products = productRepository.findAll();
    }
    else{
      products = productRepository.findByCategory(categoryId);
    }

    List<ProductBean> productBeans = new ArrayList<>();
    for (int i = (page - 1) * ITEMS_PER_PAGE; i < Math.min(page * ITEMS_PER_PAGE, products.size()); i++) {
      Product p = products.get(i);
      productBeans.add(new ProductBean(
              p.getId(),
              p.getName(),
              p.getPrice(),
              p.getStock(),
              p.getDescription(),
              p.getImage(),
              p.getCategory().getId())
      );
    }

    return productBeans;

  }


}
