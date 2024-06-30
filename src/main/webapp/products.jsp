<%@ page import="shoppyapp.beans.UserBean" %>
<%@ page import="shoppyapp.beans.CategoryBean" %>
<%@ page import="java.util.List" %>
<%@ page import="shoppyapp.services.CategoryService" %>

<%
    if (session.getAttribute("user") != null && ((UserBean) session.getAttribute("user")).isAdmin()) {
        // If user is admin, display the page
    }
    else {
        response.sendRedirect("index.jsp");
    }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
    <meta name="author" content="Radovan Draskovic">
    <meta name="description" content="Shoppy - Online Shop">
    <meta name="keywords" content="Shoppy, Online Shop, E-Commerce, Shopping, Products, Categories, Orders, Customers">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="icon" type="image/png" href="assets/favicon.ico">

    <link rel="stylesheet" type="text/css" href="styles/products.css">
    <link rel="stylesheet" type="text/css" href="styles/navbar.css">

    <script src="scripts/account-modal.js" defer></script>
    <script src="scripts/products.js" defer></script>

</head>
<body onload="preparePage()">

<%@ include file="navbar.jsp"%>

<!-- Side by side div with the left one containing created categories and the right one containing the form for creating a new category -->
<div id="products-container">
    <div id="products" onscroll="scrollHandler()">
        <h2>Products</h2>

        <div id="search-div">
            <select id="selectInput" onchange="categoryChangeHandler()">
                <option id="category_option_0" value="0">All Categories</option>
                <%
                    CategoryService categoryService = new CategoryService();
                    List<CategoryBean> categories = categoryService.getAllCategories();
                    for (CategoryBean category : categories) {
                    %>
                        <option id="category_option_<%=category.getId()%>" value="<%= category.getId() %>"><%= category.getName() %></option>
                    <%
                    }
                %>
            </select>
        </div>

        <ul id="products-ul">
            <!-- Products will be here -->
        </ul>
    </div>

    <div id="create-product">
        <div>
            <h2>Create Product</h2>
            <form action="product_servlet" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="create">
                <input type="text" name="name" placeholder="Product Name" spellcheck="false" required>
                <input type="number" min="1" step="any" name="price" placeholder="Product Price" required>
                <input type="number" min="0" name="stock" placeholder="Stock Quantity" required>
                <input type="text" name="description" placeholder="Product Description" spellcheck="false" required>

                <!-- Choose image for the product -->
                <input type="file" name="image" accept="image/*">

                <!-- Display all categories in a dropdown list -->
                <label>
                    Category:
                    <select name="category_id" required>
                        <%
                            for (CategoryBean category : categories) {
                            %>
                                <option value="<%= category.getId() %>"><%= category.getName() %></option>
                            <%
                            }
                        %>
                    </select>
                </label>

                <button type="submit">Create</button>
            </form>

            <!-- Display error message if product creation failed -->
            <%
                if (request.getAttribute("error") != null) {
                    System.out.println("Error: " + request.getAttribute("error"));
            %>
            <div style="text-align: center;">
                <p style="color: red; font-weight: bold;"><%= request.getAttribute("error") %></p>
            </div>
            <%
                }
            %>
        </div>

        <div id="edit-modal">
            <h2>Edit Product</h2>
            <form action="product_servlet" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" id="edit-id">
                <input type="text" name="name" id="edit-name" placeholder="Product Name" required>
                <input type="number" min="1" step="any" name="price" id="edit-price" placeholder="Product Price" required>
                <input type="number" min="0" name="stock" id="edit-stock" placeholder="Stock Quantity" required>
                <input type="text" name="description" id="edit-description" placeholder="Product Description" required>
                <input type="file" name="image" id="edit-image" accept="image/*"> <!-- Choose image for the product -->
                <select name="category_id" id="edit-category" required>
                    <%
                        for (CategoryBean category : categories) {
                    %>
                    <option value="<%= category.getId() %>"><%= category.getName() %></option>
                    <%
                        }
                    %>
                </select>
                <button type="submit">Edit</button>
            </form>

            <div>
                <!-- Display error message if product edit failed -->
                <%
                    if (request.getAttribute("editUpdate") != null) {
                %>
                <div style="text-align: center;">
                    <p style="color: red; font-weight: bold;"><%= request.getAttribute("editUpdate") %></p>
                </div>
                <%
                    }
                %>
            </div>

        </div>

    </div>
</div>

</body>
</html>
