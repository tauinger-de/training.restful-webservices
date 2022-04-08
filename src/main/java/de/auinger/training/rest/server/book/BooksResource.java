package de.auinger.training.rest.server.book;

import org.javacream.books.warehouse.api.Book;
import org.javacream.books.warehouse.api.BookException;
import org.javacream.books.warehouse.api.BooksService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BooksResource {

    @Inject
    private BooksService booksService;

    @GET
    public List<Book> getBooks() {
        return new ArrayList<>(booksService.findAllBooks());
    }
    
    @GET
    @Path("/{isbn}")
    public Book getBook(@PathParam("isbn") String isbn) throws BookException {
    	return booksService.findBook(isbn);
    }
}
