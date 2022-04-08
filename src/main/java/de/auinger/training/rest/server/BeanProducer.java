package de.auinger.training.rest.server;

import org.javacream.books.isbngenerator.impl.RandomIsbnGenerator;
import org.javacream.books.warehouse.api.BooksService;
import org.javacream.books.warehouse.impl.MapBooksService;
import org.javacream.store.api.StoreService;
import org.javacream.store.impl.StoreServiceImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped    // makes all beans here be created as singletons
public class BeanProducer {

    // --- initialize and store dependencies:

    private final StoreServiceImpl storeService;
    private final MapBooksService booksService;

    private BeanProducer() {
        storeService = new StoreServiceImpl();

        RandomIsbnGenerator isbnGenerator = new RandomIsbnGenerator();
        isbnGenerator.setPrefix("ISBN-");
        isbnGenerator.setSuffix("-de");

        booksService = new MapBooksService();
        booksService.setStoreService(storeService);
        booksService.setIsbnGenerator(isbnGenerator);
    }

    // --- make dependencies known to CDI context:

    @Produces
    public BooksService booksService() {
        return booksService;
    }

    @Produces
    public StoreService storeService() {
        return storeService;
    }
}
