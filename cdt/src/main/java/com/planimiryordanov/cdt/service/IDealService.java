package com.planimiryordanov.cdt.service;

import com.planimiryordanov.cdt.dto.view.DealViewDTO;

import java.util.List;

/**
 * @author Planimir Yordanov
 */
public interface IDealService {

    /**
     * getAllDeals() - returns the existing deals.
     * @return - list of deals
     */
    List<DealViewDTO> getAllDeals();
}
