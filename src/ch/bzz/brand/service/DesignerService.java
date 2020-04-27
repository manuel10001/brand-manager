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

@Path("designer")
public class DesignerService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDesigner() {
        Map<String, Designer> designerMap = new Brand().getDesignerMap();
        return Response
                .status(200)
                .entity(designerMap)
                .build();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readDesigner(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String designerUUID
    ) {
        int httpStatus;

        Designer designer = new Brand().getDesignerByUUID(designerUUID);
        if (designer != null) {
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }

        return Response
                .status(httpStatus)
                .entity(designer)
                .build();
    }

    @SuppressWarnings("Duplicates")
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createDesigner(
            @Valid @BeanParam Designer designer
    ) {
        int httpStatus = 200;

        if (!DataHandler.getDesignerMap().containsValue(designer.getDesigner())) {

            designer.setDesignerUUID(UUID.randomUUID().toString());

            Map<String, Designer> designerMap = DataHandler.getDesignerMap();
            designerMap.put(designer.getDesignerUUID(), designer);

            DataHandler.writeDesigners(designerMap);

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
    public Response updateDesigner(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("uuid") String designerUUID,
            @FormParam("name") String name
    ) {
        int httpStatus = 200;

            if (DataHandler.getDesignerMap().containsKey(designerUUID)) {
                Designer designer = DataHandler.getDesignerMap().get(designerUUID);
                designer.setDesigner(name);

                Brand brand = new Brand();
                brand.getDesignerMap().put(designer.getDesignerUUID(), designer);

                DataHandler.writeDesigners(brand.getDesignerMap());

            } else {
                httpStatus = 400;
            }

            httpStatus = 200;


        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
