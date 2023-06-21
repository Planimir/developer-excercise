package com.planimiryordanov.cdt.dto.view;

import com.planimiryordanov.cdt.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

/**
 * @author Planimir Yordanov
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartViewDTO implements Comparable<CartViewDTO> {
    private ProductEntity product;
    private long order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartViewDTO that = (CartViewDTO) o;
        return order == that.order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order);
    }

    @Override
    public int compareTo(CartViewDTO other) {
        return this.order == other.order ? 0 : (int)(this.order - other.order);
    }
}
