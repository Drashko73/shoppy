package shoppyapp.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 100, unique = true)
  private String name;

  @Column(name = "description", nullable = false, length = 2000)
  private String description;

  @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ChatMessage> messages;

  // Constructors
  public ChatRoom() {
  }

  public ChatRoom(String name, String description) {
    this.name = name;
    this.description = description;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ChatMessage> getMessages() {
    return messages;
  }

  public void setMessages(List<ChatMessage> messages) {
    this.messages = messages;
  }

  // toString
  @Override
  public String toString() {
    return "ChatRoom{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
  }

}
