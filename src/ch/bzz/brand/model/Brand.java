package ch.bzz.brand.model;

import ch.bzz.brand.data.DataHandler;

import javax.xml.crypto.Data;
import java.util.Map;

public class Brand {
    private Map<String, Clothing> clothingMap;
    private Map<String, Designer> designerMap;

    public Brand() {
        clothingMap = DataHandler.getClothingMap();
        designerMap = DataHandler.getDesignerMap();
    }

    public Clothing getClothingByUUID(String clothingUUID) {
        if (getClothingMap().containsKey(clothingUUID)) {
            return getClothingMap().get(clothingUUID);
        }
        return null;
    }

    public Designer getDesignerByUUID(String designerUUID) {
        if (getDesignerMap().containsKey(designerUUID)) {
            return getDesignerMap().get(designerUUID);
        }
        return null;
    }


    public Map<String, Clothing> getClothingMap() {
        return clothingMap;
    }

    public void setClothingMap(Map<String, Clothing> clothingMap) {
        this.clothingMap = clothingMap;
    }

    public Map<String, Designer> getDesignerMap() {
        return designerMap;
    }

    public void setDesignerMap(Map<String, Designer> designerMap) {
        this.designerMap = designerMap;
    }
}
