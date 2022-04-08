package de.auinger.training.rest.server.book;

import org.javacream.books.warehouse.api.Book;
import org.javacream.books.warehouse.api.BookException;
import org.javacream.books.warehouse.api.BookException.BookExceptionType;
import org.javacream.books.warehouse.api.BooksService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.net.URISyntaxException;
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

    @GET
    @Path("/{id:[0-9]+}")
    public void getBook(@PathParam("id") int bookId) throws BookException {
    	System.out.println("getBook() for id: " + bookId);
        // TODO add some useful call for just the id
    }

    @GET
    @Path("/{isbn}")
    @Produces(MediaType.TEXT_PLAIN) // overwrites annotation at class level
    public String getBookContent(@PathParam("isbn") String isbn) throws BookException {
        return new String(booksService.findBook(isbn).getContent());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    // for now, we just use "Book" as the container for incoming create requests - although it has too many fields
    public Response createBook(Book book) throws BookException, URISyntaxException {
        String isbn = booksService.newBook(book.getTitle());
        return Response
                .status(Status.CREATED)
                .location(new URI("http://localhost:8080/rest/api/books/" + isbn)) // this doesn't look ideal yet :)
                .build();
    }

    @DELETE
    @Path("/{isbn}")
    public Response deleteBook(@PathParam("isbn") String isbn) throws BookException {
        try {
            booksService.deleteBookByIsbn(isbn);
            return Response.noContent().build();
        } catch (BookException e) {
            // delete is idempotent, but we still don't want to return a server error 500
            if (e.type == BookExceptionType.NOT_DELETED || e.type == BookExceptionType.NOT_FOUND) {
                return Response.status(Status.NOT_FOUND).build();
            } else {
                throw e;
            }
        }
    }

    @PUT
    @Path("/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateBook(@PathParam("isbn") String isbn, UpdateBookRequest updateBookRequest) throws BookException {
        Book book = updateBookRequest.toBook();
        book.setIsbn(isbn);
        booksService.updateBook(book);
    }


    static class UpdateBookRequest {
        public String title;
        public List<String> keywords;
        public double price;
        public String content;

        Book toBook() {
            Book book = new Book(null, title, price, false, keywords);
            book.setContent(content.getBytes());
            return book;
        }
    }
}
