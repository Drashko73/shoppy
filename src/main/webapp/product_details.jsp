<%@ page import="shoppyapp.beans.UserBean" %>
<%@ page import="shoppyapp.beans.ProductBean" %>
<%@ page import="shoppyapp.services.ProductService" %>
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
    <link rel="stylesheet" type="text/css" href="styles/navbar.css">

    <script src="scripts/account-modal.js" defer></script>

</head>
<body>

<%@ include file="navbar.jsp"%>

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
