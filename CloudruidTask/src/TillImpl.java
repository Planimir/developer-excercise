import java.util.*;


/**
 *   This class is for calculating the total price of our till + implementing up the deals.
 *
 *   @author Planimir Yordanov
 */
public class TillImpl implements TillInterface {
    private final Map<String, Product> products = new HashMap<>();
    private final Map<String, Deal> deals = new HashMap<>();


    /**
     *  This function adds products to our "products" Map.
     * @param product is a reference to the class Product and has as values from the class - String name and int price.
     */
    @Override
    public void addProduct(Product product) {
        this.products.put(product.getName(), product);
    }

    /**
     *  This function adds deals to our "deals" Map.
     * @param deal is a reference to the class Deal and has as values from the class - String name and Set<String> applicableProducts;
     */
    @Override
    public void addDeal(Deal deal) {
        this.deals.put(deal.getName(), deal);
    }

    /**
     * @param basket The list which gets all the String data from the Map products.
     * @return The total price after going through the products in our basket, checking if there is a valid deal and summing
     * all the products at the end after checking for active deals.
     */
    @Override
    public int calculateTotal(List<String> basket) {
        int total = 0;
        Map<String, Integer> productCounts = new HashMap<>();
        List<Product> dealProducts = new ArrayList<>();

        for (String productName : basket) {
            Product product = products.get(productName);
            if (product == null) continue; // skip if product is not recognized

            total += product.getPrice();
            productCounts.put(productName, productCounts.getOrDefault(productName, 0) + 1);

            // Apply buy 1 get, 1 half price
            if (deals.containsKey("buy 1 get 1 half price") && deals.get("buy 1 get 1 half price").appliesToProduct(productName)) {
                if (productCounts.get(productName) % 2 == 0) {
                    total -= product.getPrice() / 2;
                }
            }

            // Apply 2 for 3 deal
            if (deals.containsKey("2 for 3") && deals.get("2 for 3").appliesToProduct(productName)) {
                dealProducts.add(product);
                if (dealProducts.size() == 3) {
                    total -= getCheapest(dealProducts).getPrice();
                    dealProducts.clear();
                }
            }
        }

        return total;
    }


    /**
     *   This function streams the List of products we have given it as method param and calculates which of them is the cheapest.
     */
    @Override
    public Product getCheapest(List<Product> products) {
        return products.stream().min(Comparator.comparing(Product::getPrice)).orElseThrow();
    }
}