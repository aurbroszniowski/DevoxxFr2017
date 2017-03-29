package org.ehcache.service;

import org.ehcache.generator.Person;
import org.ehcache.repository.SomeRepository;

import java.util.Map;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;

/**
 * @author Aurelien Broszniowski
 */
public class SomeCacheLoader implements CacheLoader<Long, Person> {

  private SomeRepository repository = new SomeRepository();

  @Override
  public Person load(final Long id) throws CacheLoaderException {
    Person val = repository.readFromDb(id);
    return val;
  }

  @Override
  public Map<Long, Person> loadAll(final Iterable<? extends Long> iterable) throws CacheLoaderException {
    return null;
  }
}
