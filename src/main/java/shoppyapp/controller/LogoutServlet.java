package shoppyapp.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shoppyapp.services.UserService;

@WebServlet(name = "LogoutServlet", value = "/logout_servlet")
public class LogoutServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            HttpSession session = request.getSession();
            String sessionID = (String) session.getAttribute("sessionID");

            if (sessionID != null) {
                userService.logoutUser(sessionID);
                session.invalidate();
            }

            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            throw new ServletException("Error in doPost method of LogoutServlet.", e);
        }
    }

}
