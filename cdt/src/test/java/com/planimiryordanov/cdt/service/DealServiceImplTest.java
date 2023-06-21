package com.planimiryordanov.cdt.service;

import com.planimiryordanov.cdt.dto.view.DealViewDTO;
import com.planimiryordanov.cdt.entity.DealEntity;
import com.planimiryordanov.cdt.entity.ProductEntity;
import com.planimiryordanov.cdt.repository.IDealRepository;
import com.planimiryordanov.cdt.service.impl.DealServiceImpl;
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
public class DealServiceImplTest {
    private final String VALID_DEAL_ID = "deal-1";
    private final String VALID_DEAL_NAME= "real deal";
    private final String VALID_PRODUCT_ID = "product-1";
    private final String VALID_PRODUCT_NAME = "carevica";
    private final int VALID_PRODUCT_PRICE = 50;
    private ModelMapper realMapper;
    private DealEntity dealEntity;
    private ProductEntity productEntity;
    private IDealService dealService;

    @Mock
    private IDealRepository mockDealRepository;
    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    public void setUp() {
        this.realMapper = new ModelMapper();
        this.dealService = new DealServiceImpl(mockModelMapper, mockDealRepository);

        this.productEntity = new ProductEntity();
        this.productEntity.setId(VALID_PRODUCT_ID);
        this.productEntity.setName(VALID_PRODUCT_NAME);
        this.productEntity.setPrice(VALID_PRODUCT_PRICE);

        this.dealEntity = new DealEntity();
        this.dealEntity.setId(VALID_DEAL_ID);
        this.dealEntity.setName(VALID_DEAL_NAME);
        this.dealEntity.setApplicableProducts(List.of(this.productEntity));
    }

    @Test
    public void testGetAllDealsShouldReturnValidResult() {
        // Arrange
        when(this.mockDealRepository.findAll())
                .thenReturn(List.of(this.dealEntity));
        when(this.mockModelMapper.map(this.dealEntity, DealViewDTO.class))
                .thenReturn(this.realMapper.map(this.dealEntity, DealViewDTO.class));

        // Act
        List<DealViewDTO> result = this.dealService.getAllDeals();

        // Assert
        verify(this.mockDealRepository, times(1)).findAll();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(VALID_DEAL_NAME, result.get(0).getName());
    }
}
