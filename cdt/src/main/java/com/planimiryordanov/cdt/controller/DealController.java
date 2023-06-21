package com.planimiryordanov.cdt.controller;

import com.planimiryordanov.cdt.service.IDealService;
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
public class DealController {

    @Autowired
    private final IDealService dealService;

    @GetMapping("/deals")
    public String getCartPage(Model model) {
        model.addAttribute("deals", this.dealService.getAllDeals());
        return "deals";
    }
}
