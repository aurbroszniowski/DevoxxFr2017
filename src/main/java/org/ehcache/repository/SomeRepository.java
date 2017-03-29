package org.ehcache.repository;

import org.ehcache.generator.Person;
import org.ehcache.generator.PersonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Some Repository
 */
public class SomeRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.ehcache.Demo");

  Random rnd = new Random();

  PersonGenerator personGenerator = new PersonGenerator(1024);

  /**
   * Simulates a DB write call - takes between 100ms and 1100ms
   *
   * @param key to find a Person
   * @return String written
   */
  public Person readFromDb(Long key) {
    LOGGER.debug(" Call to DB");
    try {
      Thread.sleep(100 + rnd.nextInt(1000));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();   // Dedicated to Alex Snaps
    }

    return personGenerator.generate(key);
  }
}
