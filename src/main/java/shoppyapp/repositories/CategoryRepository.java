package shoppyapp.repositories;

import jakarta.persistence.EntityManager;
import shoppyapp.entities.Category;
import shoppyapp.util.DbUtil;
import shoppyapp.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository {

  private final EntityManager entityManager = DbUtil.getEntityManager();

    public CategoryRepository() {
    }

    public Optional<Category> findById(int id) {
        try {
            return Optional.of(entityManager.find(Category.class, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Category> findByName(String name) {
        try {
            return Optional.of(entityManager.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class)
                    .setParameter("name", name)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Category> findAll() {
        try {
            return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void save(Category category) {
        try {
            entityManager.getTransaction().begin();
            if(category.getId() == 0) {
                entityManager.persist(category);
            } else {
                entityManager.merge(category);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public boolean delete(Category category) {
      // Retrieve the category from the database
      Category categoryToDelete = entityManager.find(Category.class, category.getId());
      if (categoryToDelete == null) {
          return false;
      }

      // If the category has products, return false
      if (!categoryToDelete.getProducts().isEmpty()) {
//        LoggerUtil.logMessage("Cannot delete category with ID: " + categoryToDelete.getId() + ". It has associated products.");
        return false;
      }

        try {
            entityManager.getTransaction().begin();
            entityManager.remove(category);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public Optional<Category> update(Category category) {

      // If there is a category with the same name and different ID, return empty
      Optional<Category> existingCategory = findByName(category.getName());
      if (existingCategory.isPresent() && existingCategory.get().getId() != category.getId()) {
          entityManager.clear();  // Clear the persistence context to avoid conflicts with the existing category
          return Optional.empty();
      }

      try {
          entityManager.getTransaction().begin();
          Category updatedCategory = entityManager.merge(category);
          entityManager.getTransaction().commit();
          return Optional.of(updatedCategory);
      } catch (Exception e) {
          entityManager.getTransaction().rollback();
          return Optional.empty();
      }
    }

}
