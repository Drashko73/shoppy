<%@ page import="shoppyapp.beans.UserBean" %>
<%@ page import="shoppyapp.services.OrderService" %>
<%@ page import="shoppyapp.beans.OrderBean" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: radov
  Date: 28-Jun-24
  Time: 2:23 AM
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

    <link rel="stylesheet" href="styles/orders.css">

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

<!-- Display all orders for the logged user -->
<div class="orders-container">
    <%
        OrderService orderService = new OrderService();
        List<OrderBean> orders = orderService.getOrdersByUserId(((UserBean) session.getAttribute("user")).getId());

        // If user has no orders, display message and image
        if (orders.isEmpty()) {
    %>
    <div id="no-orders">
        <h2>No Orders</h2>
        <p>You have not placed any orders yet.</p>
        <img src="assets/empty-cart.png" alt="No Orders" width="200px" height="200px">
    </div>
    <%
    } else {
    %>
    <div id="orders">
        <h1>Your Orders</h1>
        <table class="orders-table">
            <tr>
                <th>Order ID</th>
                <th>Order Date</th>
                <th>Order Total</th>
            </tr>
            <%
                for (OrderBean order : orders) {
            %>
            <tr>
                <td><%=order.getId()%></td>
                <td><%= order.getOrderDate() %></td>
                <td>$<%= String.format("%.2f", order.getTotalPrice()) %></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <%
        }
    %>
</div>

</body>
</html>
