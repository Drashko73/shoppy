package shoppyapp.socket;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import shoppyapp.entities.User;
import shoppyapp.services.UserService;
import shoppyapp.util.LoggerUtil;

import java.util.Map;

public class ChatServerConfigurator extends ServerEndpointConfig.Configurator {

  private final UserService userService = new UserService();

  @Override
  public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

    // Get user property from the request
    Map<String, Object> userProperties = sec.getUserProperties();
    String sessionId = request.getParameterMap().get("sessionId").get(0);
    User user = userService.getUserBySessionId(sessionId).orElse(null);

    if(user != null) {
      userProperties.put("user", user);
    }
    else {
      throw new IllegalStateException("User not found with session ID: " + sessionId);
    }

  }

}
