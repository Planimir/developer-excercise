package com.planimiryordanov.cdt.service.impl;

import com.planimiryordanov.cdt.dto.view.ProductViewDTO;
import com.planimiryordanov.cdt.repository.IProductRepository;
import com.planimiryordanov.cdt.service.IProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Planimir Yordanov
 */
@AllArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final IProductRepository productRepository;

    @Override
    public List<ProductViewDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product ->
                        this.modelMapper.map(product, ProductViewDTO.class))
                .toList();
    }
}
