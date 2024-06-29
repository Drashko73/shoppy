package shoppyapp.repositories;

import jakarta.persistence.EntityManager;
import shoppyapp.entities.Order;
import shoppyapp.util.DbUtil;
import shoppyapp.util.LoggerUtil;

import java.util.List;

public class OrderRepository {

  private final EntityManager entityManager = DbUtil.getEntityManager();

  public OrderRepository() {
  }

  public List<Order> getUserOrders(long userId) {
    return entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId", Order.class)
        .setParameter("userId", userId)
        .getResultList();
  }

  public void addOrder(Order order) {
    try {
      if (!entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().begin();
      }
      if(order.getId() == null) {
        entityManager.persist(order);
      } else {
        entityManager.merge(order);
      }
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      LoggerUtil.logError(e.getMessage());
    }
  }

}
