package org.ehcache.service;

import org.ehcache.generator.Person;
import org.ehcache.repository.SomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

/**
 * Example service : Cache aside with sized cache
 */

@Service
public class Ex3Service implements PersonService {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.ehcache.Demo");

  private SomeRepository repository = new SomeRepository();
  private Cache<Long, Person> cache;

  public Ex3Service() throws URISyntaxException {
    CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");

    CacheManager cacheManager = cachingProvider.getCacheManager(
        getClass().getResource("/ehcache-ex3.xml").toURI(),
        getClass().getClassLoader());
    cache = cacheManager.getCache("someCache3", Long.class, Person.class);
  }

  @Override
  public Person loadPerson(final Long id) {
    Person value = cache.get(id);
    if (value != null) {
      return value;
    }
    value = repository.readFromDb(id);
    cache.put(id, value);
    return value;
  }
}















