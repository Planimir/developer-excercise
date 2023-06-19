import java.util.Set;

/**
 *  This class helps us decide which products are suitable for a deal.
 *
 * @author Planimir Yordanov
 *
 */


public class Deal {
    private final String name;
    private final Set<String> applicableProducts;

    /**
     *  Class Deal's constructor, used to create later on a deal and decide which items are to be in the deal.
     * @param name the deal's name
     * @param applicableProducts the products to be used in the deal
     *
     */
    public Deal(String name, Set<String> applicableProducts) {
        this.name = name;
        this.applicableProducts = applicableProducts;
    }

    public String getName() {
        return name;
    }

    public Set<String> getApplicableProducts(){ return applicableProducts ; }

    public boolean appliesToProduct(String productName) {
        return applicableProducts.contains(productName);
    }
}