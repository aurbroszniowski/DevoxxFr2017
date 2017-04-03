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
 *
 * Please implement TODO lines
 *
 */

@Service
public class Ex1Service implements PersonService {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.ehcache.Demo");

  private SomeRepository repository = new SomeRepository();
  private Cache<Long, Person> cache;

  public Ex1Service() {
    // TODO : get Ehcache as caching provider ("org.ehcache.jsr107.EhcacheCachingProvider")
    CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");

    // TODO : Get javax.cache.CacheManager from caching provider
    CacheManager cacheManager = cachingProvider.getCacheManager();

    MutableConfiguration<Long, Person> configuration = new MutableConfiguration<>();
    configuration.setTypes(Long.class, Person.class);
    // TODO Create Cache
    cache = cacheManager.createCache("someCache1", configuration);
  }

  @Override
  public Person loadPerson(final Long id) {
    LOGGER.debug("---> Call to service 1");

    // TODO implements Cache Aside pattern to cache the call to the repository
    // pattern :

    // is value in cache for the id?
    Person value = cache.get(id);
    if (value != null){
      // if yes -> return value
      return value;
    }
    // if not -> read it from the repository
    value = repository.readFromDb(id);
    // then put it in the cache
    cache.put(id, value);
    // then return it
    return value;
  }
}















