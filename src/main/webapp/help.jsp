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
    <link rel="stylesheet" type="text/css" href="styles/navbar.css">

    <script src="scripts/account-modal.js" defer></script>
    <script src="scripts/help.js" defer></script>

</head>
<body>

    <%@ include file="navbar.jsp"%>

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
