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

@WebServlet(name = "LoginServlet", value = "/login_servlet")
public class LoginServlet extends HttpServlet {

  private final UserService userService = new UserService();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    try {
      String email = request.getParameter("email");
      String password = request.getParameter("password");

      if (!validate(email, password)) {
        request.setAttribute("error", "Email and password cannot be empty.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
        return;
      }

      Optional<UserBean> userOptional = userService.loginUser(email, password);

      if (userOptional.isPresent()) {
        UserBean userBean = userOptional.get();
        HttpSession session = request.getSession();
        session.setAttribute("sessionID", userBean.getSessionID());
        request.getSession().setAttribute("user", userOptional.get());
        response.sendRedirect("index.jsp");
      } else {
        request.setAttribute("error", "Login failed. Invalid email or password.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
      }
    }
    catch (Exception e) {
      throw new ServletException("Error in doPost method of LoginServlet.", e);
    }

  }

  // Validate if email and password are not empty
  private boolean validate(String email, String password) {
    return email != null && !email.isEmpty() && password != null && !password.isEmpty();
  }

}
