package ch.bzz.brand.service;

import ch.bzz.bookshelf.data.DataHandler;
import ch.bzz.bookshelf.model.Book;
import ch.bzz.bookshelf.model.Bookshelf;
import ch.bzz.bookshelf.model.Publisher;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;


@Path("clothing")
public class ClothingService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listBooks() {
        Map<String, Book> bookMap = new Bookshelf().getBookMap();
        Response response = Response
                .status(200)
                .entity(bookMap)
                .build();
        return response;

    }


    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readBook(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String bookUUID
    ) {
        int httpStatus;

        Book book = new Bookshelf().getBookByUUID(bookUUID);
        if (book != null) {
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity(book)
                .build();
        return response;
    }


    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createBook(
            @Valid @BeanParam Book book,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("publisherUUID") String publisherUUID
    ) {
        int httpStatus = 200;
        Bookshelf bookshelf = new Bookshelf();
        book.setBookUUID(UUID.randomUUID().toString());
        Publisher publisher = DataHandler.getPublisherMap().get(publisherUUID);
        book.setPublisher(publisher);

        bookshelf.getBookMap().put(book.getBookUUID(), book);
        DataHandler.writeBooks(bookshelf.getBookMap());

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }


    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @Valid @BeanParam Book book,
            @FormParam("publisherUUID") String publisherUUID
    ) {
        int httpStatus = 200;

        Bookshelf bookshelf = new Bookshelf();
        if (bookshelf.getBookMap().containsKey(book.getBookUUID())) {
            Publisher publisher = DataHandler.getPublisherMap().get(publisherUUID);
            book.setPublisher(publisher);
            bookshelf.getBookMap().put(book.getBookUUID(), book);
            DataHandler.writeBooks(bookshelf.getBookMap());
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }


    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String bookUUID
    ) {
        int httpStatus;

        Bookshelf bookshelf = new Bookshelf();
        Book book = bookshelf.getBookByUUID(bookUUID);
        if (book != null) {
            httpStatus = 200;
            bookshelf.getBookMap().remove(book);
            DataHandler.writeBooks(bookshelf.getBookMap());
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }
}
