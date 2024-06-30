package shoppyapp.rmi;

import shoppyapp.config.Common;
import shoppyapp.util.LoggerUtil;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

  public static void main(String[] args) {

    try {
      ChatRoom chatRoom = new ChatRoom();

      Registry registry = LocateRegistry.createRegistry(Common.RMI_SERVER_PORT);

      registry.rebind("chatRoom", chatRoom);

      LoggerUtil.logMessage("RMI Server is running on port " + Common.RMI_SERVER_PORT + "...");

    } catch (RemoteException e) {
      throw new RuntimeException(e);
    }

  }

}
