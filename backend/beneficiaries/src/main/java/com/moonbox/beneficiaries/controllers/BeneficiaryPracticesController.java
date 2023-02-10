package com.moonbox.beneficiaries.controllers;


import com.moonbox.beneficiaries.entities.BeneficiaryPractice;
import com.moonbox.beneficiaries.repositories.BeneficiaryPracticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/beneficiary-practices")
public class BeneficiaryPracticesController {

    @Autowired
    private BeneficiaryPracticeRepository practiceRepository;

    @GetMapping
    public ResponseEntity<List<BeneficiaryPractice>> getBeneficiaryPractices() {
        return ResponseEntity.ok(practiceRepository.findAll());
    }
}
