import java.util.List;
/**
 *    This class is used for declaring our functions for the client`s Till.
 *
 *    @author Planimir Yordanov
 */

public interface TillInterface {

    /**
     * Declaration of the function calculateTotal which we Override in the TillImpl class.
     */
    public int calculateTotal(List<String> basket);
    public void addProduct(Product product);
    public void addDeal(Deal deal);
    public Product getCheapest(List<Product> products);
}
