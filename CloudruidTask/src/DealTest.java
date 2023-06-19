import java.util.HashSet;
import java.util.Set;

/**
 *  This class is a TestClass for the class Deal.
 *
 *  @author Planimir Yordanov
 */

public class DealTest {

    public static void main(String[] args) {
        Set<String> applicableProducts = new HashSet<>();
        applicableProducts.add("apple");
        applicableProducts.add("banana");
        Deal deal2for3 = new Deal("2 for 3", applicableProducts);

        Set<String> products = deal2for3.getApplicableProducts();
        System.out.println("Applicable products:");
        for (String product : products) {
            System.out.println(product);
        }

        System.out.println("\nDeal name: " + deal2for3.getName());

        System.out.println("\nDoes it apply to 'apple'? " + deal2for3.appliesToProduct("apple"));
        System.out.println("Does it apply to 'banana'? " + deal2for3.appliesToProduct("banana"));
        System.out.println("Does it apply to 'tomato'? " + deal2for3.appliesToProduct("tomato"));
    }
}

