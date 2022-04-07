package de.auinger.training.rest.server.book;

import org.javacream.books.warehouse.api.Book;
import org.javacream.books.warehouse.api.BooksService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/books")
public class BooksResource {

    @Inject
    private BooksService booksService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return new ArrayList<>(booksService.findAllBooks());
    }
}
