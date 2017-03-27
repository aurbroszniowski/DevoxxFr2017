package org.ehcache.service;

import org.ehcache.repository.SomeRepository;

import java.util.Map;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;

/**
 * @author Aurelien Broszniowski
 */
public class SomeCacheLoader implements CacheLoader<String, String> {

  private SomeRepository repository = new SomeRepository();

  @Override
  public String load(final String id) throws CacheLoaderException {
    String val = repository.readFromDb(id);
    return val;
  }

  @Override
  public Map<String, String> loadAll(final Iterable<? extends String> iterable) throws CacheLoaderException {
    return null;
  }
}
