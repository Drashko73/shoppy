package shoppyapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shoppyapp.services.ChatService;

import java.io.IOException;

@WebServlet(name = "ChatRoomServlet", urlPatterns = {"/chatroom_servlet"})
public class ChatRoomServlet extends HttpServlet {

  private final ChatService chatService = new ChatService();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String action = request.getParameter("action");

    if(action != null && action.equals("create")) {
      String name = request.getParameter("name");
      String description = request.getParameter("description");

      boolean chatRoomCreated = chatService.createChatRoom(name, description);

      if(chatRoomCreated) {
        request.setAttribute("success", "Chat room created successfully");
        response.sendRedirect("chat_rooms.jsp");
      }
      else {
        request.setAttribute("errorCreate", "There is already a chat room with the same name");
        request.getRequestDispatcher("chat_rooms.jsp").forward(request, response);
      }
    }
    else if(action != null && action.equals("update")) {

      long chatRoomId = Long.parseLong(request.getParameter("id"));
      String name = request.getParameter("name");
      String description = request.getParameter("description");

      boolean chatRoomUpdated = chatService.updateChatRoom(chatRoomId, name, description);

      if(chatRoomUpdated) {
        request.setAttribute("success", "Chat room updated successfully");
        response.sendRedirect("chat_rooms.jsp");
      }
      else {
        request.setAttribute("errorUpdate", "There is already a chat room with the same name");
        request.getRequestDispatcher("chat_rooms.jsp").forward(request, response);
      }

    }

  }

}
