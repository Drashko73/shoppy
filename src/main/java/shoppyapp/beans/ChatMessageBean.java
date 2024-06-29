package shoppyapp.beans;

import java.io.Serializable;

public class ChatMessageBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private Long senderId;
  private String message;
  private String timestamp;
  private Long chatRoomId;

  public ChatMessageBean() {
  }

  public ChatMessageBean(Long id, Long senderId, String message, String timestamp, Long chatRoomId) {
    this.id = id;
    this.senderId = senderId;
    this.message = message;
    this.timestamp = timestamp;
    this.chatRoomId = chatRoomId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public Long getChatRoomId() {
    return chatRoomId;
  }

  public void setChatRoomId(Long chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  @Override
  public String toString() {
    return "ChatMessageBean{" +
            "id=" + id +
            ", senderId=" + senderId +
            ", message='" + message + '\'' +
            ", timestamp='" + timestamp + '\'' +
            ", chatRoomId=" + chatRoomId +
            '}';
  }

}
