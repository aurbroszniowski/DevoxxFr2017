package org.ehcache.service;

/**
 * Exercises:
 *
 * - cache aside : Ex2Service
 * - cache through (loader only) : Ex4Service
 * - statistics : Ex5Service
 * - performance testing : Ex8Service
 * - comparison jsr107 providers : Ex8Service
 * - ehcache capacity : Ex6Service
 * - ehcache topology : Ex7Service
 *
 */

public interface SomeService {

  String someLogic(String id);

}
