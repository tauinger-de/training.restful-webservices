package de.auinger.training.rest.server.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

/**
 * This class exists since the glassfish-jersey 2.x doesn't seem to include the given message in the output when
 * dealing with/rendering a WebApplicationException.
 * <p>
 * See https://stackoverflow.com/questions/45952171/webapplicationexception-is-not-showing-the-error-message/45954257#45954257
 */
public class ApiException extends WebApplicationException {

    public ApiException(String message, Response.Status status) {
        super(createResponse(message, status));
    }

    public static Response createResponse(String message, Response.Status status) {
        return Response
                .status(status)
                .entity(getErrorJson(message, status))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static String getErrorJson(String message, Response.Status status) {
        String errorJson;
        try {
            ErrorResponse errorResponse = new ErrorResponse(message, status);
            errorJson = new ObjectMapper().writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            System.err.println(e);
            errorJson = "";
        }
        return errorJson;
    }

    public static class ErrorResponse {
        public int statusCode;
        public String status;
        public String message;
        public String dateTime;

        public ErrorResponse(String message, Response.Status status) {
            this.statusCode = status.getStatusCode();
            this.status = status.getReasonPhrase();
            this.message = message;
            this.dateTime = LocalDateTime.now().toString();
        }
    }
}
