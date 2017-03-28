package org.ehcache.service;

import org.ehcache.repository.SomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.net.URISyntaxException;

/**
 * Example service : Cache aside with sized cache
 */

@Service
public class Ex5Service implements SomeService {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.ehcache.Demo");

  private SomeRepository repository = new SomeRepository();
  private Cache<String, String> cache;

  public Ex5Service() throws URISyntaxException {
    CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");

    CacheManager cacheManager = cachingProvider.getCacheManager(
        getClass().getResource("/ehcache-ex5.xml").toURI(),
        getClass().getClassLoader());
    cache = cacheManager.getCache("someCache5", String.class, String.class);
  }

  @Override
  public String someLogic(final String id) {
    String value = cache.get(id);
    if (value != null) {
      return value;
    }
    value = repository.readFromDb(id);
    cache.put(id, value);
    return value;
  }
}















