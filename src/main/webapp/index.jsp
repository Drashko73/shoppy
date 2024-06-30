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
          <li id="filterbycategory_0" onclick="filterByCategoryHandler(this.id)">
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

      <div id="pagination">
        <button id="firstPage" onclick="firstPage()"> << </button>
        <button id="prevPage" onclick="previousPage()"> < </button>
        <span id="pageNumbers"></span>
        <button id="nextPage" onclick="nextPage()"> > </button>
        <button id="lastPage" onclick="lastPage()"> >> </button>
      </div>

      <div id="product-grid"></div>

    </section>
  </div>

</body>
</html>