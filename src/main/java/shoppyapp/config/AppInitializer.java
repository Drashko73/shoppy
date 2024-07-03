package shoppyapp.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import shoppyapp.rmi.RMIServer;
import shoppyapp.services.UserService;
import shoppyapp.util.LoggerUtil;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

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
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    while (drivers.hasMoreElements()) {
      Driver driver = drivers.nextElement();
      try {
        DriverManager.deregisterDriver(driver);
      } catch (Exception e) {
        LoggerUtil.logError("Error unregistering driver: " + driver + " | " + e.getMessage());
      }
    }

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
