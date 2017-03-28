package org.ehcache.service;

import org.ehcache.core.internal.service.ServiceLocator;
import org.ehcache.management.ManagementRegistryService;
import org.ehcache.repository.SomeRepository;
import org.springframework.stereotype.Service;
import org.terracotta.management.model.context.Context;
import org.terracotta.management.model.stats.ContextualStatistics;
import org.terracotta.management.registry.ResultSet;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.lang.reflect.Field;
import java.net.URISyntaxException;

/**
 * Example service : Cache aside with sized cache
 */

@Service
public class Ex5Service implements SomeService {

  private SomeRepository repository = new SomeRepository();
  private Cache<String, String> cache;
  private final ManagementRegistryService managementRegistry;

  public Ex5Service() throws URISyntaxException {
    CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");

    CacheManager cacheManager = cachingProvider.getCacheManager(
        getClass().getResource("/ehcache-ex5.xml").toURI(),
        getClass().getClassLoader());
    cache = cacheManager.getCache("someCache5", String.class, String.class);
    managementRegistry = getManagementRegistryService(cacheManager);
  }

  public void dumpCounters() {
    ContextualStatistics contextualStatistics = queryStats();

    System.out.printf("[Heap]");
    System.out.printf("  HitCount : %7d  MissCount : %7d  EvictionCount : %7d    ",
        contextualStatistics.getStatistic("OnHeap:HitCount").longValue(),
        contextualStatistics.getStatistic("OnHeap:MissCount").longValue(),
        contextualStatistics.getStatistic("OnHeap:EvictionCount").longValue()
    );
    System.out.printf("[OffHeap]");
    System.out.printf("  HitCount : %7d  MissCount : %7d  EvictionCount : %7d    ",
        contextualStatistics.getStatistic("OffHeap:HitCount").longValue(),
        contextualStatistics.getStatistic("OffHeap:MissCount").longValue(),
        contextualStatistics.getStatistic("OffHeap:EvictionCount").longValue()
    );
    System.out.printf("[Disk]");
    System.out.printf("  HitCount : %7d  MissCount : %7d  EvictionCount : %7d    ",
        contextualStatistics.getStatistic("Disk:HitCount").longValue(),
        contextualStatistics.getStatistic("Disk:MissCount").longValue(),
        contextualStatistics.getStatistic("Disk:EvictionCount").longValue()
    );
    System.out.println();
  }

  private ContextualStatistics queryStats() {
    Context context = Context.empty()
        .with("cacheManagerName", "someCacheManager5")
        .with("cacheName", "someCache5");

    ResultSet<ContextualStatistics> executeQuery = managementRegistry.withCapability("StatisticsCapability")
        .queryAllStatistics()
        .on(context)
        .build()
        .execute();

    return executeQuery.getResult(context);
  }

  private ManagementRegistryService getManagementRegistryService(CacheManager cacheManager) {
    try {
      org.ehcache.CacheManager unwrap = cacheManager.unwrap(org.ehcache.CacheManager.class);
      Field serviceLocatorField = org.ehcache.core.EhcacheManager.class.getDeclaredField("serviceLocator");
      serviceLocatorField.setAccessible(true);
      ServiceLocator result = (ServiceLocator) serviceLocatorField.get(unwrap);
      return result.getService(ManagementRegistryService.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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















