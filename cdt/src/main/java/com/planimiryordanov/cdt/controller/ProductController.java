package com.planimiryordanov.cdt.controller;

import com.planimiryordanov.cdt.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Planimir Yordanov
 */
@AllArgsConstructor
@Controller
public class ProductController {

    @Autowired
    private final IProductService productService;

    @GetMapping("/")
    public String redirectPage(){
        return "redirect:products";
    }

    @GetMapping("/products")
    public String getProductsPage(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

}
