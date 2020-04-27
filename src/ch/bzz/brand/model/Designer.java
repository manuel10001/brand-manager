package ch.bzz.brand.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class Designer {

    @FormParam("name")
    @NotEmpty
    @Size(min=2, max=40)
    private String designer;
    private String designerUUID;

    public String getDesignerUUID() {
        return designerUUID;
    }

    public void setDesignerUUID(String designerUUID) {
        this.designerUUID = designerUUID;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }
}
