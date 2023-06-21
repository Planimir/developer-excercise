package com.planimiryordanov.cdt.init;

import com.planimiryordanov.cdt.entity.DealEntity;
import com.planimiryordanov.cdt.entity.ProductEntity;
import com.planimiryordanov.cdt.repository.IDealRepository;
import com.planimiryordanov.cdt.repository.IProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import static com.planimiryordanov.cdt.constants.AppConstants.*;

/**
 * @author Planimir Yordanov
 */
@AllArgsConstructor
@Component
public class DBInit implements CommandLineRunner {

    @Autowired
    private final IProductRepository productRepository;

    @Autowired
    private final IDealRepository dealRepository;

    @Override
    public void run(String... args) throws Exception {
        List<ProductEntity> products = List.of(
                new ProductEntity("apple", 50),
                new ProductEntity("banana", 40),
                new ProductEntity("tomato", 30),
                new ProductEntity("potato", 26)
        );
        List<DealEntity> deals = List.of(
                new DealEntity(DEAL_2_FOR_3,
                        List.of(products.get(0),
                                products.get(1),
                                products.get(2))),
                new DealEntity(DEAL_BUY_1_GET_1_FREE,
                        List.of(products.get(3)))
        );
        if (this.productRepository.count() == 0) {

            this.productRepository.saveAll(products);
        }
        if (this.dealRepository.count() == 0) {

            this.dealRepository.saveAll(deals);
        }
    }
}
