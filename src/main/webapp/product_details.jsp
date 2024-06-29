<%@ page import="shoppyapp.beans.UserBean" %>
<%@ page import="shoppyapp.beans.ProductBean" %>
<%@ page import="shoppyapp.services.ProductService" %>
<%@ page import="shoppyapp.util.LoggerUtil" %><%--
  Created by IntelliJ IDEA.
  User: radov
  Date: 27-Jun-24
  Time: 6:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ProductBean product = null;
    ProductService productService = new ProductService();

    if (request.getParameter("product_id") != null) {
        int product_id = Integer.parseInt(request.getParameter("product_id"));
//        LoggerUtil.logMessage("Showing details for product with ID: " + product_id);
        product = productService.getProductById(product_id);
    }

    if(product == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<html>
<head>
    <title>Shoppy</title>

    <meta name="author" content="Radovan Draskovic">
    <meta name="description" content="Shoppy - Online Shop">
    <meta name="keywords" content="Shoppy, Online Shop, E-Commerce, Shopping, Products, Categories, Orders, Customers">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="icon" type="image/png" href="assets/favicon.ico">

    <link rel="stylesheet" type="text/css" href="styles/product_details.css">

    <script src="scripts/account-modal.js" defer></script>

</head>
<body>

<nav id="navbar">
    <!-- Navigation bar content -->
    <!-- logo, links, cart, login, register -->
    <div id="logo-and-links">
        <div id="logo">
            <a href="index.jsp">
                <img src="assets/logo.jpg" alt="Shoppy Logo" width="100px" height="30px" style="display: block">
            </a>
        </div>

        <div id="links">
            <!-- If user is admin, display Categories and Products link -->
            <!-- If user is not admin, do not display Categories link -->
            <%
                if (session.getAttribute("user") != null) {
                    if (((UserBean) session.getAttribute("user")).isAdmin()) {
            %>
            <a href="products.jsp">Products</a>
            <a href="categories.jsp">Categories</a>
            <a href="chat_rooms.jsp">Manage Chat Rooms</a>
            <%
                    }
                    %>
                    <a href="orders.jsp">Orders</a>
                    <a href="cart.jsp">Cart</a>
                    <a href="chat.jsp">Chat Rooms</a>
                    <%
                }
            %>
            <a href="help.jsp">Help</a>
        </div>
    </div>

    <div id="account-management">
        <!-- If user is logged in, display his full name -->
        <!-- If user is not logged in, display Login/Register link -->

        <%
            if (session.getAttribute("user") != null) {
        %>
        <div id="login">
              <span onclick="openModal()" style="cursor: pointer">
                <%= ((UserBean) session.getAttribute("user")).getFirstName() + " " + ((UserBean) session.getAttribute("user")).getLastName() %>
              </span>
        </div>
        <%
        }
        else {
        %>
        <div id="login">
            <a href="login.jsp">Login/Register</a>
        </div>
        <%
            }
        %>
    </div>

    <div id="userModal" class="modal">
        <div class="modal-content">

            <h2>Account Info</h2>
            <%
                if (session.getAttribute("user") != null) {
            %>
            <p>First Name: <%= ((UserBean) session.getAttribute("user")).getFirstName()%></p>
            <p>Last Name: <%= ((UserBean) session.getAttribute("user")).getLastName() %></p>
            <p>Username: <%= ((UserBean) session.getAttribute("user")).getUsername() %> </p>
            <p>Email:
                <a style="color: black; text-decoration: underline" href="mailto: <%= ((UserBean) session.getAttribute("user")).getEmail() %>">
                    <%= ((UserBean) session.getAttribute("user")).getEmail() %>
                </a>
            </p>
            <form action="logout_servlet" method="post">
                <button type="submit">Logout</button>
            </form>
            <%
                }
            %>

        </div>
    </div>

</nav>

<div class="product-details-container">
    <div class="product-image">
        <%
            if (product.getImage() != null) {
        %>
        <img src="product_servlet?action=image&name=<%= product.getImage() %>" alt="Product Image" draggable="false">
        <%
        } else {
        %>
        <img src="assets/default-placeholder-product.png" alt="No Image" draggable="false">
        <%
            }
        %>
    </div>
    <div class="product-info">
        <h2 class="product-name"><%= product.getName() %></h2>
        <p class="product-description"><%= product.getDescription() %></p>
        <p class="product-price">Price: $<%= product.getPrice() %></p>
        <p class="product-stock">In Stock: <%= product.getStock() %> pieces</p>

        <%
            // If user is logged in, display Add to Cart button
            if (session.getAttribute("user") != null) {
              %>
                <form action="cart_servlet" method="post">
                    <input type="hidden" name="product_id" value="<%= product.getId() %>">
                    <input type="hidden" name="user_id" value="<%= ((UserBean) session.getAttribute("user")).getId() %>">
                    <input type="hidden" name="action" value="add">
                    <label for="quantity-input">Quantity:</label>
                    <input id="quantity-input" type="number" name="quantity" value="1" min="1" max="<%= product.getStock() %>">
                    <br><br>
                    <button type="submit" class="add-to-cart">Add to Cart</button>
                </form>
            <%
                if(request.getAttribute("success") != null) {
//                  LoggerUtil.logMessage("Success message: " + request.getAttribute("success"));
                %>
                    <p style="color: green"><%= request.getAttribute("success") %></p>
                <%
                }
            }
            else {
              // Display it as a disabled button
            %>
            <button class="add-to-cart-disabled" disabled>Add to Cart</button>
            <div style="font-size: 10px">
                *<a style="text-decoration: underline;" href="login.jsp">Login/Register</a> to add this product to your cart.
            </div>
            <%
            }

            // Check if there is an error message in the session
            if (session.getAttribute("error_message") != null) {
            %>
                <p style="color: red"><%= session.getAttribute("error") %></p>
            <%
            }

        %>
    </div>
</div>

</body>
</html>
