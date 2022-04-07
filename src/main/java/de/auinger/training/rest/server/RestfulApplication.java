package de.auinger.training.rest.server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Having this allows us to not create a web.xml
 */
@ApplicationPath("/api")
public class RestfulApplication extends Application {
}
