package com.moonbox.beneficiaries.commands;

import com.moonbox.beneficiaries.entities.Beneficiary;
import com.moonbox.beneficiaries.services.BeneficiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Scope(value = "prototype")
public class BeneficiaryCommand {

    private final BeneficiaryService beneficiaryService;

    public BeneficiaryCommand(BeneficiaryService beneficiaryService) {
        this.beneficiaryService = beneficiaryService;
    }

    public List<Beneficiary> execute() {
        log.info("execute");
        return beneficiaryService.getBeneficiaries();
    }
}
