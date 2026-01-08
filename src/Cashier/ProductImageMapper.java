package Cashier;

import java.util.HashMap;
import java.util.Map;

public class ProductImageMapper {

    private static final Map<String, String> IMAGE_MAP = new HashMap<>();

    static {
        IMAGE_MAP.put("Doritos", "doritos2.png");
        IMAGE_MAP.put("Milk", "milk.png");
        IMAGE_MAP.put("Bread", "bread.png");
        IMAGE_MAP.put("Pepsi", "pepsi.png");
        IMAGE_MAP.put("Coca-cola", "cocacola.png");
    }

    public static String getImage(String productName) {
        return IMAGE_MAP.getOrDefault(
            productName,
            "default.png"
        );
    }
}
