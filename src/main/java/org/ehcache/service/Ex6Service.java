package org.ehcache.service;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.repository.SomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;

/**
 * Example service : Cache sizing
 * <p>
 * See http://www.ehcache.org/documentation/3.0/107.html
 */

@Service
public class Ex6Service implements SomeService {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.jsoftbiz.Demo");

  private SomeRepository repository = new SomeRepository();
  private Cache<String, String> cache;

  public Ex6Service() {
    // TODO : get Ehcache as caching provider ("org.ehcache.jsr107.EhcacheCachingProvider")

    CacheManager cacheManager; // TODO : Get javax.cache.CacheManager from caching provider

    CacheConfiguration<String, String> ehcacheConfiguration; //TODO Create an Ehcache configuration with a heap resource of 10000 elements

    MutableConfiguration<String, String> configuration; // TODO : create Mutableconfiguration from the Ehcache configuration using Eh107Configuration.fromEhcacheCacheConfiguration

    // TODO Create Cache
  }

  @Override
  public String someLogic(final String id) {
    LOGGER.debug("---> Call to service 6");

    // TODO implement the caching pattern you want
    String val = repository.readFromDb(id);
    return val;
  }
}
