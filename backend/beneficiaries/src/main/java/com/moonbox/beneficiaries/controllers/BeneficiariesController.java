package com.moonbox.beneficiaries.controllers;

import com.moonbox.beneficiaries.commands.BeneficiaryCommand;
import com.moonbox.beneficiaries.entities.Beneficiary;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/beneficiaries")
public class BeneficiariesController {

    private final BeanFactory beanFactory;

    public BeneficiariesController(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @GetMapping
    public ResponseEntity<List<Beneficiary>> getBeneficiaries() {

        BeneficiaryCommand command = beanFactory.getBean(BeneficiaryCommand.class);
        return ResponseEntity.ok(command.execute());
    }
}
