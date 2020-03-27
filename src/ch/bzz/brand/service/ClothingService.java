package ch.bzz.brand.service;

import ch.bzz.brand.data.DataHandler;
import ch.bzz.brand.model.Brand;
import ch.bzz.brand.model.Clothing;
import ch.bzz.brand.model.Designer;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;


@Path("clothing")
public class ClothingService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listClothes() {
        Map<String, Clothing> clothingMap = new Brand().getClothingMap();
        return Response
                .status(200)
                .entity(clothingMap)
                .build();
    }


    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readClothing(
            @QueryParam("clothingId") String clothingId
    ) {
        int httpStatus;

        Clothing clothing = new Brand().getClothingById(clothingId);
        if (clothing != null) {
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }

        return Response
                .status(httpStatus)
                .entity(clothing)
                .build();
    }


    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createClothing(
            @FormParam("name") String name,
            @FormParam("color") String color,
            @FormParam("designerUUID") String designerUUID,
            @FormParam("price") BigDecimal price
    ) {
        int httpStatus = 200;

        Clothing clothing = new Clothing();
        clothing.setUUID(UUID.randomUUID().toString());
        clothing.setName(name);
        clothing.setColor(color);
        clothing.setPrice(price);

        Designer designer = DataHandler.getDesignerMap().get(designerUUID);
        clothing.setDesigner(designer);

        Brand brand = new Brand();
        brand.getClothingMap().put(clothing.getUUID(), clothing);

        DataHandler.writeClothes(brand.getClothingMap());


        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }


//    @PUT
//    @Path("update")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response updateBook(
//            @Valid @BeanParam Book book,
//            @FormParam("publisherUUID") String publisherUUID
//    ) {
//        int httpStatus = 200;
//
//        Bookshelf bookshelf = new Bookshelf();
//        if (bookshelf.getBookMap().containsKey(book.getBookUUID())) {
//            Publisher publisher = DataHandler.getPublisherMap().get(publisherUUID);
//            book.setPublisher(publisher);
//            bookshelf.getBookMap().put(book.getBookUUID(), book);
//            DataHandler.writeBooks(bookshelf.getBookMap());
//        } else {
//            httpStatus = 404;
//        }
//
//        Response response = Response
//                .status(httpStatus)
//                .entity("")
//                .build();
//        return response;
//    }
//
//
//    @DELETE
//    @Path("delete")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response deleteBook(
//            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
//            @QueryParam("uuid") String bookUUID
//    ) {
//        int httpStatus;
//
//        Bookshelf bookshelf = new Bookshelf();
//        Book book = bookshelf.getBookByUUID(bookUUID);
//        if (book != null) {
//            httpStatus = 200;
//            bookshelf.getBookMap().remove(book);
//            DataHandler.writeBooks(bookshelf.getBookMap());
//        } else {
//            httpStatus = 404;
//        }
//
//        Response response = Response
//                .status(httpStatus)
//                .entity("")
//                .build();
//        return response;
//    }
}
