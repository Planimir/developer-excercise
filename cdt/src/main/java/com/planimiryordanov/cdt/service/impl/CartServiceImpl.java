package com.planimiryordanov.cdt.service.impl;

import com.planimiryordanov.cdt.dto.view.CartViewDTO;
import com.planimiryordanov.cdt.entity.CartEntity;
import com.planimiryordanov.cdt.entity.DealEntity;
import com.planimiryordanov.cdt.entity.ProductEntity;
import com.planimiryordanov.cdt.model.CheckoutDetails;
import com.planimiryordanov.cdt.repository.ICartRepository;
import com.planimiryordanov.cdt.repository.IDealRepository;
import com.planimiryordanov.cdt.service.ICartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import static com.planimiryordanov.cdt.constants.AppConstants.DEAL_2_FOR_3;
import static com.planimiryordanov.cdt.constants.AppConstants.DEAL_BUY_1_GET_1_FREE;


/**
 * @author Planimir Yordanov
 */
@AllArgsConstructor
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final ICartRepository cartRepository;
    @Autowired
    private final IDealRepository dealRepository;

    @Override
    public List<CartViewDTO> getCart() {
        return this.cartRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CartViewDTO.class))
                .sorted()
                .toList();
    }

    @Override
    public void addProductToCart(ProductEntity product) {
        if (product == null) return;
        CartEntity cart = new CartEntity(product, (this.cartRepository.count() + 1));
        this.cartRepository.saveAndFlush(cart);
    }

    @Override
    @Transactional
    public void removeProductFromCart(long order) {
        if (order < 1) return;
        this.cartRepository.deleteByOrder(order);
    }

    @Override
    public CheckoutDetails checkout() {
        int total = 0;
        boolean applied2For3 = false;
        List<DealEntity> deals = this.dealRepository.findAll();
        Map<ProductEntity, Integer> productCounts = new HashMap<>();
        List<ProductEntity> dealProducts = new ArrayList<>();
        List<String> appliedDiscounts = new ArrayList<>();
        List<CartEntity> cartItems = this.cartRepository.findAll()
                .stream()
                .sorted()
                .toList();

        for (CartEntity item : cartItems) {
            ProductEntity product = item.getProduct();
            if (product == null) continue; // skip if product is not recognized

            total += product.getPrice();
            productCounts.put(product, productCounts.getOrDefault(product, 0) + 1);

            // Apply buy 1 get, 1 half price
            DealEntity currentDeal = deals
                    .stream()
                    .filter(x -> x.getName().equals(DEAL_BUY_1_GET_1_FREE))
                    .findFirst()
                    .orElse(null);
            if (currentDeal != null && currentDeal.getApplicableProducts().contains(product)) {
                if (productCounts.get(product) % 2 == 0) {
                    total -= product.getPrice() / 2;
                    appliedDiscounts.add(DEAL_BUY_1_GET_1_FREE);
                }
            }

            // Apply 2 for 3 deal
            currentDeal = deals
                    .stream()
                    .filter(x -> x.getName().equals(DEAL_2_FOR_3))
                    .findFirst()
                    .orElse(null);
            if (!applied2For3 && currentDeal!= null && currentDeal.getApplicableProducts().contains(product)) {
                dealProducts.add(product);
                if (dealProducts.size() == 3) {
                    total -= getCheapest(dealProducts).getPrice();
                    dealProducts.clear();
                    appliedDiscounts.add(DEAL_2_FOR_3);
                    applied2For3 = true;
                }
            }
        }
        cartRepository.deleteAll();
        return new CheckoutDetails(total / 100, total % 100, String.join(", ", appliedDiscounts));
    }

    private ProductEntity getCheapest(List<ProductEntity> products) {
        return products.stream().min(Comparator.comparing(ProductEntity::getPrice)).orElseThrow();
    }
}
