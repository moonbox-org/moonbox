package com.moonbox.beneficiaries.repositories;

import com.moonbox.beneficiaries.entities.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, String> {

    Continent findByCode(String code);
}
