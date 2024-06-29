package shoppyapp.repositories;

import jakarta.persistence.EntityManager;
import shoppyapp.entities.User;
import shoppyapp.util.DbUtil;
import shoppyapp.util.LoggerUtil;

import java.util.Optional;

public class UserRepository {

  private final EntityManager entityManager = DbUtil.getEntityManager();

    public UserRepository() {
    }

    public Optional<User> findById(long id) {
        try {
            return Optional.of(entityManager.find(User.class, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByUsername(String username) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findBySessionID(String sessionID) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.sessionID = :sessionID", User.class)
                    .setParameter("sessionID", sessionID)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void save(User user) {
        try {
            entityManager.getTransaction().begin();
            if(user.getId() == 0) {
                entityManager.persist(user);
            } else {
                entityManager.merge(user);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void addUser(User user) {
      save(user);
    }

    public void close() {
      try {
        entityManager.close();
      } catch (Exception e) {
        LoggerUtil.logError("Error closing user repository: " + e.getMessage());
      }
    }

}
