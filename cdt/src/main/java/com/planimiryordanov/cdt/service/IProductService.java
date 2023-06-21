package com.planimiryordanov.cdt.service;

import com.planimiryordanov.cdt.dto.view.ProductViewDTO;
import com.planimiryordanov.cdt.entity.ProductEntity;

import java.util.List;

/**
 * @author Planimir Yordanov
 */
public interface IProductService {

    /**
     * getAllProducts() - get a list of all products.
     * @return - list of all products as ProductViewDTO.
     */
    List<ProductViewDTO> getAllProducts();
}
