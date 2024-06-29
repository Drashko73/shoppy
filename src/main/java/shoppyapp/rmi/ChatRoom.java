package shoppyapp.rmi;

import shoppyapp.services.ChatService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatRoom extends UnicastRemoteObject implements IChatRoom {

  private static final ChatService chatService = new ChatService();

  protected ChatRoom() throws RemoteException {
  }

  @Override
  public int getMessageCount(long chatRoomId) {
    return chatService.getChatRoomMessagesCount(chatRoomId);
  }

}
