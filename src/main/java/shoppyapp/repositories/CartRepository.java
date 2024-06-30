package shoppyapp.repositories;

import jakarta.persistence.EntityManager;
import shoppyapp.entities.Cart;
import shoppyapp.util.DbUtil;

import java.util.List;
import java.util.Optional;

public class CartRepository {

  private final EntityManager entityManager = DbUtil.getEntityManager();

  public CartRepository() {
  }

  public Optional<Cart> findById(long id) {
    try {
      return Optional.of(entityManager.find(Cart.class, id));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public List<Cart> findUserCarts(long userId) {
    try {
      return entityManager.createQuery("SELECT c FROM Cart c WHERE c.user.id = :userId", Cart.class)
              .setParameter("userId", userId)
              .getResultList();
    } catch (Exception e) {
      return List.of();
    }
  }

  public void save(Cart cart) {
    try {
      entityManager.getTransaction().begin();
      if(cart.getId() == null) {
        entityManager.persist(cart);
      } else {
        entityManager.merge(cart);
      }
      entityManager.getTransaction().commit();
    } catch (Exception e) {
//      LoggerUtil.logMessage("Failed to save cart");
      if(entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      e.printStackTrace();
    }
  }

  public boolean delete(Cart cart) {
    // Retrieve the cart from the database
    Cart cartToDelete = entityManager.find(Cart.class, cart.getId());
    if (cartToDelete != null) {
      try {
        entityManager.getTransaction().begin();
        entityManager.remove(cartToDelete);
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

  public boolean increaseProductQuantity(int userId, int productId, int quantity) {
    try {
      entityManager.getTransaction().begin();
      entityManager.createQuery("UPDATE Cart c SET c.quantity = c.quantity + :quantity WHERE c.user.id = :userId AND c.product.id = :productId")
              .setParameter("quantity", quantity)
              .setParameter("userId", userId)
              .setParameter("productId", productId)
              .executeUpdate();
      entityManager.getTransaction().commit();
      return true;
    } catch (Exception e) {
      if(entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
    }
    return false;
  }

  public boolean hasProductInCart(int userId, int productId) {
    try {
      return entityManager.createQuery("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.product.id = :productId", Cart.class)
              .setParameter("userId", userId)
              .setParameter("productId", productId)
              .getSingleResult() != null;
    } catch (Exception e) {
      return false;
    }
  }

}
