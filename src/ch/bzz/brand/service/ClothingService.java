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
            @QueryParam("clothingUUID") String clothingUUID
    ) {
        int httpStatus;

        Clothing clothing = new Brand().getClothingByUUID(clothingUUID);
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


    @SuppressWarnings("Duplicates")
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createClothing(
            @FormParam("name") String name,
            @FormParam("color") String color,
            @FormParam("designerID") String designerID,
            @FormParam("price") BigDecimal price
    ) {
        int httpStatus = 200;

        Clothing clothing = new Clothing();
        clothing.setUUID(UUID.randomUUID().toString());
        clothing.setName(name);
        clothing.setColor(color);
        clothing.setPrice(price);

        if (DataHandler.getDesignerMap().containsKey(designerID)) {
            Designer designer = DataHandler.getDesignerMap().get(designerID);
            clothing.setDesigner(designer);

            Brand brand = new Brand();
            brand.getClothingMap().put(clothing.getUUID(), clothing);

            DataHandler.writeClothes(brand.getClothingMap());

        } else {
            httpStatus = 400;
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }


    @SuppressWarnings("Duplicates")
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateClothing(
            @FormParam("uuid") String clothingUUID,
            @FormParam("name") String name,
            @FormParam("color") String color,
            @FormParam("designerID") String designerID,
            @FormParam("price") BigDecimal price
    ) {
        int httpStatus = 200;

        Clothing clothing;
        if (DataHandler.getClothingMap().containsKey(clothingUUID)) {
            clothing = new Brand().getClothingByUUID(clothingUUID);
            clothing.setName(name);
            clothing.setColor(color);
            clothing.setPrice(price);

            if (DataHandler.getDesignerMap().containsKey(designerID)) {
                Designer designer = DataHandler.getDesignerMap().get(designerID);
                clothing.setDesigner(designer);

                Brand brand = new Brand();
                brand.getClothingMap().put(clothing.getUUID(), clothing);

                DataHandler.writeClothes(brand.getClothingMap());

            } else {
                httpStatus = 400;
            }

            httpStatus = 200;
        } else {
            httpStatus = 404;
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }


    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteClothing(
            @QueryParam("uuid") String clothingUUID
    ) {
        int httpStatus;
        try {
            Brand brand = new Brand();
            Clothing clothing = brand.getClothingByUUID(clothingUUID);
            if (clothing != null) {
                httpStatus = 200;
                brand.getClothingMap().remove(clothingUUID);
                DataHandler.writeClothes(brand.getClothingMap());
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }
}
