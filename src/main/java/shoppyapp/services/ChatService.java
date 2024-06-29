package shoppyapp.services;

import shoppyapp.beans.ChatMessageBean;
import shoppyapp.beans.ChatRoomBean;
import shoppyapp.entities.ChatMessage;
import shoppyapp.entities.ChatRoom;
import shoppyapp.entities.User;
import shoppyapp.repositories.ChatMessageRepository;
import shoppyapp.repositories.ChatRoomRepository;
import shoppyapp.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ChatService {

  private final ChatRoomRepository chatRoomRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final UserRepository userRepository;

  public ChatService() {
    this.chatRoomRepository = new ChatRoomRepository();
    this.chatMessageRepository = new ChatMessageRepository();
    this.userRepository = new UserRepository();
  }

  // Create a new chat room
  public boolean createChatRoom(String name, String description) {

    // Check if there is already a chat room with the same name
    Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByName(name);
    if(chatRoomOptional.isPresent()) {
      return false;
    }

    // Create a new chat room
    ChatRoom chatRoom = new ChatRoom(name, description);
    chatRoomRepository.save(chatRoom);

    return true;
  }

  // Get a chat room by its ID
  public Optional<ChatRoomBean> getChatRoomById(Long id) {
    Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(id);
    if (chatRoomOptional.isPresent()) {
      ChatRoom chatRoom = chatRoomOptional.get();
      return Optional.of(new ChatRoomBean(chatRoom.getId(), chatRoom.getName(), chatRoom.getDescription()));
    }
    return Optional.empty();
  }

  // Get all chat rooms
  public List<ChatRoomBean> getAllChatRooms() {
    List<ChatRoom> chatRooms = chatRoomRepository.findAll();
    List<ChatRoomBean> chatRoomBeans = new ArrayList<>();

    for (ChatRoom chatRoom : chatRooms) {
      chatRoomBeans.add(new ChatRoomBean(chatRoom.getId(), chatRoom.getName(), chatRoom.getDescription()));
    }

    return chatRoomBeans;
  }

  // Get all messages in a chat room
  public List<ChatMessageBean> getChatRoomMessages(Long chatRoomId) {
    List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomId(chatRoomId);
    List<ChatMessageBean> chatMessageBeans = new ArrayList<>();

    for (ChatMessage chatMessage : chatMessages) {
      chatMessageBeans.add(
              new ChatMessageBean(
                      chatMessage.getId(),
                      chatMessage.getSender().getId(),
                      chatMessage.getMessage(),
                      chatMessage.getTimestamp().toString(),
                      chatMessage.getChatRoom().getId()
              ));
    }

    return chatMessageBeans;
  }

  // Add a message to a chat room
  public boolean addMessageToChatRoom(Long chatRoomId, Long senderId, String message) {
    Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatRoomId);
    if (chatRoomOptional.isPresent()) {
      ChatRoom chatRoom = chatRoomOptional.get();

      // Get the sender user
      Optional<User> senderOptional = userRepository.findById(senderId);

      if (senderOptional.isPresent()) {
        User sender = senderOptional.get();
        ChatMessage chatMessage = new ChatMessage(sender, message, LocalDateTime.now(), chatRoom);
        chatMessageRepository.save(chatMessage);
        return true;
      }
      else {
        return false;
      }
    }
    return false;
  }

  // Update chat room information
  public boolean updateChatRoom(Long chatRoomId, String name, String description) {
    Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatRoomId);
    if (chatRoomOptional.isPresent()) {

      // Check if there is already a chat room with the same name
      Optional<ChatRoom> chatRoomWithSameNameOptional = chatRoomRepository.findByName(name);

      if(chatRoomWithSameNameOptional.isPresent() && !Objects.equals(chatRoomWithSameNameOptional.get().getId(), chatRoomId)) {
        return false;
      }

      ChatRoom chatRoom = chatRoomOptional.get();
      chatRoom.setName(name);
      chatRoom.setDescription(description);
      chatRoomRepository.update(chatRoom);
      return true;
    }
    return false;
  }

  // Close the chat service
  public void close() {
    chatRoomRepository.close();
    chatMessageRepository.close();
    userRepository.close();
  }

}
