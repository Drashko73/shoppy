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
    <title>Categories</title>
    <meta name="author" content="Radovan Draskovic">
    <meta name="description" content="Shoppy - Online Shop">
    <meta name="keywords" content="Shoppy, Online Shop, E-Commerce, Shopping, Products, Categories, Orders, Customers">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="icon" type="image/png" href="assets/favicon.ico">

    <link rel="stylesheet" type="text/css" href="styles/categories.css">
    <link rel="stylesheet" type="text/css" href="styles/navbar.css">

    <script src="scripts/account-modal.js" defer></script>
    <script src="scripts/categories.js" defer></script>

</head>
<body>

<!-- Navigation bar -->
<%@ include file="navbar.jsp" %>

<!-- Side by side div with the left one containing created categories and the right one containing the form for creating a new category -->
<div id="categories-container">
    <div id="categories">
        <h2>Categories</h2>

        <div id="search-div">
            <input id="searchInput" type="text" name="searchTerm" placeholder="Search Categories" spellcheck="false" onkeyup="searchCategoriesHandler()">
        </div>

        <ul>
            <!-- Loop through all categories and display them -->
            <%
                CategoryService categoryService = new CategoryService();

                List<CategoryBean> categories = categoryService.getAllCategories();
                for (CategoryBean category : categories) {
                %>
                    <li class="category-item">
                        <div>
                            <h3 class="category-name"><%= category.getName() %></h3>
                        </div>
                        <div>
                            <p style="text-align: justify"><%= category.getDescription() %></p>
                        </div>
                        <div>
                            <div>
                                <button
                                        class="edit"
                                        data-id="<%= category.getId() %>"
                                        data-name="<%= category.getName() %>"
                                        data-description="<%= category.getDescription() %>"
                                >Edit</button>
                            </div>

                            <form action="category_servlet" method="post">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="<%= category.getId() %>">
                                <button class="delete" onclick="return confirm('Are you sure you want to delete this category?')">Delete</button>
                            </form>
                        </div>
                    </li>
                <%
                }
            %>
        </ul>
    </div>

    <div id="create-category">
        <div>
            <h2>Create Category</h2>
            <form action="category_servlet" method="post">
                <input type="hidden" name="action" value="create">
                <input type="text" name="name" placeholder="Category Name" spellcheck="false" required>
                <input type="text" name="description" placeholder="Category Description" spellcheck="false" required>
                <button type="submit">Create</button>
            </form>

            <!-- Display error message if category creation failed -->
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

        <div id="edit-category">
            <h2>Edit Category</h2>
            <form action="category_servlet" method="post">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="id" id="edit-id">
                <input type="text" name="name" id="edit-name" placeholder="Category Name" spellcheck="false" required>
                <input type="text" name="description" id="edit-description" placeholder="Category Description" spellcheck="false" required>
                <button type="submit">Edit</button>
            </form>

            <div>
                <!-- Display error message if category edit failed -->
                <%
                    if (request.getAttribute("editError") != null) {
                    %>
                        <div style="text-align: center;">
                            <p style="color: red; font-weight: bold;"><%= request.getAttribute("editError") %></p>
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
