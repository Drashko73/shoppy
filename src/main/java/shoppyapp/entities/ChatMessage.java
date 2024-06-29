package shoppyapp.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User sender;

  @Column(name = "message", nullable = false, length = 2000)
  private String message;

  @Column(name = "timestamp", nullable = false)
  private LocalDateTime timestamp;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_room_id", nullable = false)
  private ChatRoom chatRoom;

  // Constructors
  public ChatMessage() {
  }

  public ChatMessage(User sender, String message, LocalDateTime timestamp, ChatRoom chatRoom) {
    this.sender = sender;
    this.message = message;
    this.timestamp = timestamp;
    this.chatRoom = chatRoom;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getSender() {
    return sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public ChatRoom getChatRoom() {
    return chatRoom;
  }

  public void setChatRoom(ChatRoom chatRoom) {
    this.chatRoom = chatRoom;
  }

  // toString
  @Override
  public String toString() {
    return "ChatMessage{" +
            "id=" + id +
            ", sender=" + sender +
            ", message='" + message + '\'' +
            ", timestamp=" + timestamp +
            ", chatRoom=" + chatRoom +
            '}';
  }

}
