package com.moonbox.inventory.repositories;

import com.moonbox.inventory.entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Category findByName(String name);

    @Transactional
    void deleteById(String id);

    @Transactional
    void deleteByName(String name);
}