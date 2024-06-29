<%
    if (session.getAttribute("user") != null) {
        response.sendRedirect("index.jsp");
    }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shoppy - Login</title>
    <meta name="author" content="Radovan Draskovic">
    <meta name="description" content="Shoppy - Online Shop">
    <meta name="keywords" content="Shoppy, Online Shop, E-Commerce, Shopping, Products, Categories, Orders, Customers">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="icon" type="image/png" href="assets/favicon.ico">

    <link rel="stylesheet" type="text/css" href="styles/login.css">
    <link rel="stylesheet" type="text/css" href="styles/navbar.css">

    <script src="scripts/login.js" defer></script>
</head>
<body>

    <%@ include file="navbar.jsp"%>

    <div id="login-container">
        <div class="container" id="container">
            <div class="form-container sign-up-container">
                <!-- Create Account Form -->
                <form action="register_servlet" method="post">
                    <h1>Create Account</h1>
                    <input type="text" name="first_name" placeholder="First Name" spellcheck="false">
                    <input type="text" name="last_name" placeholder="Last Name" spellcheck="false">
                    <input id="input-email-register" name="email" type="email" placeholder="Email" spellcheck="false" onchange="validEmailAddressSignUp()">
                    <input type="password" name="password" placeholder="Password" spellcheck="false">
                    <button>Sign Up</button>

                    <%
                        if (request.getAttribute("error") != null) {
                    %>
                    <span style="color: red; font-weight: bold;">
                        <%= request.getAttribute("error") %>
                    </span>
                    <%
                        }
                    %>

                </form>

            </div>
            <div class="form-container sign-in-container">
                <!-- Sign In Form -->
                <form action="login_servlet" method="post">
                    <h1>Sign in</h1>
                    <!-- Email input -->
                    <input id="input-email-login" type="email" name="email" placeholder="Email" spellcheck="false" onchange="validEmailAddressSignIn()">
                    <input type="password" name="password" placeholder="Password" spellcheck="false">
                    <a href="mailto:support@shoppy.com" content="Contact Support">Problems with login?</a>
                    <button>Sign In</button>

                    <%
                        if (request.getAttribute("error") != null) {
                    %>
                    <span style="color: red; font-weight: bold;">
                        <%= request.getAttribute("error") %>
                    </span>
                    <%
                        }
                    %>
                </form>
            </div>
            <div class="overlay-container">
                <div class="overlay">
                    <div class="overlay-panel overlay-left">
                        <h1>Welcome Back!</h1>
                        <p>To keep connected with us please sign in with your personal info</p>
                        <button class="ghost" id="signIn">Sign In</button>
                    </div>
                    <div class="overlay-panel overlay-right">
                        <h1>Don't have an account?</h1>
                        <p>Sign up now and start shopping with us!</p>
                        <button class="ghost" id="signUp">Sign Up</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
