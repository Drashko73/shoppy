package shoppyapp.services;

import shoppyapp.beans.OrderBean;
import shoppyapp.beans.ProductBean;
import shoppyapp.entities.Order;
import shoppyapp.entities.OrderItem;
import shoppyapp.entities.User;
import shoppyapp.repositories.OrderItemRepository;
import shoppyapp.repositories.OrderRepository;
import shoppyapp.repositories.ProductRepository;
import shoppyapp.repositories.UserRepository;
import shoppyapp.util.LoggerUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final UserRepository userRepository;
  private final ProductService productService;

  public OrderService() {
    orderRepository = new OrderRepository();
    orderItemRepository = new OrderItemRepository();
    userRepository = new UserRepository();
    productService = new ProductService();
  }

  public boolean createOrder(List<OrderItem> orderItems, long userId) {

    // Check if the stock is enough
    if (!canCreateOrder(orderItems)) {
      return false;
    }

    // Update the stock
    updateStock(orderItems);

    // Create the order
    Date currentDate = new Date();
    double total = calculateTotal(orderItems);

    // Get the user by its ID
    Optional<User> user = userRepository.findById(userId);
    if(user.isPresent()) {

      Order order = new Order(user.get(), currentDate, orderItems, total);
      orderRepository.addOrder(order);

      return true;
    }

    return false;
  }

  public List<OrderBean> getOrdersByUserId(long userId) {

    // Get the user by its ID
    Optional<User> user = userRepository.findById(userId);
    if(user.isPresent()) {

      // Get the orders by the user
      List<Order> orders = orderRepository.getUserOrders(user.get().getId());

      List<OrderBean> orderBeans = new ArrayList<>();
      for(Order order : orders) {
        orderBeans.add(new OrderBean(order.getId(), user.get().getId(), order.getOrderDate(), order.getTotalPrice()));
      }

      return orderBeans;

    }

    return null;
  }

  private double calculateTotal(List<OrderItem> orderItems) {

      double total = 0;

      // Iterate over the order items to calculate the total
      for (OrderItem orderItem : orderItems) {
        total += orderItem.getProduct().getPrice() * orderItem.getQuantity();
      }

      // Return the total
      return total;
  }

  private void updateStock(List<OrderItem> orderItems) {

    // Iterate over the order items to update the stock
    for (OrderItem orderItem : orderItems) {

      ProductBean productBean = productService.getProductById(orderItem.getProduct().getId());
      boolean updated = productService.updateProductStock(productBean.getId(), productBean.getStock() - orderItem.getQuantity());
      if(!updated) {
        LoggerUtil.logMessage("Failed to update stock for product: " + orderItem.getProduct().getId());
      }
      else {
        LoggerUtil.logMessage("Stock updated for product: " + orderItem.getProduct().getId());
      }
    }
  }

  private boolean canCreateOrder(List<OrderItem> orderItems) {

    boolean canCreateOrder = true;

    // Iterate over the order items to check if the stock is enough
    for (OrderItem orderItem : orderItems) {
      if(!productService.checkStock(orderItem.getProduct().getId(), orderItem.getQuantity())) {
        canCreateOrder = false;
//        LoggerUtil.logMessage("Stock not enough for product: " + orderItem.getProduct().getId() + " with quantity: " + orderItem.getQuantity());
        break;
      }
    }

    // Return the result
    return canCreateOrder;
  }

}
