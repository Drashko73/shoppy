<%@ page import="shoppyapp.beans.UserBean" %><%--
  Created by IntelliJ IDEA.
  User: radov
  Date: 26-Jun-24
  Time: 4:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shoppy - Help</title>

    <meta name="author" content="Radovan Draskovic">
    <meta name="description" content="Shoppy - Online Shop">
    <meta name="keywords" content="Shoppy, Online Shop, E-Commerce, Shopping, Products, Categories, Orders, Customers">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="icon" type="image/png" href="assets/favicon.ico">

    <link rel="stylesheet" type="text/css" href="styles/help.css">

    <script src="scripts/account-modal.js" defer></script>
    <script src="scripts/help.js" defer></script>

</head>
<body>

    <nav id="navbar">
        <!-- Navigation bar content -->
        <!-- logo, links, cart, login, register -->
        <div id="logo-and-links">
            <div id="logo">
                <a href="index.jsp">
                    <img src="assets/logo.jpg" alt="Shoppy Logo" width="100px" height="30px">
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

    <!-- Help content -->
    <section id="faq">
        <h1>Frequently Asked Questions</h1>
        <div class="faq-item">
            <button class="faq-question">How to create an Account?</button>
            <div class="faq-answer">
                <p>
                    To create an account, click on the 'Login/Register' link in the navigation bar.
                    After that, you should see a form like the one below.
                </p>
                <div class="image-div">
                    <img src="assets/loginForm.png" alt="Login Form">
                </div>
                <p>
                    Click on the 'Sign Up' button to navigate to the registration form.
                    Fill in the required fields and click on the 'Sign Up' button to create an account.
                </p>
                <div class="image-div">
                    <img src="assets/filledRegisterForm.png" alt="Filled Register Form">
                </div>
            </div>
        </div>
        <div class="faq-item">
            <button class="faq-question">How do I log into my account?</button>
            <div class="faq-answer">
                <p>
                    In order to log into your account, click on the 'Login/Register' link in the navigation bar.
                    After that, you should see a form like the one below.
                </p>
                <div class="image-div">
                    <img src="assets/loginForm.png" alt="Login Form">
                </div>
                <p>
                    Fill in the required fields and click on the 'Sign In' button to log into your account.
                </p>
            </div>
        </div>
        <div class="faq-item">
            <button class="faq-question">What if I forget credentials for my account?</button>
            <div class="faq-answer">
                <p>
                    If you forget your password, click on the 'Login/Register' link in the navigation bar.
                    After that, click on the 'Problems with login?' link in order to contact our support team.
                </p>
            </div>
        </div>
        <div class="faq-item">
            <button class="faq-question">Do you offer international shipping?</button>
            <div class="faq-answer">
                <p>Yes, we offer international shipping to most countries. Shipping rates and delivery times vary depending on the destination.</p>
            </div>
        </div>
        <!-- Add more FAQ items as needed -->
    </section>


</body>
</html>
