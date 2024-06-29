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
    <link rel="stylesheet" href="styles/navbar.css">

    <script src="scripts/account-modal.js" defer></script>

</head>
<body>

<%@ include file="navbar.jsp"%>

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
