package ch.bzz.brand.service;

import ch.bzz.brand.data.DataHandler;
import ch.bzz.brand.model.Brand;
import ch.bzz.brand.model.Clothing;
import ch.bzz.brand.model.Designer;

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
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String clothingUUID
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
            @Valid @BeanParam Clothing clothing,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("designerUUID") String designerUUID
    ) {
        int httpStatus = 200;

        clothing.setUUID(UUID.randomUUID().toString());

        if (DataHandler.getDesignerMap().containsKey(designerUUID)) {
            Designer designer = DataHandler.getDesignerMap().get(designerUUID);
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
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("uuid") String clothingUUID,
            @Valid @BeanParam Clothing clothing,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("designerUUID") String designerUUID
    ) {
        int httpStatus = 200;

        if (DataHandler.getClothingMap().containsKey(clothingUUID)) {
            clothing.setUUID(clothingUUID);

            if (DataHandler.getDesignerMap().containsKey(designerUUID)) {
                Designer designer = DataHandler.getDesignerMap().get(designerUUID);
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
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
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
