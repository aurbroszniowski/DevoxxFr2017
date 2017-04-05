package org.ehcache.service;

import org.ehcache.generator.Person;
import org.ehcache.repository.SomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

/**
 * Example service : Cache aside
 * <p>
 * Please implement TODO lines
 */

@Service
public class Ex1Service implements PersonService {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.ehcache.Demo");

  private SomeRepository repository = new SomeRepository();
  private Cache<Long, Person> cache;

  public Ex1Service() {
    // TODO : get Ehcache as caching provider ("org.ehcache.jsr107.EhcacheCachingProvider")

    // TODO : Get javax.cache.CacheManager from caching provider

    // TODO Create Cache
  }

  @Override
  public Person loadPerson(final Long id) {
    LOGGER.debug("---> Call to service 1");

    // TODO implements Cache Aside pattern to cache the call to the repository
    // pattern :
    // is value in cache for the id?
    // if yes -> return value
    // if not -> read it from the repository
    // then put it in the cache
    // then return it
    return null;
  }
}















