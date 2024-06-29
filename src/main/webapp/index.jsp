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
  <link rel="stylesheet" type="text/css" href="styles/navbar.css">

  <%--  Link to JavaScript --%>
  <script src="scripts/index-script.js" defer></script>
  <script src="scripts/account-modal.js" defer></script>

</head>
<body onload="preparePage()">

  <%@ include file="navbar.jsp"%>

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