package com.planimiryordanov.cdt.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Planimir Yordanov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductViewDTO {

    private String id;
    private String name;
    private int price;
}
