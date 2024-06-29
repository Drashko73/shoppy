package shoppyapp.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DbUtil {

  private static final String PERSISTENCE_UNIT_NAME = "shoppy-pu";
  private static EntityManagerFactory factory;

  static {
    try {
      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

      EntityManager entityManager = factory.createEntityManager();
      entityManager.getTransaction().begin(); // Begin the transaction
      entityManager.createNativeQuery("SELECT 1").getSingleResult();  // Ensure initialisation
      entityManager.getTransaction().commit();  // Commit the transaction
      entityManager.close();
    } catch (Exception e) {
      System.err.println("An error occurred during database initialization: " + e.getMessage());
      e.printStackTrace();
    }
  }

    public static EntityManager getEntityManager() {
      return factory.createEntityManager();
    }

}
