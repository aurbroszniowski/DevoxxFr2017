# Performance through caching
# Workshop Devoxx Fr 2017


Ce projet utilise le framework Web Spark qui nécessite le jdk 1.8.

Pour lire les rapport de performance, préférez Firefox, ou chrome en le démarrant avec la ligne de commande suivante:
```chrome.exe --allow-file-access-from-files```

Veuillez cloner ce projet avant le Hands on Lab, et faire un build initial afin d'être sûr de récupérer toutes les dépendances
 
```mvn clean install```

Pour démarrer l'application, exécutez la ligne de commande

```mvn clean package exec:java```

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
Le warmup.
Lancer différents tests de charges pour mesurer les gains de performances (volume de 100, 10000 et 1000000 de données).

Exercice 4. Les statistiques.
Utiliser les statistiques jsr107 dans le cadre des tests de performance.

Exercice 5. Cache size
Le sizing du cache et jsr107. Solutions des providers JCache.

Exercice 6. Configuration du cache
Les trade-offs dans le caching (consistency, eviction, expiration (TTI/TTL))

Exercice 7. Plus de perf : les serializers / copiers
