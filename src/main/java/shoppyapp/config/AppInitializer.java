package shoppyapp.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import shoppyapp.beans.UserBean;
import shoppyapp.rmi.RMIServer;
import shoppyapp.services.UserService;

import java.util.Optional;

@WebListener
public class AppInitializer implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    registerAdminUser();  // Register the admin user if it doesn't exist
    RMIServer.main(new String[]{});  // Start the RMI server
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // Close the database connection
    // DatabaseInitializer.close();
  }

  private static void registerAdminUser() {
    UserService userService = new UserService();
    String adminUsername = "admin";
    String adminPassword = "admin";
    String adminEmail = "admin@shoppy.com";
    String adminFirstName = "Admin";
    String adminLastName = "Admin";

    userService.registerAdminUser(adminUsername, adminPassword, adminEmail, adminFirstName, adminLastName);
  }

}
