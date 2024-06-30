<%@ page import="shoppyapp.beans.UserBean" %>
<%@ page import="shoppyapp.services.ChatService" %>
<%@ page import="java.util.List" %>
<%@ page import="shoppyapp.beans.ChatRoomBean" %>
<%@ page import="shoppyapp.rmi.IChatRoom" %>
<%@ page import="java.rmi.Naming" %>
<%@ page import="java.rmi.NotBoundException" %>
<%@ page import="shoppyapp.config.Common" %>

<%
    if (session.getAttribute("user") != null && ((UserBean) session.getAttribute("user")).isAdmin()) {
        // If user is admin, display the page
    }
    else {
        response.sendRedirect("index.jsp");
    }

    IChatRoom chatRoom = null;
    try {
        chatRoom = (IChatRoom) Naming.lookup(Common.getRmiServerUrl());
    } catch (NotBoundException e) {
        throw new RuntimeException(e);
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

    <link rel="stylesheet" type="text/css" href="styles/chat_rooms.css">
    <link rel="stylesheet" type="text/css" href="styles/navbar.css">

    <script src="scripts/account-modal.js" defer></script>
    <script src="scripts/chat_rooms.js" defer></script>

</head>
<body>

<%@ include file="navbar.jsp" %>

<div id="chat-rooms-container">
    <div id="chat-rooms">
        <h2>Chat Rooms</h2>

        <ul>
            <!-- Loop through all chat rooms and display them -->
            <%
                ChatService chatService = new ChatService();

                List<ChatRoomBean> chatRoomList = chatService.getAllChatRooms();

                for (ChatRoomBean room : chatRoomList) {
            %>
            <li class="chat-room-item">
                <div>
                    <h3 class="chat-room-name"><%= room.getName() %></h3>
                    <p>
                        <%
                            int numberOfMessages = chatRoom.getMessageCount(room.getId());

                            // Format the number of messages if they are more than 1000
                            String formattedNumberOfMessages = numberOfMessages > 1000 ?
                                    String.format("%.1fK", (double) numberOfMessages / 1000) :
                                    String.valueOf(numberOfMessages);
                        %>
                        Total number of messages: <%= formattedNumberOfMessages %>
                    </p>
                </div>
                <div>
                    <p style="text-align: justify"><%= room.getDescription() %></p>
                </div>
                <div>
                    <div>
                        <button
                                class="edit"
                                data-id="<%= room.getId() %>"
                                data-name="<%= room.getName() %>"
                                data-description="<%= room.getDescription() %>"
                        >Edit</button>
                    </div>
                </div>
            </li>
            <%
                }
            %>
        </ul>
    </div>

    <div id="create-chat-room">
        <div>
            <h2>Create Room</h2>
            <form action="chatroom_servlet" method="post">
                <input type="hidden" name="action" value="create">
                <input type="text" name="name" placeholder="Room Name" spellcheck="false" required>
                <input type="text" name="description" placeholder="Room Description" spellcheck="false" required>

                <button type="submit">Create</button>
            </form>

            <!-- Display error message if product creation failed -->
            <%
                if (request.getAttribute("errorCreate") != null) {
                    System.out.println("Error: " + request.getAttribute("error"));
            %>
            <div style="text-align: center;">
                <p style="color: red; font-weight: bold;"><%= request.getAttribute("errorCreate") %></p>
            </div>
            <%
                }
            %>
        </div>

        <div id="edit-modal">
            <h2>Edit Room Details</h2>
            <form action="chatroom_servlet" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" id="edit-id">
                <input type="text" name="name" id="edit-name" placeholder="Room Name" required>
                <input type="text" name="description" id="edit-description" placeholder="Room Description" required>
                <button type="submit">Edit</button>
            </form>

            <div>
                <!-- Display error message if product edit failed -->
                <%
                    if (request.getAttribute("errorUpdate") != null) {
                %>
                <div style="text-align: center;">
                    <p style="color: red; font-weight: bold;"><%= request.getAttribute("errorUpdate") %></p>
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
