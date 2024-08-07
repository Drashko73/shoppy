package shoppyapp.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import shoppyapp.config.Common;

public class DbUtil {

  private static final String PERSISTENCE_UNIT_NAME = Common.PERSISTENCE_UNIT_NAME;
  private static EntityManagerFactory factory;

  static {
    try {
      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

      EntityManager entityManager = factory.createEntityManager();
    } catch (Exception e) {
      System.err.println("An error occurred during database initialization: " + e.getMessage());
      e.printStackTrace();
    }
  }

    public static EntityManager getEntityManager() {
      return factory.createEntityManager();
    }

}
