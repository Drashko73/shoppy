package shoppyapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shoppyapp.beans.CategoryBean;
import shoppyapp.services.CategoryService;
import shoppyapp.util.LoggerUtil;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "CategoryServlet", value = "/category_servlet")
public class CategoryServlet extends HttpServlet {

  private final CategoryService categoryService = new CategoryService();

  // Method to handle GET request for fetching all categories
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Fetch all categories
    request.setAttribute("categories", categoryService.getAllCategories());

    // Forward the request to the categories.jsp page
    request.getRequestDispatcher("categories.jsp").forward(request, response);
  }

  // Method to handle POST request for adding a new category
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String action = request.getParameter("action");

    if (action != null && action.equals("create")) {
      // Get the name and description of the category from the request
      String name = request.getParameter("name");
      String description = request.getParameter("description");

      // Validate the name and description
      if (validate(name, description)) {
        // Add the category to the database
        Optional<CategoryBean> categoryBean = categoryService.addCategory(name, description);

        if (categoryBean.isPresent()) {
          // Set the success message
          request.setAttribute("success", "Category added successfully!");
          response.sendRedirect("categories.jsp");
        } else {
          // Set the error message
//          LoggerUtil.logMessage("Error adding category. Duplicate category name is not allowed.");
          request.setAttribute("error", "Error adding category. Duplicate category name is not allowed.");
          request.getRequestDispatcher("categories.jsp").forward(request, response);
        }
      }
      else {
        // Set the error message
        request.setAttribute("error", "Error adding category. Please provide valid name and description.");
        request.getRequestDispatcher("categories.jsp").forward(request, response);
      }
    }
    else if (action != null && action.equals("delete")) {
      // Get the ID of the category to be deleted
      int id = Integer.parseInt(request.getParameter("id"));

      // Delete the category
      boolean deleted = categoryService.deleteCategory(id);

      if (deleted) {
        // Set the success message
        request.setAttribute("success", "Category deleted successfully!");
        response.sendRedirect("categories.jsp");
      } else {
        // Set the error message
//        LoggerUtil.logMessage("Error deleting category. Cannot delete category with ID: " + id);
        request.setAttribute("error", "Error deleting category");
        request.getRequestDispatcher("categories.jsp").forward(request, response);
      }
    }
    else if(action != null && action.equals("edit")) {
      // Get the ID of the category to be edited
      if(request.getParameter("id") == null || request.getParameter("id").isEmpty() ) {
        // Set the error message
        request.setAttribute("editError", "Error updating category. No category ID provided.");
        request.getRequestDispatcher("categories.jsp").forward(request, response);
        return;
      }
      int id = Integer.parseInt(request.getParameter("id"));

      // Get the name and description of the category from the request
      String name = request.getParameter("name");
      String description = request.getParameter("description");

      // Validate the name and description
      if (validate(name, description)) {
        // Update the category
        Optional<CategoryBean> categoryBean = categoryService.updateCategory(id, name, description);

        if (categoryBean.isPresent()) {
          // Set the success message
          request.setAttribute("success", "Category updated successfully!");
          response.sendRedirect("categories.jsp");
        } else {
          // Set the error message
//          LoggerUtil.logMessage("Error updating category. Cannot update category with ID: " + id);
          request.setAttribute("editError", "Error updating category");
          request.getRequestDispatcher("categories.jsp").forward(request, response);
        }
      }
      else {
        // Set the error message
        request.setAttribute("editError", "Error updating category. Please provide valid name and description.");
        request.getRequestDispatcher("categories.jsp").forward(request, response);
      }
    }
    else {
      // Set the error message
      request.setAttribute("editError", "Error adding category. Invalid action.");
      request.getRequestDispatcher("categories.jsp").forward(request, response);
    }

  }

  // Overridden method to handle DELETE request for deleting a category
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Get the ID of the category to be deleted
    int id = Integer.parseInt(request.getParameter("id"));

    // Delete the category
    boolean deleted = categoryService.deleteCategory(id);

    if (deleted) {
      // Set the success message
      request.setAttribute("success", "Category deleted successfully!");
      response.sendRedirect("categories.jsp");
    } else {
      // Set the error message
//      LoggerUtil.logMessage("Error deleting category. Cannot delete category with ID: " + id);
      request.setAttribute("error", "Error deleting category");
      request.getRequestDispatcher("categories.jsp").forward(request, response);
    }
  }

  private boolean validate(String name, String description) {
    return name != null && !name.isEmpty() && description != null && !description.isEmpty();
  }

}
