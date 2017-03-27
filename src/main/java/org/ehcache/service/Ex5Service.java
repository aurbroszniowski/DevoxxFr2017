package org.ehcache.service;

import org.ehcache.repository.SomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import javax.management.ObjectName;

/**
 * Example service : Reading statistics
 *
 * Please implement TODO lines
 *
 */

@Service
public class Ex5Service implements SomeService {

  private static final Logger LOGGER = LoggerFactory.getLogger("org.jsoftbiz.Demo");

  private SomeRepository repository = new SomeRepository();

  private Cache<String, String> cache;
  private ObjectName objectName;

  public Ex5Service() {
    // TODO : Create a cache like in previous exercises. You can choose if you want to use the cache aside or the cache through pattern
  }

  @Override
  public String someLogic(final String id) {
      // TODO Get the statistics MBean. The Objectname is "javax.cache:type=CacheStatistics,CacheManager=urn.X-ehcache.jsr107-default-config,Cache=YOURCACHENAME"

      // TODO : Get the value for the attribute named "CacheHits". This represents the number of cache hits. You can display it.

    LOGGER.debug("---> Call to service 5");

    // TODO : Get the value from the cache if available. When it gets there, the CacheHits value will increase
    String val = repository.readFromDb(id);
    return val;
  }
}
