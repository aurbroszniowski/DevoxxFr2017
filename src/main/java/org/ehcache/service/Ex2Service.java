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
public class Ex2Service implements SomeService {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.ehcache.Demo");

  private SomeRepository repository = new SomeRepository();
  private Cache<Long, Person> cache;

  public Ex2Service() {
    CachingProvider provider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
    CacheManager cacheManager = provider.getCacheManager();

    MutableConfiguration<Long, Person> configuration = new MutableConfiguration<>();
    configuration.setTypes(Long.class, Person.class);
    configuration.setCacheLoaderFactory(new FactoryBuilder.ClassFactory<>("org.ehcache.service.SomeCacheLoader"));
    configuration.setReadThrough(true);
    cache = cacheManager.createCache("someCache2", configuration);
  }

  @Override
  public Person someLogic(final Long id) {
    LOGGER.debug("---> Call to service 2");

    // TODO : Get the value from the cache directly instead
    Person val = cache.get(id);
    return val;
  }
}
