package shoppyapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import shoppyapp.services.ProductService;
import shoppyapp.util.LoggerUtil;

import java.io.*;
import java.nio.file.Paths;

@WebServlet(name = "ProductServlet", value = "/product_servlet")
@MultipartConfig
public class ProductServlet extends HttpServlet {

  private final ProductService productService = new ProductService();

  // Add a new product
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Get the action from the request
    String action = request.getParameter("action");

    if (action != null && action.equals("create")) {
      // Get the name, price, stock, description, image, and category ID of the product from the request
      String name = request.getParameter("name");
      double price = Double.parseDouble(request.getParameter("price"));
      int stock = Integer.parseInt(request.getParameter("stock"));
      String description = request.getParameter("description");

      Part filePart = request.getPart("image"); // Retrieves <input type="file" name="image">
      boolean imageUploaded = filePart.getSize() != 0;
      String fileName = null;
      File file = null;

      if (imageUploaded) {
        fileName = getFileName(filePart);

        // add random number to file name to avoid duplicate file names
        fileName = System.currentTimeMillis() + "_" + fileName;

        // Define the path where you want to save the file
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        // Create the directory if it doesn't exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
          uploadDir.mkdir();
        }

        file = new File(uploadPath + File.separator + fileName);
      }


      int categoryId = Integer.parseInt(request.getParameter("category_id"));

      // Add the product to the database
      if(productService.addProduct(name, price, stock, description, fileName, categoryId)) {

        if (imageUploaded) {
          // Save the file to the specified path
          try (FileOutputStream fos = new FileOutputStream(file);
               InputStream is = filePart.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
              fos.write(buffer, 0, bytesRead);
            }
          }
//          LoggerUtil.logMessage("File uploaded to: " + file.getAbsolutePath());
        }

        // Set the success message
        request.setAttribute("success", "Product added successfully!");
        response.sendRedirect("products.jsp");
      } else {
        // Set the error message
        request.setAttribute("error", "Error adding product. Please provide valid details.");
        request.getRequestDispatcher("products.jsp").forward(request, response);
      }
    }
    else if(action != null && action.equals("delete")){
      // Get the ID of the product to be deleted
      int id = Integer.parseInt(request.getParameter("id"));

      // Delete the product from the database
      if(productService.deleteProduct(id)){
        // Set the success message
        request.setAttribute("success", "Product deleted successfully!");
        response.sendRedirect("products.jsp");
      } else {
        // Set the error message
        request.setAttribute("error", "Error deleting product. Please provide a valid ID.");
        request.getRequestDispatcher("products.jsp").forward(request, response);
      }
    }
    else if(action != null && action.equals("update")){

      int id = Integer.parseInt(request.getParameter("id"));  // Get the ID of the product to be updated
      String name = request.getParameter("name");  // Get new name of the product
      double price = Double.parseDouble(request.getParameter("price"));  // Get new price of the product
      int stock = Integer.parseInt(request.getParameter("stock"));  // Get new stock of the product
      String description = request.getParameter("description");  // Get new description of the product
      int categoryId = Integer.parseInt(request.getParameter("category_id"));  // Get new category ID of the product

      Part filePart = request.getPart("image"); // Retrieves <input type="file" name="image">
      boolean imageUploaded = filePart.getSize() != 0;
      String fileName = null;
      File file = null;

      if (imageUploaded) {
        fileName = getFileName(filePart);

        // add random number to file name to avoid duplicate file names
        fileName = System.currentTimeMillis() + "_" + fileName;

        // Define the path where you want to save the file
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        // Create the directory if it doesn't exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
          uploadDir.mkdir();
        }

        file = new File(uploadPath + File.separator + fileName);
      }

      // Update the product in the database
      if(productService.updateProduct(id, name, price, stock, description, fileName, categoryId)) {

        if (imageUploaded) {
          // Save the file to the specified path
          try (FileOutputStream fos = new FileOutputStream(file);
               InputStream is = filePart.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
              fos.write(buffer, 0, bytesRead);
            }
          }
//          LoggerUtil.logMessage("File uploaded to: " + file.getAbsolutePath());
        }
        // Set the success message
        request.setAttribute("success", "Product added successfully!");
        response.sendRedirect("products.jsp");
      }
      else {
          // Set the error message
          request.setAttribute("errorUpdate", "Error updating product. Please provide valid details.");
          request.getRequestDispatcher("products.jsp").forward(request, response);
      }
    }
    else {
      // Other actions
    }


  }

  // Get products image
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Get the action from the request
    String action = request.getParameter("action");

    if (action != null && action.equals("image")) {
      // Get the image name from the request
      String imageName = request.getParameter("name");

      // Define the path where the images are stored
      String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

      // Get the image file
      File file = Paths.get(uploadPath, imageName).toFile();

      // Set the content type of the response
      response.setContentType("image/jpeg");

      // Write the image to the response
      try (FileInputStream fis = new FileInputStream(file);
           ServletOutputStream os = response.getOutputStream()) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
          os.write(buffer, 0, bytesRead);
        }
      }
    }
  }

  private String getFileName(Part part) {
    String contentDisposition = part.getHeader("content-disposition");
    for (String cd : contentDisposition.split(";")) {
      if (cd.trim().startsWith("filename")) {
        return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
      }
    }
    return null;
  }

}
