package shoppyapp.repositories;

import jakarta.persistence.EntityManager;
import shoppyapp.entities.Product;
import shoppyapp.util.DbUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

  private final EntityManager entityManager = DbUtil.getEntityManager();

  public ProductRepository() {
  }

  public Optional<Product> findById(int id) {
    try {
      return Optional.of(entityManager.find(Product.class, id));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public Optional<Product> findByName(String name) {
    try {
      return Optional.of(entityManager.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class)
              .setParameter("name", name)
              .getSingleResult());
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public List<Product> findAll() {
    try {
      return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  public void save(Product product) {
    try {
      entityManager.getTransaction().begin();
      if(product.getId() == 0) {
        entityManager.persist(product);
      } else {
        entityManager.merge(product);
      }
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }

  public boolean delete(Product product) {
    // Retrieve the product from the database
    Product productToDelete = entityManager.find(Product.class, product.getId());
    if(productToDelete != null) {
      try {
        entityManager.getTransaction().begin();
        entityManager.remove(productToDelete);
        entityManager.getTransaction().commit();
        return true;
      } catch (Exception e) {
        entityManager.getTransaction().rollback();
      }
    }
    return false;
  }

  public List<Product> findByCategory(int categoryId) {
    try {
      return entityManager.createQuery("SELECT p FROM Product p WHERE p.category.id = :categoryId", Product.class)
              .setParameter("categoryId", categoryId)
              .getResultList();
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  public boolean updateProduct(Product product) {
    Product productToUpdate = entityManager.find(Product.class, product.getId());
    if (productToUpdate != null) {
      try {
        entityManager.getTransaction().begin();
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setStock(product.getStock());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setImage(product.getImage());
        productToUpdate.setCategory(product.getCategory());
        entityManager.merge(productToUpdate);
        entityManager.getTransaction().commit();
        return true;
      } catch (Exception e) {
        entityManager.getTransaction().rollback();
      }
    }
    entityManager.clear();  // Clear the entity manager to avoid conflicts
    return false;
  }

}
