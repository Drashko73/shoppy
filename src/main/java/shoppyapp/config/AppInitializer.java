package shoppyapp.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import shoppyapp.rmi.RMIServer;
import shoppyapp.services.UserService;

@WebListener
public class AppInitializer implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    registerAdminUser();  // Register the admin user if it doesn't exist
    Thread rmiServerThread = new Thread(new Runnable() {
      @Override
      public void run() {
        RMIServer.main(new String[] {});
      }
    });
    rmiServerThread.start();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
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
