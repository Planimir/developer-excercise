package com.planimiryordanov.cdt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Planimir Yordanov
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckoutDetails {
    private int aws;
    private int clouds;
    private String appliedDiscounts;
}
