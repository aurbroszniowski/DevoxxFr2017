package org.ehcache.config;

import org.ehcache.service.SomeService;

import static spark.Spark.get;

public class WebConfig {
	
	private static final String USER_SESSION_ID = "user";
	private SomeService service;
	 

	public WebConfig(SomeService service) {
		this.service = service;
//		staticFileLocation("/public");
		setupRoutes();
	}
	
	private void setupRoutes() {
		get("/read/:id", (request, response) -> {
			long start = System.currentTimeMillis();
			String val = service.someLogic(request.params(":id"));
			long end = System.currentTimeMillis();
			return val + " (this execution took " + (end - start) + "ms).";
		});

	}

}
