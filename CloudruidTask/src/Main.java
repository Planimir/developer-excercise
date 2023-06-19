import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 *    This class is used to run
 *
 *    @author Planimir Yordanov
 *
 */

public class Main {
    public static void main(String[] args) {
        TillImpl till = new TillImpl();

        till.addProduct(new Product("apple", 50));
        till.addProduct(new Product("banana", 40));
        till.addProduct(new Product("tomato", 30));
        till.addProduct(new Product("potato", 26));

        till.addDeal(new Deal("2 for 3", new HashSet<>(Arrays.asList("apple", "banana", "tomato"))));
        till.addDeal(new Deal("buy 1 get 1 half price", new HashSet<>(Collections.singletonList("potato"))));

        List<String> basket = Arrays.asList("apple", "banana", "banana", "potato", "tomato", "banana", "potato");
        int total = till.calculateTotal(basket);

        System.out.printf("Total cost: %d aws and %d clouds%n", total / 100, total % 100);
    }
}
