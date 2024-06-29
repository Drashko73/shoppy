package shoppyapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shoppyapp.beans.UserBean;
import shoppyapp.services.UserService;

import java.util.Optional;

@WebServlet(name = "RegisterServlet", value = "/register_servlet")
public class RegisterServlet extends HttpServlet {

  private final UserService userService = new UserService();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    try {
      String password = request.getParameter("password");
      String email = request.getParameter("email");
      String firstName = request.getParameter("first_name");
      String lastName = request.getParameter("last_name");

      if (!validate(firstName, lastName, email, password)) {
        request.setAttribute("error", "All fields are required.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
        return;
      }

      Optional<UserBean> userOptional = userService.registerUser(generateUsername(firstName, lastName), password, email, firstName, lastName);

      if (userOptional.isPresent()) {
        request.getSession().setAttribute("user", userOptional.get());
        HttpSession session = request.getSession();
        session.setAttribute("sessionID", userOptional.get().getSessionID());
        response.sendRedirect("index.jsp");
      } else {
        request.setAttribute("error", "Registration failed. Username or email already exists.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
      }
    }
    catch (Exception e) {
      throw new ServletException("Error in doPost method of RegisterServlet.", e);
    }

  }

  private String generateUsername(String firstName, String lastName) {
    int count = 0;
    String trash = "";
    String username = firstName.toLowerCase() + "." + lastName.toLowerCase() + trash;
    boolean usernameExists = userService.usernameExists(username);

    while (usernameExists) {
      count++;
      trash = String.valueOf(count);
      username = firstName.toLowerCase() + "." + lastName.toLowerCase() + trash;
      usernameExists = userService.usernameExists(username);
    }

    return username;
  }

  private boolean validate(String firstName, String lastName, String email, String password) {
    return firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty() && email != null && !email.isEmpty() && password != null && !password.isEmpty();
  }

}
