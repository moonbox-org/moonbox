package com.moonbox.inventory.repositories;

import com.moonbox.inventory.entities.ContainerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerTypeRepository extends JpaRepository<ContainerType, String> {
}
