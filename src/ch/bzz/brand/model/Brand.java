package ch.bzz.brand.model;

import ch.bzz.brand.data.DataHandler;

import java.util.Map;

public class Brand {
    private Map<String, Clothing> clothingMap;

    public Brand() {
        clothingMap = DataHandler.getClothingMap();
    }

    public Clothing getClothingById(String clothingId) {
        if (getClothingMap().containsKey(clothingId)) {
            return getClothingMap().get(clothingId);
        }
        return null;
    }


    public Map<String, Clothing> getClothingMap() {
        return clothingMap;
    }

    public void setClothingMap(Map<String, Clothing> clothingMap) {
        this.clothingMap = clothingMap;
    }
}
