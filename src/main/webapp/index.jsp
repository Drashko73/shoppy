<%@ page import="shoppyapp.beans.UserBean" %>
<%@ page import="shoppyapp.services.ProductService" %>
<%@ page import="shoppyapp.beans.ProductBean" %>
<%@ page import="java.util.List" %>
<%@ page import="shoppyapp.services.CategoryService" %>
<%@ page import="shoppyapp.beans.CategoryBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Shoppy</title>

  <meta name="author" content="Radovan Draskovic">
  <meta name="description" content="Shoppy - Online Shop">
  <meta name="keywords" content="Shoppy, Online Shop, E-Commerce, Shopping, Products, Categories, Orders, Customers">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <%--  Link to favicon --%>
  <link rel="icon" type="image/png" href="assets/favicon.ico">

  <%--  Link to CSS --%>
  <link rel="stylesheet" type="text/css" href="styles/index-style.css">

  <%--  Link to JavaScript --%>
  <script src="scripts/index-script.js" defer></script>
  <script src="scripts/account-modal.js" defer></script>

</head>
<body onload="preparePage()">

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

  <div id="main">
    <aside id="sidebar">
      <!-- Sidebar content -->
      <!-- List of categories -->

      <h2>Categories</h2>

      <ul>
          <li class="selected-category" id="filterbycategory_0" onclick="filterByCategoryHandler(this.id)">
            <span>All</span>
          </li>
          <%
            CategoryService categoryService = new CategoryService();
            List<CategoryBean> categories = categoryService.getAllCategories();

            for (CategoryBean category : categories) {
            %>
              <li id="filterbycategory_<%=category.getId()%>" onclick="filterByCategoryHandler(this.id)">
                <span><%= category.getName() %></span>
              </li>
            <%
            }
          %>
      </ul>

    </aside>

    <section id="products">
      <!-- Main window content -->

      <h2>Products</h2>

      <div class="product-grid">
        <%
          ProductService productService = new ProductService();
          List<ProductBean> products = productService.getAllProducts();
          for (ProductBean product : products) {
          %>
            <div class="product-item">
              <div class="product-image-container">
                <%
                  if(product.getImage() == null) {
                  %>
                    <img draggable="false" class="product-image" src="assets/default-placeholder-product.png" alt="Product Image">
                  <%
                  }
                  else {
                  %>
                    <img draggable="false" class="product-image" src="product_servlet?action=image&name=<%= product.getImage() %>" alt="Product Image">
                  <%
                  }
                %>
              </div>
              <div class="product-details">
                <h2 class="product-name"><%= product.getName() %></h2>
                <p class="product-price">$<%= product.getPrice() %></p>
                <a href="product_details.jsp?product_id=<%= product.getId() %>" class="product-link">View Details</a>
                <input type="hidden" value="<%=product.getCategoryId()%>" class="cat-id-hidden">
              </div>
            </div>
          <%
          }
        %>
      </div>

    </section>
  </div>

</body>
</html>