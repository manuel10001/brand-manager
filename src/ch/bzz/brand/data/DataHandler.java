package ch.bzz.brand.data;

import ch.bzz.brand.model.Clothing;
import ch.bzz.brand.model.Designer;
import ch.bzz.brand.service.Config;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Clothing> clothingMap = new HashMap<>();
    private static Map<String, Designer> designerMap = new HashMap<>();


    private DataHandler() {
    }


    public static DataHandler getInstance() {
        return DataHandler.instance;
    }


    public static void readClothes() {
        BufferedReader bufferedReader;
        FileReader fileReader;


        try {
            String clothingPath = Config.getProperty("clothingFile");
            fileReader = new FileReader(clothingPath);
            bufferedReader = new BufferedReader(fileReader);

        } catch (FileNotFoundException fileEx) {
            fileEx.printStackTrace();
            throw new RuntimeException();
        }



        try {
            String line;
            Clothing clothing = null;
            while ((line = bufferedReader.readLine()) != null) {
                clothing = new Clothing();
                String[] values = line.split(";");

                clothing.setUUID(values[0]);
                clothing.setName(values[1]);
                clothing.setColor(values[2]);
                Designer designer = getDesignerMap().get(values[3]);
                clothing.setDesigner(designer);
                clothing.setPrice(new BigDecimal(values[4]));


                clothingMap.put(values[0], clothing);
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                throw new RuntimeException();
            }
        }
    }


    public static void readDesigners() {
        BufferedReader bufferedReader;
        FileReader fileReader;
        try {
            String designerPath = Config.getProperty("designerFile");
            fileReader = new FileReader(designerPath);
            bufferedReader = new BufferedReader(fileReader);

        } catch (FileNotFoundException fileEx) {
            fileEx.printStackTrace();
            throw new RuntimeException();
        }

        try {
            String line;
            Designer designer = null;
            while ((line = bufferedReader.readLine()) != null) {
                designer = new Designer();
                String[] values = line.split(";");

                designer.setDesignerUUID(values[0]);
                designer.setDesigner(values[1]);

                designerMap.put(values[0], designer);
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                throw new RuntimeException();
            }
        }
    }


    public static void writeClothes(Map<String, Clothing> clothingMap) {
        Writer writer = null;
        FileOutputStream fileOutputStream = null;

        try {
            String bookPath = Config.getProperty("clothingFile");
            fileOutputStream = new FileOutputStream(bookPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "utf-8"));

            for (Map.Entry<String, Clothing> clothingEntry : clothingMap.entrySet()) {
                Clothing clothing = clothingEntry.getValue();
                String contents = String.join(";",

                        clothing.getUUID(),
                        clothing.getName(),
                        clothing.getColor(),
                        clothing.getDesigner().getDesignerUUID(),
                        clothing.getPrice().toString()
                );
                writer.write(contents + '\n');
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();

        } finally {

            try {
                if (writer != null) {
                    writer.close();
                }

                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static Map<String, Clothing> getClothingMap() {
        if (clothingMap.isEmpty()) {
            readClothes();
        }
        return clothingMap;
    }


    public static void setClothingMap(Map<String, Clothing> clothingMap) {
        DataHandler.clothingMap = clothingMap;
    }


    public static Map<String, Designer> getDesignerMap() {
        if (designerMap.isEmpty()) {
            readDesigners();
        }
        return designerMap;
    }


    public static void setDesignerMap(Map<String, Designer> designerMap) {
        DataHandler.designerMap = designerMap;
    }

}
