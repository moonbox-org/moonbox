package org.moonbox.operatormodule.controllers;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.moonbox.operatormodule.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/roles", produces = APPLICATION_JSON_VALUE)
public class RolesController {


    /* ----- PARAMETERS ----- */


    @Autowired
    private KeycloakService keycloakService;


    /* ----- METHODS ----- */


    @GetMapping(value = "/list")
    ResponseEntity<List<RoleRepresentation>> getRealmRoles() {
        return ResponseEntity.ok(keycloakService.getRealmRoles());
    }

}
