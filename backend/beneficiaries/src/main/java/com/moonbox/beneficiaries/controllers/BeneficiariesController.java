package com.moonbox.beneficiaries.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/beneficiaries")
public class BeneficiariesController {

    @GetMapping(value = "/demo")
    public ResponseEntity<String> getDemo() {
        return ResponseEntity.ok("GET - beneficiaries controller");
    }

    @PostMapping(value = "/demo")
    public ResponseEntity<String> postDemo() {
        return ResponseEntity.ok("POST - beneficiaries controller");
    }
}
