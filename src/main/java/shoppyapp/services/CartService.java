package shoppyapp.services;

import shoppyapp.beans.CartBean;
import shoppyapp.entities.Cart;
import shoppyapp.entities.Product;
import shoppyapp.entities.User;
import shoppyapp.repositories.CartRepository;
import shoppyapp.repositories.ProductRepository;
import shoppyapp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartService {

  private final CartRepository cartRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  public CartService() {
    this.cartRepository = new CartRepository();
    this.userRepository = new UserRepository();
    this.productRepository = new ProductRepository();
  }

  // Add a new cart
  public boolean addCart(int userId, int productId, int quantity) {
    // Check if the cart already exists
    if(cartRepository.hasProductInCart(userId, productId)) {
      // Increase the quantity of the product in the cart
      return cartRepository.increaseProductQuantity(userId, productId, quantity);
    }
    else {
      // Create a new cart
      Optional<User> userOptional = userRepository.findById(userId);
      if(userOptional.isEmpty()) {
        return false;
      }

      Optional<Product> productOptional = productRepository.findById(productId);
      if(productOptional.isEmpty()) {
        return false;
      }

      if(productOptional.get().getStock() < quantity) {
        return false;
      }

      Cart cart = new Cart(userOptional.get(), productOptional.get(), quantity);
      cartRepository.save(cart);
      return true;
    }
  }

  // Get items in the cart
  public List<CartBean> getCart(long userId) {
    List<Cart> carts = cartRepository.findUserCarts(userId);
    List<CartBean> cartBeans = new ArrayList<>();

    for(Cart cart : carts) {
      CartBean cartBean = new CartBean(
        cart.getId(),
        cart.getUser().getId(),
        cart.getProduct().getId(),
        cart.getQuantity()
      );
      cartBeans.add(cartBean);
    }

    return cartBeans;
  }

  // Remove an item from the cart
  public boolean removeCart(long cartId) {
    Optional<Cart> cartOptional = cartRepository.findById(cartId);
    if(cartOptional.isEmpty()) {
      return false;
    }

    cartRepository.delete(cartOptional.get());
    return true;
  }

  // Remove all items from the cart
  public void clearCart(long userId) {
    List<Cart> carts = cartRepository.findUserCarts(userId);
    for(Cart cart : carts) {
      cartRepository.delete(cart);
    }
  }

}
