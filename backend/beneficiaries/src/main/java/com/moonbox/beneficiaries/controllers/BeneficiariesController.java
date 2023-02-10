package com.moonbox.beneficiaries.controllers;

import com.moonbox.beneficiaries.entities.Beneficiary;
import com.moonbox.beneficiaries.repositories.BeneficiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/beneficiaries")
public class BeneficiariesController {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @GetMapping
    public ResponseEntity<List<Beneficiary>> getBeneficiaries() {
        return ResponseEntity.ok(beneficiaryRepository.findAll());
    }
}
