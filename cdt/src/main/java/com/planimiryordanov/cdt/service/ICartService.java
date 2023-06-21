package com.planimiryordanov.cdt.service;

import com.planimiryordanov.cdt.dto.view.CartViewDTO;
import com.planimiryordanov.cdt.entity.ProductEntity;
import com.planimiryordanov.cdt.model.CheckoutDetails;

import java.util.List;

/**
 * @author Planimir Yordanov
 */
public interface ICartService {

    /**
     * getCart() - returns data to be displayed in the web interface.
     * @return - returns data in the web interface.
     */
    List<CartViewDTO> getCart();

    /**
     * addProductToCart() - adds the product to the cart. Sets  order sequentially.
     * @param product - the product to be added
     */
    void addProductToCart(ProductEntity product);

    /**
     * removeProductFromCart() - removes the product from the cart by the sequential order of the added product.
     * @param order - sequential order of the product.
     */
    void removeProductFromCart(long order);

    /**
     * checkout() - calculates the price of the products, if it finds applicable products - applies discounts, then clears the cart.
     * @return - return calculated price in the aws/cloud currency and applies discounts.
     */
    CheckoutDetails checkout();

}
