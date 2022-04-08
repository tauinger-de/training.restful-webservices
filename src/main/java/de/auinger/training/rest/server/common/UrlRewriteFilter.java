package de.auinger.training.rest.server.common;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Allows shortening the resource-name "books" with just a "b", e.g. "/api/rest/b/ISBN1"
 */
@PreMatching
@Provider
public class UrlRewriteFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        // resource-path is something like "/books/ISBN1" (it is already without the "/rest/api" part)
        String resourcePath = requestContext.getUriInfo().getPath();

        // obtain resource name and do replacement
        String resourceName = StringUtils.substringBefore(resourcePath, "/");
        if (resourceName.equals("b")) {
            resourceName = "books";
        }

        // build new URI
        String pathAfterResourceName = StringUtils.removeStart(resourcePath, resourceName);
        String newPath = "/rest/api/" + resourceName + pathAfterResourceName; // TODO maybe improvable using baseURI
        try {
            requestContext.setRequestUri(new URI(newPath));
        } catch (URISyntaxException ignored) {
        }
    }
}
