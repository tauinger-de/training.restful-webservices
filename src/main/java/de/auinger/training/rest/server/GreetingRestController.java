package de.auinger.training.rest.server;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/greeting")
public class GreetingRestController {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getGreeting() {
		return generateGreeting();
	}

	private String generateGreeting() {
		switch (new Random().nextInt(3)) {
		case 0:
			return "Hello";
		case 1:
			return "Que tal";
		case 2:
		default:
			return "Guten Tag";
		}
	}
}
