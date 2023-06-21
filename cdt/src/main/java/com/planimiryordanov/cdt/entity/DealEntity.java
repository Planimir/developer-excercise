package com.planimiryordanov.cdt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;

/**
 * @author Planimir Yordanov
 */
@Entity
@Table(name = "deals")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DealEntity {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "deal_id"))
    private List<ProductEntity> applicableProducts;

    public DealEntity(String name, List<ProductEntity> applicableProducts) {
        this.setName(name);
        this.setApplicableProducts(applicableProducts);
    }
}
