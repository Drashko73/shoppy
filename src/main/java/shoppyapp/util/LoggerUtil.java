package shoppyapp.util;

public class LoggerUtil {

  public static void logMessage(String message) {

    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";

    // Log the message to the console in the format: [INFO] message with the current timestamp and red color
    System.out.println("--------------------");
    System.out.println(ANSI_GREEN + java.time.LocalTime.now() + " [INFO] " + message + " " + ANSI_RESET);
    System.out.println("--------------------");

  }

  public static void logError(String message) {

    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";

    // Log the message to the console in the format: [ERROR] message with the current timestamp and red color
    System.out.println("--------------------");
    System.out.println(ANSI_RED + java.time.LocalTime.now() + " [ERROR] " + message + " " + ANSI_RESET);
    System.out.println("--------------------");

  }

}
