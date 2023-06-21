package com.planimiryordanov.cdt.service;

import com.planimiryordanov.cdt.dto.view.CartViewDTO;
import com.planimiryordanov.cdt.entity.CartEntity;
import com.planimiryordanov.cdt.entity.DealEntity;
import com.planimiryordanov.cdt.entity.ProductEntity;
import com.planimiryordanov.cdt.model.CheckoutDetails;
import com.planimiryordanov.cdt.repository.ICartRepository;
import com.planimiryordanov.cdt.repository.IDealRepository;
import com.planimiryordanov.cdt.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;
import static com.planimiryordanov.cdt.constants.AppConstants.DEAL_2_FOR_3;
import static com.planimiryordanov.cdt.constants.AppConstants.DEAL_BUY_1_GET_1_FREE;
import static org.mockito.Mockito.*;

/**
 * @author Planimir Yordanov
 */
@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    private final String VALID_CART_ID = "cart-item-1";
    private final long VALID_CART_ORDER = 1;


    private final String VALID_PRODUCT_ID = "product-1";
    private final String VALID_PRODUCT_NAME = "carevica";
    private final int VALID_PRODUCT_PRICE = 50;

    private CartEntity cartEntity;
    private ProductEntity productEntity;

    private ModelMapper realMapper;
    private ICartService cartService;

    @Mock
    private ICartRepository mockCartRepository;
    @Mock
    private IDealRepository mockDealRepository;
    @Mock
    private ModelMapper mockModelMapper;


    @BeforeEach
    public void setUp() {
        this.realMapper = new ModelMapper();
        this.cartService = new CartServiceImpl(mockModelMapper, mockCartRepository, mockDealRepository);

        this.productEntity = new ProductEntity();
        this.productEntity.setId(VALID_PRODUCT_ID);
        this.productEntity.setName(VALID_PRODUCT_NAME);
        this.productEntity.setPrice(VALID_PRODUCT_PRICE);

        this.cartEntity = new CartEntity();
        this.cartEntity.setId(VALID_CART_ID);
        this.cartEntity.setOrder(VALID_CART_ORDER);
        this.cartEntity.setProduct(productEntity);
    }

    @Test
    public void testGetCartShouldReturnValidResult() {
        // Arrange
        when(this.mockCartRepository.findAll())
                .thenReturn(List.of(this.cartEntity));
        when(this.mockModelMapper.map(this.cartEntity, CartViewDTO.class))
                .thenReturn(this.realMapper.map(this.cartEntity, CartViewDTO.class));

        // Act
        List<CartViewDTO> result = this.cartService.getCart();

        // Assert
        verify(this.mockCartRepository, times(1)).findAll();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(VALID_CART_ORDER, result.get(0).getOrder());
    }

    @Test
    public void testAddProductToCartShouldAddProduct() {
        // Arrange
        when(this.mockCartRepository.count())
                .thenReturn(0L);

        // Act
        this.cartService.addProductToCart(this.productEntity);

        // Assert
        verify(this.mockCartRepository, times(1)).count();
        verify(this.mockCartRepository, times(1)).saveAndFlush(any(CartEntity.class));
    }

    @Test
    public void testAddProductToCartShouldReturnOnNullProduct() {
        // Act
        this.cartService.addProductToCart(null);

        // Assert
        verify(this.mockCartRepository, times(0)).count();
        verify(this.mockCartRepository, times(0)).saveAndFlush(any(CartEntity.class));
    }

    @Test
    public void testRemoveProductFromCartShouldRemoveProduct() {
        // Act
        this.cartService.removeProductFromCart(VALID_CART_ORDER);

        // Assert
        verify(this.mockCartRepository, times(1)).deleteByOrder(any(Long.class));
    }

    @Test
    public void testRemoveProductFromCartShouldReturnOnNegativeOrder() {
        // Act
        this.cartService.removeProductFromCart(-1);

        // Assert
        verify(this.mockCartRepository, times(0)).deleteByOrder(any(Long.class));
    }

    @Test
    public void testCheckoutShouldReturnValidResult() {
        // Arrange
        List<ProductEntity> products = List.of(
                new ProductEntity("1", "apple", 50),
                new ProductEntity("2", "banana", 40),
                new ProductEntity("3", "tomato", 30),
                new ProductEntity("4", "potato", 26)
        );
        List<DealEntity> deals = List.of(
                new DealEntity(DEAL_2_FOR_3,
                        List.of(products.get(0),
                                products.get(1),
                                products.get(2))),
                new DealEntity(DEAL_BUY_1_GET_1_FREE,
                        List.of(products.get(3)))
        );
        // "apple", "banana", "banana", "potato", "tomato", "banana", "potato"
        List<CartEntity> cartItems = List.of(
                new CartEntity(products.get(0), 1),
                new CartEntity(products.get(1), 2),
                new CartEntity(products.get(1), 3),
                new CartEntity(products.get(3), 4),
                new CartEntity(products.get(2), 5),
                new CartEntity(products.get(1), 6),
                new CartEntity(products.get(3), 7)
        );
        when(this.mockDealRepository.findAll())
                .thenReturn(deals);
        when(this.mockCartRepository.findAll())
                .thenReturn(cartItems);

        // Act
        CheckoutDetails checkoutDetails = this.cartService.checkout();

        // Assert
        verify(this.mockDealRepository, times(1)).findAll();
        verify(this.mockCartRepository, times(1)).findAll();
        verify(this.mockCartRepository, times(1)).deleteAll();
        Assertions.assertEquals(1, checkoutDetails.getAws());
        Assertions.assertEquals(99, checkoutDetails.getClouds());
        Assertions.assertEquals(String.format("%s, %s", DEAL_2_FOR_3, DEAL_BUY_1_GET_1_FREE), checkoutDetails.getAppliedDiscounts());
    }

    @Test
    public void testCheckoutShouldReturnZeroOnNullProducts() {
        // Arrange
        List<ProductEntity> products = List.of(
                new ProductEntity("1", "apple", 50),
                new ProductEntity("2", "banana", 40),
                new ProductEntity("3", "tomato", 30),
                new ProductEntity("4", "potato", 26)
        );
        // "apple", "banana", "banana", "potato", "tomato", "banana", "potato"
        List<CartEntity> cartItems = List.of(
                new CartEntity(products.get(0), 1),
                new CartEntity(products.get(1), 2),
                new CartEntity(products.get(1), 3),
                new CartEntity(products.get(3), 4),
                new CartEntity(products.get(2), 5),
                new CartEntity(products.get(1), 6),
                new CartEntity(products.get(3), 7)
        );
        when(this.mockDealRepository.findAll())
                .thenReturn(new ArrayList<>());
        when(this.mockCartRepository.findAll())
                .thenReturn(cartItems);

        // Act
        CheckoutDetails checkoutDetails = this.cartService.checkout();

        // Assert
        verify(this.mockDealRepository, times(1)).findAll();
        verify(this.mockCartRepository, times(1)).findAll();
        verify(this.mockCartRepository, times(1)).deleteAll();
        Assertions.assertEquals(2, checkoutDetails.getAws());
        Assertions.assertEquals(52, checkoutDetails.getClouds());
        Assertions.assertEquals("", checkoutDetails.getAppliedDiscounts());
    }

    @Test
    public void testCheckoutShouldNotApplyDealsIfNotSet() {
        // Arrange
        List<DealEntity> deals = List.of(
                new DealEntity(DEAL_2_FOR_3, new ArrayList<>()),
                new DealEntity(DEAL_BUY_1_GET_1_FREE, new ArrayList<>())
        );
        // "apple", "banana", "banana", "potato", "tomato", "banana", "potato"
        List<CartEntity> cartItems = List.of(
                new CartEntity(null, 1),
                new CartEntity(null, 2),
                new CartEntity(null, 3),
                new CartEntity(null, 4),
                new CartEntity(null, 5),
                new CartEntity(null, 6),
                new CartEntity(null, 7)
        );
        when(this.mockDealRepository.findAll())
                .thenReturn(deals);
        when(this.mockCartRepository.findAll())
                .thenReturn(cartItems);

        // Act
        CheckoutDetails checkoutDetails = this.cartService.checkout();

        // Assert
        verify(this.mockDealRepository, times(1)).findAll();
        verify(this.mockCartRepository, times(1)).findAll();
        verify(this.mockCartRepository, times(1)).deleteAll();
        Assertions.assertEquals(0, checkoutDetails.getAws());
        Assertions.assertEquals(0, checkoutDetails.getClouds());
        Assertions.assertEquals("", checkoutDetails.getAppliedDiscounts());
    }
}
