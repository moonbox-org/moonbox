package com.moonbox.beneficiaries.services;

import com.moonbox.beneficiaries.entities.Beneficiary;
import com.moonbox.beneficiaries.repositories.BeneficiaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;

    public BeneficiaryService(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    public List<Beneficiary> getBeneficiaries() {
        log.info("getBeneficiaries");
        return beneficiaryRepository.findAll();
    }
}
