package org.ehcache.service;

import org.ehcache.generator.Person;
import org.ehcache.repository.SomeRepository;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.management.CacheStatisticsMXBean;
import javax.cache.spi.CachingProvider;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;

/**
 * Example service : Cache aside with sized cache
 */

@Service
public class Ex4Service implements SomeService {

  private SomeRepository repository = new SomeRepository();
  private Cache<Long, Person> cache;
  private CacheStatisticsMXBean cacheStatisticsMXBean;

  public Ex4Service() throws URISyntaxException {
    CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");

    CacheManager cacheManager = cachingProvider.getCacheManager(
        getClass().getResource("/ehcache-ex4.xml").toURI(),
        getClass().getClassLoader());
    cache = cacheManager.getCache("someCache4", Long.class, Person.class);

    try {
      MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
      ObjectName objectName = new ObjectName("javax.cache:type=CacheStatistics,CacheManager="
          + getClass().getResource("/ehcache-ex4.xml")
          .toURI()
          .toString()
          .replace(":", ".") + ",Cache=someCache4");
      cacheStatisticsMXBean = MBeanServerInvocationHandler.newProxyInstance(beanServer, objectName, CacheStatisticsMXBean.class, false);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void dumpCounters() {
    System.out.printf("Cache Gets: %d, ", cacheStatisticsMXBean.getCacheGets());
    System.out.printf("Cache Miss count: %d, ", cacheStatisticsMXBean.getCacheMisses());
    System.out.printf("Cache Hit percentage: %f, ", cacheStatisticsMXBean.getCacheHitPercentage());
    System.out.printf("Cache Evictions count: %d\n", cacheStatisticsMXBean.getCacheEvictions());
  }

  @Override
  public Person someLogic(final Long id) {
    Person value = cache.get(id);
    if (value != null) {
      return value;
    }
    value = repository.readFromDb(id);
    cache.put(id, value);
    return value;
  }
}















