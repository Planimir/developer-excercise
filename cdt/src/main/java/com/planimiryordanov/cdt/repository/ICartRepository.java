package com.planimiryordanov.cdt.repository;

import com.planimiryordanov.cdt.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Planimir Yordanov
 */
public interface ICartRepository extends JpaRepository<CartEntity, String> {

    /**
     * deleteByOrder() - delete cart item by it's order.
     * @param order - the sequential order of the product.
     */
    void deleteByOrder(long order);
}
