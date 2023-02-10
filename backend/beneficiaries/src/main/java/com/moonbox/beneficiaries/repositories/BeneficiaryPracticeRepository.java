package com.moonbox.beneficiaries.repositories;

import com.moonbox.beneficiaries.entities.BeneficiaryPractice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryPracticeRepository extends JpaRepository<BeneficiaryPractice, Long> {
}
