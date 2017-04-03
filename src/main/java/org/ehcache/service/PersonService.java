package org.ehcache.service;

import org.ehcache.generator.Person;

public interface PersonService {

  Person loadPerson(Long id);

}
