package com.moonbox.inventory.repositories;

import com.moonbox.inventory.entities.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, String> {
}
