package com.moonbox.inventory.repositories;

import com.moonbox.inventory.entities.MeasuringUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasuringUnitRepository extends JpaRepository<MeasuringUnit, String> {
}
