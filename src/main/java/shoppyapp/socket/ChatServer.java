package shoppyapp.socket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import shoppyapp.beans.UserBean;
import shoppyapp.services.ChatService;
import shoppyapp.services.UserService;
import shoppyapp.util.LoggerUtil;

import java.time.LocalDateTime;
import java.util.*;

@ServerEndpoint(value = "/chat/{chatroom}", configurator = ChatServerConfigurator.class)
public class ChatServer {

  private static final Map<String, Set<Session>> chatRooms = new HashMap<>();
  private final ChatService chatService = new ChatService();
  private final UserService userService = new UserService();

  private static int counter = 0;

  public ChatServer() {
  }

  // OnOpen event for a new connection to the chat server
  @OnOpen
  public void onOpen(Session session) {

    counter++;

    // Get the chat room ID from the session
    String chatroom = session.getPathParameters().get("chatroom");

    // Check if the chat room exists in the chatRooms map
    if (!chatRooms.containsKey(chatroom)) {
      chatRooms.put(chatroom, Collections.synchronizedSet(new HashSet<>()));
    }

    // Add the session to the chat room
    chatRooms.get(chatroom).add(session);

  }

  @OnMessage
  public void onMessage(String message, Session session) {

    LoggerUtil.logMessage("Message received: " + message);

    // Get the chat room ID from the session
    String chatroom = session.getPathParameters().get("chatroom");

    // Get the user from the session
    UserBean user = (UserBean) session.getUserProperties().get("user");

    if(user == null) {
//      LoggerUtil.logMessage("User not found in session: " + session.getId());
      return;
    }

    // Get the chat room by its ID
    chatService.getChatRoomById(Long.parseLong(chatroom)).ifPresent(chatRoom -> {

      // Add the message to the chat room
      chatService.addMessageToChatRoom(chatRoom.getId(), user.getId(), message);

      // Get all sessions in the chat room
      Set<Session> sessions = chatRooms.get(chatroom);

      String formattedMessage = formatMessageToJSON(user.getId(), user.getUsername(), message, LocalDateTime.now());

      // Send the message to all sessions in the chat room
      sessions.forEach(s -> {
        if(s.isOpen()) {
          s.getAsyncRemote().sendText(formattedMessage);
        }
        else {
          chatRooms.get(chatroom).remove(s); // Remove the session if it is closed
        }
      });

    });
  }

  @OnClose
  public void onClose(Session session) {

    // Get the chat room ID from the session
    String chatroom = session.getPathParameters().get("chatroom");
    chatRooms.get(chatroom).remove(session);

    counter--;

//    LoggerUtil.logMessage("Connection closed to chat server: " + session.getId() + " in chatroom: " + chatroom);
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    LoggerUtil.logError("Error in chat server: " + throwable.getMessage());
  }

  private String formatMessageToJSON(long userId, String username, String message, LocalDateTime timestamp) {
    return "{\"userId\": " + userId + ", \"username\": \"" + username + "\", \"message\": \"" + message + "\", \"timestamp\": \"" + timestamp + "\"}";
  }

}
