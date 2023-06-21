package com.planimiryordanov.cdt.repository;

import com.planimiryordanov.cdt.entity.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Planimir Yordanov
 */
public interface IDealRepository extends JpaRepository<DealEntity, String> {
}
