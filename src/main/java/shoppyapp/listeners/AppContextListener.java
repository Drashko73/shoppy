package shoppyapp.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import shoppyapp.rmi.RMIServer;
import shoppyapp.util.LoggerUtil;

@WebListener
public class AppContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
//    try {
//      RMIServer.main(new String[]{});
//      LoggerUtil.logMessage("RMI Server started...");
//    }
//    catch (Exception e) {
//      throw new RuntimeException(e);
//    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
//    LoggerUtil.logMessage("RMI Server shutting down...");
  }

}
