
/**
 *  This class is a TestClass for the class Deal.
 *
 *  @author Planimir Yordanov
 */

public class ProductTest {

    public static void main(String[] args) {
        // Create products
        Product apple = new Product("apple", 50);
        Product banana = new Product("banana", 60);
        Product potato = new Product("potato", 30);

        System.out.println("Product name: " + apple.getName());

        System.out.println("Product price: " + apple.getPrice());

        System.out.println("Products:");
        System.out.println("Name: " + apple.getName() + ", Price: " + apple.getPrice());
        System.out.println("Name: " + banana.getName() + ", Price: " + banana.getPrice());
        System.out.println("Name: " + potato.getName() + ", Price: " + potato.getPrice());
    }
}
