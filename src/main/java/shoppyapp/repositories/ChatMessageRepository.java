package shoppyapp.repositories;

import jakarta.persistence.EntityManager;
import shoppyapp.entities.ChatMessage;
import shoppyapp.util.DbUtil;
import shoppyapp.util.LoggerUtil;

import java.util.List;
import java.util.Optional;

public class ChatMessageRepository {

  private final EntityManager entityManager = DbUtil.getEntityManager();

  public ChatMessageRepository() {

  }

  public Optional<ChatMessage> findById(Long id) {
    ChatMessage chatMessage = entityManager.find(ChatMessage.class, id);
    return chatMessage != null ? Optional.of(chatMessage) : Optional.empty();
  }

  public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
    return entityManager.createQuery("SELECT cm FROM ChatMessage cm WHERE cm.chatRoom.id = :chatRoomId", ChatMessage.class)
            .setParameter("chatRoomId", chatRoomId)
            .getResultList();
  }

  public List<ChatMessage> findAll() {
    return entityManager.createQuery("SELECT cm FROM ChatMessage cm", ChatMessage.class)
            .getResultList();
  }

  public void save(ChatMessage chatMessage) {
    try {
      if(chatMessage.getId() == null) {
        entityManager.getTransaction().begin();
        entityManager.persist(chatMessage);
        entityManager.getTransaction().commit();
      }
      else {
        entityManager.getTransaction().begin();
        entityManager.merge(chatMessage);
        entityManager.getTransaction().commit();
      }
    }
    catch (Exception e) {
      if(entityManager.getTransaction().isActive()) {
        if(entityManager.getTransaction().isActive()) {
          entityManager.getTransaction().rollback();
        }
      }
      LoggerUtil.logError("Error saving chat message: " + e.getMessage());
    }
  }

  public void deleteById(Long id) {
    Optional<ChatMessage> chatMessageOptional = findById(id);

    if(chatMessageOptional.isPresent()) {
      ChatMessage chatMessage = chatMessageOptional.get();

      try {
        entityManager.getTransaction().begin();
        entityManager.remove(chatMessage);
        entityManager.getTransaction().commit();
      }
      catch (Exception e) {
        if(entityManager.getTransaction().isActive()) {
          if(entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
          }
        }
        LoggerUtil.logError("Error deleting chat message: " + e.getMessage());
      }
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
