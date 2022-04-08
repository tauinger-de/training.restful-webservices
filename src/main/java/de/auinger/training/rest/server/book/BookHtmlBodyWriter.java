package de.auinger.training.rest.server.book;

import org.javacream.books.warehouse.api.Book;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces(MediaType.TEXT_HTML)
public class BookHtmlBodyWriter implements MessageBodyWriter<Book> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.isAssignableFrom(Book.class);
    }

    @Override
    public void writeTo(Book book, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
        String output = "<h1>" + book.getTitle() + "</h1>" +
                "<p>" + new String(book.getContent()) + "</p>";
        entityStream.write(output.getBytes());
    }

}
