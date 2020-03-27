package ch.bzz.brand.model;

import java.util.Map;

public class Brand {
    private Map<String, Clothing> clothingMap;


    public Map<String, Clothing> getClothingMap() {
        return clothingMap;
    }

    public void setClothingMap(Map<String, Clothing> clothingMap) {
        this.clothingMap = clothingMap;
    }
}
