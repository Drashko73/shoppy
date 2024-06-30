package shoppyapp.config;

public class Common {
  public static final int RMI_SERVER_PORT = 10102;
  public static final int NUMBER_OF_PRODUCTS_TO_SHOW = 8;
  public static final String PERSISTENCE_UNIT_NAME = "shoppy-pu";

  public static String getRmiServerUrl() {
    return "rmi://localhost:" + RMI_SERVER_PORT + "/chatRoom";
  }

}
