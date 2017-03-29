package org.ehcache.service;

import org.ehcache.generator.Person;
import org.ehcache.repository.SomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Example service
 */
@Service
public class Ex0Service implements SomeService {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.ehcache.Demo");

  private SomeRepository repository = new SomeRepository();

  @Override
  public Person someLogic(final Long id) {
    LOGGER.debug("---> Call to service 1");
    return repository.readFromDb(id);
  }
}
