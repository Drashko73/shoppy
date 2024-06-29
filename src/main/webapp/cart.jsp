<%@ page import="shoppyapp.beans.UserBean" %>
<%@ page import="shoppyapp.services.CartService" %>
<%@ page import="java.util.List" %>
<%@ page import="shoppyapp.beans.CartBean" %>
<%@ page import="shoppyapp.services.ProductService" %>
<%@ page import="shoppyapp.beans.ProductBean" %><%--
  Created by IntelliJ IDEA.
  User: radov
  Date: 27-Jun-24
  Time: 11:29 PM
  To change this template use File | Settings | File Templates.
--%>

<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shoppy</title>

    <meta name="author" content="Radovan Draskovic">
    <meta name="description" content="Shoppy - Online Shop">
    <meta name="keywords" content="Shoppy, Online Shop, E-Commerce, Shopping, Products, Categories, Orders, Customers">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="icon" type="image/png" href="assets/favicon.ico">

    <link rel="stylesheet" href="styles/cart.css">
    <link rel="stylesheet" href="styles/navbar.css">

    <script src="scripts/account-modal.js" defer></script>

</head>
<body>

<%@ include file="navbar.jsp"%>

<div id="cart-content">

<%-- Display all products in the cart --%>
<%-- If cart is empty, display message --%>

<%
    CartService cartService = new CartService();
    ProductService productService = new ProductService();

    List<CartBean> cart = cartService.getCart(((UserBean) session.getAttribute("user")).getId());

    if (cart.isEmpty()) {
%>
    <div id="empty-cart">
        <h1>Your cart is empty</h1>
        <img src="assets/empty-cart.png" alt="Empty Cart" draggable="false">
    </div>
<%
    }
    else {
%>
    <div class="cart-container">
        <h1>Your cart</h1>
        <table class="cart-table">
            <tr>
                <th>Product</th>
                <th class="text-align-center">Quantity</th>
                <th class="text-align-center">Price</th>
                <th class="text-align-center">Remove</th>
            </tr>
            <%
                double totalPrice = 0;
                for (CartBean cartBean : cart) {
                    ProductBean productBean = productService.getProductById(cartBean.getProductId());
                    totalPrice += cartBean.getQuantity() * productBean.getPrice();
            %>
            <tr>
                <td>
                    <div class="product-image-and-name">
                        <div class="product-image">
                            <%
                                if (productBean.getImage() != null) {
                            %>
                            <img src="product_servlet?action=image&name=<%= productBean.getImage() %>" alt="Product Image" width="100px" height="100px" draggable="false">
                            <%
                            } else {
                            %>
                            <img src="assets/default-placeholder-product.png" alt="Product Image" width="100px" height="100px" draggable="false">
                            <%
                                }
                            %>
                        </div>
                        <div class="product-name"><%= productBean.getName() %></div>
                    </div>
                </td>
                <td class="text-align-center"><%= cartBean.getQuantity() %></td>
                <td class="text-align-center">$<%= String.format("%.2f", cartBean.getQuantity() * productBean.getPrice()) %></td>
                <td class="text-align-center">
                    <form action="cart_servlet" method="post">
                        <input type="hidden" name="cart_id" value="<%= cartBean.getId() %>">
                        <input type="hidden" name="action" value="remove">
                        <button type="submit" class="remove-button" onclick="return confirm('Are you sure you want to remove this product from the cart?')">Remove</button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>

        <div id="total-price">
            <h2>Total Price: $<%= totalPrice %></h2>
        </div>

        <div id="checkout-button">
            <form action="order_servlet" method="post">
                <input type="hidden" name="action" value="add_order">
                <input type="hidden" name="user_id" value="<%= ((UserBean) session.getAttribute("user")).getId() %>">
                <button type="submit">Make Purchase</button>
            </form>
        </div>

    </div>
    <%
    }
%>

</div>

</body>
</html>
