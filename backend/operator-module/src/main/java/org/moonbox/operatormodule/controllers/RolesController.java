package org.moonbox.operatormodule.controllers;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.moonbox.operatormodule.commands.GetRealmRolesCommand;
import org.moonbox.operatormodule.services.KeycloakService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/roles", produces = APPLICATION_JSON_VALUE)
public class RolesController {


    /* ----- PARAMETERS ----- */

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private KeycloakService keycloakService;


    /* ----- METHODS ----- */


    @GetMapping(value = "/list")
    @PreAuthorize("hasRole('operator:read') || hasRole('superuser')")
    ResponseEntity<List<RoleRepresentation>> getRealmRoles() {
        return ResponseEntity.ok(keycloakService.getRealmRoles());
    }

    @GetMapping
    ResponseEntity<RoleRepresentation> getRole(
            @RequestParam(name = "roleId", required = false) String roleId,
            @RequestParam(name = "roleName", required = false) String roleName
    ) {
        GetRealmRolesCommand command = beanFactory.getBean(GetRealmRolesCommand.class, roleId, roleName);
        return command.execute();
    }

}
