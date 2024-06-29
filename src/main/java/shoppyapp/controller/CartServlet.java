package shoppyapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shoppyapp.services.CartService;

import java.io.IOException;

@WebServlet(name = "CartServlet", value = "/cart_servlet")
public class CartServlet extends HttpServlet {

  private final CartService cartService = new CartService();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    String action = request.getParameter("action");

    if(action != null && action.equals("add"))  {

      // Get the user ID, product ID, and quantity from the request
      int userId = Integer.parseInt(request.getParameter("user_id"));
      int productId = Integer.parseInt(request.getParameter("product_id"));
      int quantity = Integer.parseInt(request.getParameter("quantity"));

      // Try to add the product to the cart
      boolean added = cartService.addCart(userId, productId, quantity);

      // Redirect to the product page if the product was added successfully
      if(added) {
        request.setAttribute("success", "Product added to cart successfully");
        request.getRequestDispatcher("product_details.jsp?product_id=" + productId).forward(request, response);
        //response.sendRedirect("product_details.jsp?product_id=" + productId);
      }
      else {
        request.setAttribute("error", "Failed to add product to cart");
        request.getRequestDispatcher("product_details.jsp?product_id=" + productId).forward(request, response);
        //response.sendRedirect("product_details.jsp?product_id=" + productId);
      }

    }
    else if(action != null && action.equals("remove")) {

      // Get the cart_id
      long cartId = Long.parseLong(request.getParameter("cart_id"));

      // Try to remove the product from the cart
      boolean removed = cartService.removeCart(cartId);

      // Redirect to the cart page if the product was removed successfully
      if(removed) {
        request.setAttribute("success", "Product removed from cart successfully");
        response.sendRedirect("cart.jsp");
      }
      else {
        request.setAttribute("error", "Failed to remove product from cart");
        response.sendRedirect("cart.jsp");
      }
    }
    else {
      // Other actions
    }

  }

}
