package de.auinger.training.rest.server.greeting;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/greeting")
public class GreetingResource {

    // jax-rs creates an instance of this class FOR EACH REQUEST -- hence we can do stuff like this:
    @QueryParam("firstName")
    String firstNameFromQuery;
    @HeaderParam("X-firstName")
    String firstNameFromHeader;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getGreeting() {
        // which name to use? header wins over query!
        String firstName = (firstNameFromHeader != null) ? firstNameFromHeader : firstNameFromQuery;
        return generateGreeting(firstName);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{firstName}")
    public String getGreetingUsingPath(@PathParam("firstName") String firstName) {
        return generateGreeting(firstName);
    }

    private String generateGreeting(String firstName) {
        String greeting;
        switch (new Random().nextInt(3)) {
            case 0:
                greeting = "Hello";
                break;
            case 1:
                greeting = "Que tal";
                break;
            case 2:
            default:
                greeting = "Guten Tag";
        }
        if (firstName != null && !firstName.isEmpty()) {
            greeting = greeting + " " + firstName;
        }
        return greeting;
    }
}
