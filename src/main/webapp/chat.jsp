<%@ page import="shoppyapp.services.ChatService" %>
<%@ page import="shoppyapp.beans.ChatRoomBean" %>
<%@ page import="java.util.List" %>

<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
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

    <link rel="stylesheet" href="styles/chat.css">
    <link rel="stylesheet" href="styles/navbar.css">

    <script src="scripts/account-modal.js" defer></script>

</head>
<body>

<%@ include file="navbar.jsp"%>

<!-- Display all chat rooms -->
<div class="chat-rooms-container">
    <h1>Available Chat Rooms</h1>
    <div id="chat-rooms">
        <%
            ChatService chatService = new ChatService();
            List<ChatRoomBean> chatRooms = chatService.getAllChatRooms();

            for (ChatRoomBean chatRoom : chatRooms) {
        %>
        <div class="chat-room">
            <h2><%= chatRoom.getName() %></h2>
            <p><%= chatRoom.getDescription() %></p>
            <a href="chat_room.jsp?room_id=<%= chatRoom.getId() %>" class="join-button">Join</a>
        </div>
        <%
            }
        %>
    </div>
</div>

</body>
</html>
