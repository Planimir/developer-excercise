package com.planimiryordanov.cdt.repository;

import com.planimiryordanov.cdt.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Planimir Yordanov
 */
public interface IProductRepository extends JpaRepository<ProductEntity, String> {
}
