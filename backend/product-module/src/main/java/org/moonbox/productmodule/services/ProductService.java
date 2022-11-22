package org.moonbox.productmodule.services;

import org.moonbox.productmodule.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    /* ----- PARAMETERS ----- */

    private final ProductRepository productRepository;

    /* ----- CONSTRUCTOR ----- */

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
