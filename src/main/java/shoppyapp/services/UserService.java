package shoppyapp.services;

import shoppyapp.beans.UserBean;
import shoppyapp.entities.User;
import shoppyapp.repositories.UserRepository;
import shoppyapp.util.PasswordUtil;

import java.util.Optional;
import java.util.UUID;

public class UserService {

  private final UserRepository userRepository;

    public UserService() {
      this.userRepository = new UserRepository();
    }

    public Optional<UserBean> loginUser(String email, String password) {
      Optional<User> userOptional = userRepository.findByEmail(email);

      if(userOptional.isPresent()) {
        User user = userOptional.get();

        if(PasswordUtil.verifyPassword(password, user.getPassword())) {
          String sessionID = generateSessionID();
          user.setSessionID(sessionID);
          userRepository.save(user);

          return Optional.of(
                  new UserBean(
                          user.getId(),
                          user.getUsername(),
                          user.getPassword(),
                          user.getEmail(),
                          user.getFirstName(),
                          user.getLastName(),
                          user.isAdmin(),
                          user.isBanned(),
                          sessionID
                  )
          );
        }
      }

      return Optional.empty();
    }

    public Optional<UserBean> registerUser(String username, String password, String email, String firstName, String lastName) {
      Optional<User> userOptional = userRepository.findByEmail(email);
      Optional<User> userOptional2 = userRepository.findByUsername(username);

      if(userOptional.isPresent() || userOptional2.isPresent()) {
        return Optional.empty();
      }

      String hashedPassword = PasswordUtil.hashPassword(password);
      User user = new User(username, hashedPassword, email, firstName, lastName, false, false);
      String sessionID = generateSessionID();
      user.setSessionID(sessionID);
      userRepository.save(user);

      return Optional.of(
        new UserBean(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isAdmin(),
                user.isBanned(),
                user.getSessionID()
        )
      );
    }

    public void logoutUser(String sessionID) {
      Optional<User> userOptional = userRepository.findBySessionID(sessionID);

      if(userOptional.isPresent()) {
        User user = userOptional.get();
        user.setSessionID(null);
        userRepository.save(user);
      }
    }

    public void registerAdminUser(String username, String password, String email, String firstName, String lastName) {
      Optional<User> userOptional = userRepository.findByEmail(email);

      if(userOptional.isPresent()) {
        return;
      }

      String hashedPassword = PasswordUtil.hashPassword(password);
      User user = new User(username, hashedPassword, email, firstName, lastName, true, false);
      userRepository.save(user);
    }

    public boolean usernameExists(String username) {
      return userRepository.findByUsername(username).isPresent();
    }

    public Optional<UserBean> getUserBySessionId(String sessionId) {
      Optional<User> userOptional = userRepository.findBySessionID(sessionId);
      if(userOptional.isPresent()) {
        User user = userOptional.get();
        return Optional.of(
                new UserBean(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.isAdmin(),
                        user.isBanned(),
                        user.getSessionID()
                )
        );
      }

      return Optional.empty();
    }

    public Optional<UserBean> getUserById(Long userId) {
      Optional<User> userOptional = userRepository.findById(userId);

      if(userOptional.isPresent()) {
        User user = userOptional.get();
        return Optional.of(
                new UserBean(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.isAdmin(),
                        user.isBanned(),
                        user.getSessionID()
                )
        );
      }

      return Optional.empty();
    }

    private String generateSessionID() {
      return UUID.randomUUID().toString();
    }

}
