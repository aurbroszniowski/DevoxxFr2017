package org.ehcache.service;

import org.ehcache.core.internal.service.ServiceLocator;
import org.ehcache.generator.Person;
import org.ehcache.management.ManagementRegistryService;
import org.ehcache.repository.SomeRepository;
import org.springframework.stereotype.Service;
import org.terracotta.management.model.context.Context;
import org.terracotta.management.model.stats.ContextualStatistics;
import org.terracotta.management.registry.ResultSet;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

/**
 * Example service : Cache aside with sized cache with custom Serializer and Copier
 */

@Service
public class Ex7Service implements SomeService {

  private SomeRepository repository = new SomeRepository();
  private Cache<Long, Person> cache;
  private final ManagementRegistryService managementRegistry;

  public Ex7Service() throws URISyntaxException {
    CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");

    CacheManager cacheManager = cachingProvider.getCacheManager(
        getClass().getResource("/ehcache-ex7.xml").toURI(),
        getClass().getClassLoader());
    cache = cacheManager.getCache("someCache7", Long.class, Person.class);
    // there unfortunately isn't a public API to read statistics at the moment, see:
    // https://github.com/ehcache/ehcache3/issues/1940
    managementRegistry = getManagementRegistryService(cacheManager);
  }

  public void dumpCounters() {
    ContextualStatistics contextualStatistics = queryStats();

    System.out.printf("[Cache]");
    System.out.printf("  HitCount : %7d  MissCount : %7d  EvictionCount : %7d    ",
        contextualStatistics.getStatistic("Cache:HitCount").longValue(),
        contextualStatistics.getStatistic("Cache:MissCount").longValue(),
        contextualStatistics.getStatistic("Cache:EvictionCount").longValue()
    );
    try {
      Number hitCount = contextualStatistics.getStatistic("OnHeap:HitCount");
      System.out.printf("[Heap]");
      System.out.printf("  HitCount : %7d  MissCount : %7d  EvictionCount : %7d    ",
          hitCount.longValue(),
          contextualStatistics.getStatistic("OnHeap:MissCount").longValue(),
          contextualStatistics.getStatistic("OnHeap:EvictionCount").longValue()
      );
    } catch (NoSuchElementException e) {
      // no heap
    }
    try {
      Number hitCount = contextualStatistics.getStatistic("OffHeap:HitCount");
      System.out.printf("[OffHeap]");
      System.out.printf("  HitCount : %7d  MissCount : %7d  EvictionCount : %7d    ",
          hitCount.longValue(),
          contextualStatistics.getStatistic("OffHeap:MissCount").longValue(),
          contextualStatistics.getStatistic("OffHeap:EvictionCount").longValue()
      );
    } catch (NoSuchElementException e) {
      // no offheap
    }
    try {
      Number hitCount = contextualStatistics.getStatistic("Disk:HitCount");
      System.out.printf("[Disk]");
      System.out.printf("  HitCount : %7d  MissCount : %7d  EvictionCount : %7d    ",
          hitCount.longValue(),
          contextualStatistics.getStatistic("Disk:MissCount").longValue(),
          contextualStatistics.getStatistic("Disk:EvictionCount").longValue()
      );
    } catch (NoSuchElementException e) {
      // no disk
    }
    System.out.println();
  }

  private ContextualStatistics queryStats() {
    Context context = Context.empty()
        .with("cacheManagerName", "someCacheManager7")
        .with("cacheName", "someCache7");

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
      ServiceLocator result = (ServiceLocator)serviceLocatorField.get(unwrap);
      return result.getService(ManagementRegistryService.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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















