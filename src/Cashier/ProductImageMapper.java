package Cashier;

import java.util.HashMap;
import java.util.Map;

public class ProductImageMapper {

    private static final Map<String, String> IMAGE_MAP = new HashMap<>();

    static {
        
        IMAGE_MAP.put("Doritos", "doritos1.png");
        IMAGE_MAP.put("Cheetos", "cheetos.png");
        IMAGE_MAP.put("KitKat", "kitkat.png");
        IMAGE_MAP.put("Fritos", "fritos.png");
        IMAGE_MAP.put("Pringles", "pringles.png");
        IMAGE_MAP.put("Chip Ahoys", "chipahoy.png");
        IMAGE_MAP.put("Indomie", "indomie.png");
        IMAGE_MAP.put("Buldak", "buldak.png");
        IMAGE_MAP.put("Nongshim", "nong.png");
        IMAGE_MAP.put("Coca-Cola", "coca.png");
        IMAGE_MAP.put("Monster", "mon.png");
        IMAGE_MAP.put("Dr.Pepper", "pep.png");
        IMAGE_MAP.put("Soda", "so.png");
        IMAGE_MAP.put("Hi-Tech", "hi.png");
        IMAGE_MAP.put("Pepsi", "pepsi.png");
        
    }

    public static String getImage(String productName) {
        return IMAGE_MAP.getOrDefault(
            productName,
            "default.png"
        );
    }
}
