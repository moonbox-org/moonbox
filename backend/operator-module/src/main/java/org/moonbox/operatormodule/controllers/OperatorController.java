package org.moonbox.operatormodule.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/operator", produces = (APPLICATION_JSON_VALUE))
public class OperatorController {

    @GetMapping(value = "/test")
    @PreAuthorize("(hasRole('operator:read') && hasRole('operator:write')) || hasRole('superuser')")
    ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello from Operator Module test endpoint!");
    }

}
