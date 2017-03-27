package org.ehcache.service;

/**
 * Cache performance test
 */
public class Ex8Service implements SomeService {
  @Override
  public String someLogic(final String id) {
    //TODO : use Exercise 2 (Cache aside) or Exercise 3 (cache through) code. And execute the Performance Test
    //      "PerfTest".
    //      the resulting report will be available in the directory "target/rainfall-report"

    // TODO : Second step is running several times the performance test : Each one for each available jsr107 providers :
    //     Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
    //     Caching.getCachingProvider("com.hazelcast.cache.HazelcastCachingProvider");
    //     Caching.getCachingProvider("org.apache.ignite.cache.CachingProvider");


    return null;
  }
}
