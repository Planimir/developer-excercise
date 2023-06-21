package com.planimiryordanov.cdt.controller;

import com.planimiryordanov.cdt.entity.ProductEntity;
import com.planimiryordanov.cdt.repository.IProductRepository;
import com.planimiryordanov.cdt.service.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Planimir Yordanov
 */
@AllArgsConstructor
@Controller
public class CartController {

    @Autowired
    private final IProductRepository productRepository;
    @Autowired
    ICartService cartService;

    @GetMapping("/cart")
    public String getCartPage(Model model) {
        model.addAttribute("cart", this.cartService.getCart());
        return "cart";
    }

    @GetMapping("/checkout")
    public String getCheckoutPage(Model model) {
        model.addAttribute("details", this.cartService.checkout());
        return "checkout";
    }

    @PostMapping("/cart/product/add/{product_id}") // same as top, but not redirecting, it's just a gateway.
    public String addProductToCartFromProductPage(@PathVariable("product_id") String productId) {
        ProductEntity product = this.productRepository.findById(productId).orElse(null);
        this.cartService.addProductToCart(product);
        return "redirect:/products";
    }

    @PostMapping("/cart/add/{product_id}") // same as top, but not redirecting, it's just a gateway.
    public String addProductToCart(@PathVariable("product_id") String productId) {
        ProductEntity product = this.productRepository.findById(productId).orElse(null);
        this.cartService.addProductToCart(product);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{order}") // same as top, but not redirecting, it's just a gateway.
    public String removeProductFromCart(@PathVariable("order") long order) {
        this.cartService.removeProductFromCart(order);
        return "redirect:/cart";
    }

}
