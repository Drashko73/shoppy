package shoppyapp.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatRoom extends Remote {

  int getMessageCount(long chatRoomId) throws RemoteException;

}
