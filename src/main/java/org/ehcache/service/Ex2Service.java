package org.ehcache.service;

import org.ehcache.generator.Person;
import org.ehcache.repository.SomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

/**
 * Example service : Cache through
 * <p>
 * Please implement TODO lines
 */

@Service
public class Ex2Service implements PersonService {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.ehcache.Demo");

  private SomeRepository repository = new SomeRepository();
  private Cache<Long, Person> cache;

  public Ex2Service() {
    // TODO : Create cache which uses a cache loader org.ehcache.service.SomeCacheLoader
  }

  @Override
  public Person loadPerson(final Long id) {
    LOGGER.debug("---> Call to service 2");

    // TODO : Get the value from the cache directly
    return null;
  }
}
