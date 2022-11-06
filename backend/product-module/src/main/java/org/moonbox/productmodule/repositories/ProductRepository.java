package org.moonbox.productmodule.repositories;

import org.moonbox.productmodule.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Override
    Page<Product> findAll(Pageable pageable);
}
