package shoppyapp.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

  /**
   * The cost factor for BCrypt algorithm that determines the complexity of the hash computation.
   * The higher the cost, the more hashing rounds are performed, increasing the workload and time needed to compute the hash.
   * The cost factor is a balance between security and performance. A higher number means better security but slower hash computations.
   */
  private static final int BCRYPT_COST = 12;

  /**
   * This method is used to hash a plain text password using BCrypt.
   * BCrypt automatically handles the creation of salt and combines it with the password.
   * The salt is a random value, and along with the cost factor, it gets stored in the resulting hash string.
   * Therefore, each call to this method will produce a different hash.
   *
   * @param password The plain text password that needs to be hashed.
   * @return The hashed password.
   */
    public static String hashPassword(String password) {
      return BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_COST));
    }

  /**
   * This method is used to verify a plain text password against a hashed password using BCrypt.
   * BCrypt's checkpw method takes a plain text password and a hashed password as input.
   * It hashes the plain text password with the salt extracted from the hashed password and then compares the two hashes.
   * If they match, it returns true, indicating that the password is correct.
   *
   * @param password The plain text password that needs to be verified.
   * @param hashedPassword The hashed password against which the plain text password needs to be verified.
   * @return A boolean indicating whether the plain text password matches the hashed password.
   */
    public static boolean verifyPassword(String password, String hashedPassword) {
      return BCrypt.checkpw(password, hashedPassword);
    }
}
