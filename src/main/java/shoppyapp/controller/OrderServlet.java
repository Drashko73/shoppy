package shoppyapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shoppyapp.beans.CartBean;
import shoppyapp.beans.ProductBean;
import shoppyapp.entities.OrderItem;
import shoppyapp.entities.Product;
import shoppyapp.repositories.ProductRepository;
import shoppyapp.services.CartService;
import shoppyapp.services.OrderService;
import shoppyapp.services.ProductService;
import shoppyapp.util.LoggerUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "OrderServlet", value = "/order_servlet")
public class OrderServlet extends HttpServlet {

  private final OrderService orderService = new OrderService();
  private final CartService cartService = new CartService();
  private final ProductRepository productRepository = new ProductRepository();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");

    if (action != null && action.equals("add_order")) {

      // Get the user ID from the request
      long userId = Long.parseLong(request.getParameter("user_id"));

      // Get the cart items
      List<CartBean> cartItems = cartService.getCart(userId);

      // Convert the cart items to order items
      List<OrderItem> orderItems = new ArrayList<>();
      for (CartBean cartItem : cartItems) {

        Optional<Product> productOptional = productRepository.findById(cartItem.getProductId());
        if (productOptional.isPresent()) {
          Product product = productOptional.get();
          OrderItem orderItem = new OrderItem(product, cartItem.getQuantity(), cartItem.getQuantity() * product.getPrice(), null);
          orderItems.add(orderItem);
        }
      }

      // Try to create the order
      boolean created = orderService.createOrder(orderItems, userId);

//      LoggerUtil.logMessage("Order created: " + created);

      if (created) {

        // Clear the cart
        cartService.clearCart(userId);

        request.setAttribute("success", "Order created successfully");
        response.sendRedirect("orders.jsp");
      } else {
        request.setAttribute("error", "Failed to create order");
        response.sendRedirect("index.jsp");
      }

    }
  }

}
