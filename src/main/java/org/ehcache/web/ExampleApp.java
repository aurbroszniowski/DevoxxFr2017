package org.ehcache.web;

import org.ehcache.config.WebConfig;
import org.ehcache.service.Ex0Service;
import org.ehcache.service.SomeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static spark.Spark.get;

/**
 * Example App
 */
@Configuration
@ComponentScan({ "org.ehcache" })
public class ExampleApp {

  private static Class<? extends SomeService> serviceClass = Ex0Service.class;

  public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ExampleApp.class);
    new WebConfig(ctx.getBean(serviceClass));
    ctx.registerShutdownHook();
  }

}
