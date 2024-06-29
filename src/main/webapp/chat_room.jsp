<%@ page import="shoppyapp.services.ChatService" %>
<%@ page import="shoppyapp.beans.ChatRoomBean" %>
<%@ page import="java.util.Optional" %>
<%@ page import="shoppyapp.beans.UserBean" %>
<%@ page import="shoppyapp.util.LoggerUtil" %>
<%@ page import="shoppyapp.services.UserService" %>
<%@ page import="shoppyapp.beans.ChatMessageBean" %>
<%@ page import="java.util.List" %>

<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    long chatRoomId = 0;

    try {

        // Get the chat room id from the request
        chatRoomId = Long.parseLong(request.getParameter("room_id"));

        // Check if the chat room exists
        ChatService chatService = new ChatService();
        Optional<ChatRoomBean> chatRoom = chatService.getChatRoomById(chatRoomId);

        if (chatRoom.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

//        LoggerUtil.logMessage("Chat Room: " + chatRoom.get().getName());

    }
    catch (Exception e) {
        response.sendRedirect("index.jsp");
        return;
    }

    String sessionId = session.getAttribute("sessionID").toString();
    if(sessionId == null || sessionId.isEmpty()) {
        response.sendRedirect("index.jsp");
        return;
    }

    UserBean user = (UserBean) session.getAttribute("user");

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

    <link rel="stylesheet" href="styles/chat_room.css">

    <script src="scripts/account-modal.js" defer></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- jQuery -->

    <script>

        let websocket;

        $(document).ready(function() {
            let sessionID = "<%= sessionId %>";
            let chatRoomId = "<%= chatRoomId %>";

            if(!sessionID) {    // If session id is invalid
                window.location.replace("login.jsp");
            }

            if(chatRoomId === "0") {    // If chat room id is invalid
                window.location.replace("chat.jsp");
            }

            connectWebSocket(chatRoomId, sessionID);

            $('#messageForm').submit(function(event) {
                event.preventDefault();
                let message = $('#messageInput').val().trim();
                if (message.length > 0) {
                    sendMessage(message);
                    $('#messageInput').val('');
                }
            });

            scrollToBottom();

        });

        function connectWebSocket(chatRoomId, sessionId) {
            console.log('Connecting to WebSocket... ' + chatRoomId + ' ' + sessionId);
            websocket = new WebSocket('ws://localhost:10101/shoppy_war_exploded/chat/' + chatRoomId + '?sessionId=' + sessionId);

            websocket.onopen = function(event) {
                console.log('WebSocket connected');
            };

            websocket.onmessage = function(event) {
                let message = JSON.parse(event.data);
                displayMessage(message);
            };

            websocket.onerror = function(event) {
                console.error('WebSocket error: ' + event.data);
            };

            websocket.onclose = function(event) {
                console.log('WebSocket closed');
            };
        }

        function sendMessage(message) {
            websocket.send(message);
        }

        function displayMessage(message) {
            console.log(message);
            let currentUser = '<%= user.getId() %>';
            let senderId = message.userId;
            let messageClass = senderId == currentUser ? 'sent' : 'received';

            let messageElement = '' +
                '<div class="message ' + messageClass + '">' +
                '   <div class="message-content">' +
                '       <div class="message-header">' +
                '           <span class="username">' + message.username + '</span>' +
                '           <span class="timestamp">' + message.timestamp + '</span>' +
                '       </div>' +
                '       <div class="message-text">' + message.message + '</div>' +
                '   </div>' +
                '   <img src="assets/user-avatar.png" alt="User Avatar" class="avatar" draggable="false">' +
                '</div>';

            $('#chatMessages').append(messageElement);
            console.log(messageElement);
            scrollToBottom();
        }

        let scrollTimeout;

        function scrollToBottom() {
            let delay = 200;

            clearTimeout(scrollTimeout);

            scrollTimeout = setTimeout(function() {
                $('#chatMessages').animate({ scrollTop: $('#chatMessages')[0].scrollHeight }, 'slow');
            }, delay);
        }

    </script>

</head>
<body>

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

<div id="chatContainer" class="container">

    <div id="chatMessages" class="chat-messages">

        <%
            // Get chat messages from the database
            ChatService chatService = new ChatService();
            UserService userService = new UserService();
            List<ChatMessageBean> messages = chatService.getChatRoomMessages(chatRoomId);

            for (ChatMessageBean message : messages) {
            %>
                <div class="message <%= message.getSenderId() == user.getId() ? "sent" : "received" %>">
                    <div class="message-content">
                        <div class="message-header">
                            <%
                                Optional<UserBean> sender = userService.getUserById(message.getSenderId());
                            %>
                            <span class="username">
                                <%=sender.map(UserBean::getUsername).orElse("Unknown") %>
                            </span>
                            <span class="timestamp"><%= message.getTimestamp() %></span>
                        </div>
                        <div class="message-text">
                            <%= message.getMessage() %>
                        </div>
                    </div>
                    <img src="assets/user-avatar.png" alt="User Avatar" class="avatar" draggable="false">
                </div>
            <%
            }
        %>
    </div>

    <form id="messageForm" class="message-form">
        <input type="text" id="messageInput" placeholder="Type your message..." class="message-input" maxlength="2000">
        <button type="submit" class="send-button">Send</button>
    </form>
</div>

</body>
</html>
