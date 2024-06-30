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

      entityManager.clear();  // Clear the entity manager to avoid conflicts

    } catch (Exception e) {
      if(entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
    }
  }

  public boolean create(Product product) {
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(product);
      entityManager.getTransaction().commit();
      return true;
    } catch (Exception e) {
      if(entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      return false;
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
        if(entityManager.getTransaction().isActive()) {
          entityManager.getTransaction().rollback();
        }
      }
    }
    return false;
  }

  public boolean update(Product product) {
    try {
      entityManager.getTransaction().begin();
      entityManager.merge(product);
      entityManager.getTransaction().commit();
      return true;
    } catch (Exception e) {
      if(entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      return false;
    }
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

}
