package org.ehcache.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Some Repository
 */
public class SomeRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.jsoftbiz.Demo");

  Random rnd = new Random();

  /**
   * Simulates a DB write call - takes between 100ms and 1100ms
   *
   * @param value to be written
   * @return String written
   */
  public String readFromDb(String value) {
    LOGGER.debug(" Call to DB");
    try {
      Thread.sleep(100 + rnd.nextInt(1000));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    return "Hello " + value;
  }
}
