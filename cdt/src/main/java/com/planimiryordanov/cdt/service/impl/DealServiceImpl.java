package com.planimiryordanov.cdt.service.impl;

import com.planimiryordanov.cdt.dto.view.DealViewDTO;
import com.planimiryordanov.cdt.repository.IDealRepository;
import com.planimiryordanov.cdt.service.IDealService;
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
public class DealServiceImpl implements IDealService {

    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final IDealRepository dealRepository;

    @Override
    public List<DealViewDTO> getAllDeals() {
        return this.dealRepository.findAll()
                .stream()
                .map(d -> this.modelMapper.map(d, DealViewDTO.class))
                .toList();
    }
}
