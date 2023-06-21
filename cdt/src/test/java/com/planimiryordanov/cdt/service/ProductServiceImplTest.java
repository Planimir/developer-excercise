package com.planimiryordanov.cdt.service;

import com.planimiryordanov.cdt.dto.view.ProductViewDTO;
import com.planimiryordanov.cdt.entity.ProductEntity;
import com.planimiryordanov.cdt.repository.IProductRepository;
import com.planimiryordanov.cdt.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;
import static org.mockito.Mockito.*;

/**
 * @author Planimir Yordanov
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    private final String VALID_PRODUCT_ID = "product-1";
    private final String VALID_PRODUCT_NAME = "carevica";
    private final int VALID_PRODUCT_PRICE = 50;

    private ProductEntity productEntity;
    private ModelMapper realMapper;
    private IProductService productService;

    @Mock
    private IProductRepository mockProductRepository;
    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    public void setUp() {
        this.realMapper = new ModelMapper();
        this.productService = new ProductServiceImpl(mockModelMapper, mockProductRepository);

        this.productEntity = new ProductEntity();
        this.productEntity.setId(VALID_PRODUCT_ID);
        this.productEntity.setName(VALID_PRODUCT_NAME);
        this.productEntity.setPrice(VALID_PRODUCT_PRICE);
    }

    @Test
    public void testGetAllProductsShouldReturnValidResult() {
        // Arrange
        when(this.mockProductRepository.findAll())
                .thenReturn(List.of(this.productEntity));
        when(this.mockModelMapper.map(this.productEntity, ProductViewDTO.class))
                .thenReturn(this.realMapper.map(this.productEntity, ProductViewDTO.class));

        // Act
        List<ProductViewDTO> result = this.productService.getAllProducts();

        // Assert
        verify(this.mockProductRepository, times(1)).findAll();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(VALID_PRODUCT_NAME, result.get(0).getName());
        Assertions.assertEquals(VALID_PRODUCT_PRICE, result.get(0).getPrice());
    }
}
