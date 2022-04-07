package de.auinger.training.rest.server;

import javax.enterprise.inject.Produces;
import org.javacream.books.warehouse.api.BooksService;
import org.javacream.books.warehouse.impl.MapBooksService;

public class BeanProducer {

    @Produces
    public BooksService booksService() {
        return new MapBooksService();
    }
}
