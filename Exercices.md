# Performance through caching
# Workshop Devoxx Fr 2017

# Exercices

L'URL de test est :

[http://localhost:4567/read/something](http://localhost:4567/read/something)


Vous pouvez aussi démarrer l'application depuis votre Editeur en exécutant la classe ```org.ehcache.web.ExampleApp```

Vous trouverez dans l'interface ```org.ehcache.service.SomeService``` la liste des exercises.
Chaque exercice est représenté par une classe ``org.ehcache.service.ExXService``` dans laquelle vous rajouterez le code nécessaire.

Les commentaires TODO sont là pour vous aider à compléter les exercices.

Pour injecter le service correspondant à l'exercice en cours, il vous faudra changer la référence dans la classe ExampleApp:

```private static Class<? extends SomeService> serviceClass = Ex1Service.class;```

Par défaut, le Service est Ex1Service, qui représente la version sans cache.

Pour avoir accès à la documentation, veuillez consulter les liens suivants:
 
[http://www.ehcache.org/documentation/3.3/107.html](http://www.ehcache.org/documentation/3.3/107.html)

[https://github.com/jsr107/jsr107spec/tree/master/src/main/java/javax/cache](https://github.com/jsr107/jsr107spec/tree/master/src/main/java/javax/cache)

---

Voici la liste des exercices que nous aborderons:

Exercice 1. Cache pattern : Cache aside
Utiliser l'API JCache afin d'intégrer un cache à l'application.
Utiliser la pattern Cache Aside.

Exercice 2. Cache pattern : Cache through
Utiliser la pattern Cache Through.

Exercice 3. Tests de performances de votre application.
Lancer différents tests de charges pour mesurer les gains de performances (volume de 100, 10000 et 1000000 de données).

Exercice 4. Les statistiques.
Utiliser les statistiques jsr107 dans le cadre des tests de performance.

Exercice 5. Cache size
Le sizing du cache et jsr107. Solutions des providers JCache.

Exercice 6. Warmup et persistance
Le warmup.
Disk persistency

Exercice 7. Plus de perf : les serializers
Quand, comment et pourquoi les serializers sont utilises, leur impact sur les perf et comment les optimiser

Exercice 8. Configuration du cache et trade-offs
Les trade-offs dans le caching (ARC, expiration avec TTI/TTL)

