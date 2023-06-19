/**
 *    This class is for adding the products with their price.
 *
 *    @author Planimir Yordanov
 *
 */

public class Product {
    private final String name;
    private final int price;

    /**
     *   Class Product's constructor, used for adding products to our "products" Map in the class Till, later
     *   transferred to the List<String> basket.
     *
     * @param name the product's name
     * @param price the product's price
     */
    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}