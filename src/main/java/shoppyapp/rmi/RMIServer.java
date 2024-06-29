package shoppyapp.rmi;

import shoppyapp.util.LoggerUtil;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

  public static void main(String[] args) {

    try {
      ChatRoom chatRoom = new ChatRoom();

      Registry registry = LocateRegistry.createRegistry(10102);

      registry.rebind("chatRoom", chatRoom);

      LoggerUtil.logMessage("ChatRoom is running on port 10102");

    } catch (RemoteException e) {
      throw new RuntimeException(e);
    }

  }

}
