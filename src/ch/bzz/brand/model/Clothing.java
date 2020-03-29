package ch.bzz.brand.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;

public class Clothing {
    private String UUID;

    @FormParam("name")
    @NotEmpty
    @Size(min=2, max=40)
    private String name;

    @FormParam("color")
    @NotEmpty
    @Size(min=3, max=20)
    private String color;

    private Designer designer;

    @FormParam("price")
    @DecimalMax(value="999.95")
    @DecimalMin(value="0.05")
    private BigDecimal price;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
