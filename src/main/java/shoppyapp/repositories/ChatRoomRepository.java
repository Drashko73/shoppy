package shoppyapp.repositories;

import jakarta.persistence.EntityManager;
import shoppyapp.entities.ChatRoom;
import shoppyapp.util.DbUtil;
import shoppyapp.util.LoggerUtil;

import java.util.List;
import java.util.Optional;

public class ChatRoomRepository {

  private final EntityManager entityManager = DbUtil.getEntityManager();

  public ChatRoomRepository() {
  }

  public Optional<ChatRoom> findById(Long id) {
    ChatRoom chatRoom = entityManager.find(ChatRoom.class, id);
    return chatRoom != null ? Optional.of(chatRoom) : Optional.empty();
  }

  public Optional<ChatRoom> findByName(String name) {
    return entityManager.createQuery("SELECT cr FROM ChatRoom cr WHERE cr.name = :name", ChatRoom.class)
            .setParameter("name", name)
            .getResultList()
            .stream()
            .findFirst();
  }

  public List<ChatRoom> findAll() {
    return entityManager.createQuery("SELECT cr FROM ChatRoom cr", ChatRoom.class)
            .getResultList();
  }

  public void save(ChatRoom chatRoom) {
    try {
      if(chatRoom.getId() == null) {
        entityManager.getTransaction().begin();
        entityManager.persist(chatRoom);
        entityManager.getTransaction().commit();
      }
      else {
        entityManager.getTransaction().begin();
        entityManager.merge(chatRoom);
        entityManager.getTransaction().commit();
      }
    }
    catch (Exception e) {
      if(entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      LoggerUtil.logError("Error saving chat room: " + e.getMessage());
    }
  }

  public void deleteById(Long id) {
    Optional<ChatRoom> chatRoomOptional = findById(id);

    if (chatRoomOptional.isPresent()) {
      ChatRoom chatRoom = chatRoomOptional.get();
      entityManager.getTransaction().begin();
      entityManager.remove(chatRoom);
      entityManager.getTransaction().commit();
    }
    else {
      LoggerUtil.logError("Chat room with id " + id + " not found");
    }
  }

  public void update(ChatRoom chatRoom) {
    Optional<ChatRoom> chatRoomOptional = findById(chatRoom.getId());

    if (chatRoomOptional.isPresent()) {
      ChatRoom existingChatRoom = chatRoomOptional.get();
      existingChatRoom.setName(chatRoom.getName());
      existingChatRoom.setDescription(chatRoom.getDescription());
      save(existingChatRoom);
    }
    else {
      LoggerUtil.logError("Chat room with id " + chatRoom.getId() + " not found");
    }
  }

  public void close() {
    try {
      entityManager.close();
    }
    catch (Exception e) {
      LoggerUtil.logError("Error closing entity manager: " + e.getMessage());
    }
  }


}
