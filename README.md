# Performance through caching
# Workshop JBCNConf 2016


You need JAVA 1.8 installed

Also you will need a browser, Firefox works out of the bos but if you use Chrome you will need to start it with :
```chrome.exe --allow-file-access-from-files```

Please make sure to clone this project locally before attending to the workshop, and getting all the dependencies by doing a 
 
```mvn clean install```

To start the application, execute:

```mvn clean package exec:java```

Then access into the browser

[http://localhost:4567/read/something](http://localhost:4567/read/something)



You can start the application from your IDE, by executing the class ```org.jsoftbiz.web.ExampleApp```
It is easier to do this way when writing the exercises. It will help you for debugging too.

The class ```org.jsoftbiz.service.Service``` lists the exercises. 
For each exercise, you need to write code in one of the ``org.jsoftbiz.service.ExXService``` classes.

Look at the TODO lines. Each one needs to be implemented in order to complete the exercise.

Then you inject the exercise service in the ExampleApp, by changing the line:

```private static Class<? extends SomeService> serviceClass = Ex1Service.class;```

where you change the name of the service class. (By default, the Service is Ex1Service, which represented the non cached version)

You can get help from the following documentations:
 
[http://www.ehcache.org/documentation/3.0/107.html](http://www.ehcache.org/documentation/3.0/107.html)

[https://github.com/jsr107/jsr107spec/tree/master/src/main/java/javax/cache](https://github.com/jsr107/jsr107spec/tree/master/src/main/java/javax/cache)

---

1. Utiliser JCache
2. Cache pattern : Cache aside
3. Cache pattern : Cache through
4. base preprod (1000000 elt) -> test perf avec loads differents - 100 / 10000 / 1000000
5. base preprod (1000000 elt) -> investigation -> jsr107 stats (hit ratio est le pbm)
6. base preprod (1000000 elt) -> solution? (cache size)
7. config cache size etendue -> limitations jsr107 / solutions hazelcast/ignite/ehcache -> ex configuration du sizing et pbm rencontrés
8. perf / warmup / jitwatch - tradeoffs (consistency, eviction, TTI/TTL) - Pieges -> pas un store