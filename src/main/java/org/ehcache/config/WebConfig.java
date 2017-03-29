package org.ehcache.config;

import spark.Request;
import spark.Response;
import org.ehcache.generator.Person;
import org.ehcache.service.SomeService;

import static spark.Spark.get;

public class WebConfig {

  private static final String USER_SESSION_ID = "user";
  private SomeService service;


  public WebConfig(SomeService service) {
    this.service = service;
    setupRoutes();
  }

  private void setupRoutes() {
    get("/read/:id", (Request request, Response response) -> {
      try {
        long start = System.currentTimeMillis();
        Person val = service.someLogic(Long.valueOf(request.params(":id")));
        long end = System.currentTimeMillis();
        return val.toString() + "<br/>\n (this execution took " + (end - start) + "ms).";
      } catch (NumberFormatException e) {
        return "You must enter a Long as the key to find a Person";
      }
    });

  }

}
