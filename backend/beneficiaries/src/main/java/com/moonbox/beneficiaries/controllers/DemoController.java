package com.moonbox.beneficiaries.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/demo")
public class DemoController {

    @GetMapping()
    public ResponseEntity<String> getDemo() {
        return ResponseEntity.ok("GET - beneficiaries controller");
    }

    @PostMapping()
    public ResponseEntity<String> postDemo() {
        return ResponseEntity.ok("POST - beneficiaries controller");
    }
}
