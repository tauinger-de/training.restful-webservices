package de.auinger.training.rest.server.book;

import de.auinger.training.rest.server.common.ApiException;
import org.javacream.books.warehouse.api.BookException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BookExceptionMapper implements ExceptionMapper<BookException> {

    @Override
    public Response toResponse(BookException exception) {
        switch (exception.type) {
            case NOT_FOUND:
                return ApiException.createResponse(exception.getMessage(), Response.Status.NOT_FOUND);
            case CONSTRAINT:
                return ApiException.createResponse(exception.getMessage(), Response.Status.BAD_REQUEST);
            default:
                return ApiException.createResponse(exception.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
