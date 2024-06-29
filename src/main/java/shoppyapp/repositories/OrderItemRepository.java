package shoppyapp.repositories;

import jakarta.persistence.EntityManager;
import shoppyapp.entities.OrderItem;
import shoppyapp.util.DbUtil;
import shoppyapp.util.LoggerUtil;

import java.util.List;

public class OrderItemRepository {

  private final EntityManager entityManager = DbUtil.getEntityManager();

  public OrderItemRepository() {
  }

  public void addOrderItem(OrderItem orderItem) {
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(orderItem);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      LoggerUtil.logError(e.getMessage());
    }
  }

  public List<OrderItem> getOrderItems(int orderId) {
    return entityManager.createQuery("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId", OrderItem.class)
        .setParameter("orderId", orderId)
        .getResultList();
  }



}
