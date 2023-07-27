package com.moonbox.inventory.repositories;

import com.moonbox.inventory.entities.Packaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackagingRepository extends JpaRepository<Packaging, String> {
}
