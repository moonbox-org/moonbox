package org.moonbox.operatormodule.controllers;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.moonbox.operatormodule.commands.GetAllOperatorsCommand;
import org.moonbox.operatormodule.commands.GetLoggedInOperatorCommand;
import org.moonbox.operatormodule.commands.GetOperatorCommand;
import org.moonbox.operatormodule.models.Operator;
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
@RequestMapping(value = "/api/v1/operators", produces = APPLICATION_JSON_VALUE)
public class OperatorController {


    /* ----- PARAMETERS ----- */


    @Autowired
    BeanFactory beanFactory;

    @Autowired
    KeycloakService keycloakService;


    /* ----- METHODS ----- */


    /*
     * returns the operator that is currently logged in
     */
    @GetMapping(value = "/loggedInOperator")
    @PreAuthorize("hasRole('operator:read') || hasRole('superuser')")
    ResponseEntity<Operator> getLoggedInOperator() {
        log.info("START /loggedInOperator");

        GetLoggedInOperatorCommand command = beanFactory.getBean(GetLoggedInOperatorCommand.class);
        Operator output = command.execute();

        log.info("END /loggedInOperator");
        return ResponseEntity.ok(output);
    }

    /*
     * returns the operator according to the search parameter
     */
    @GetMapping()
    @PreAuthorize("hasRole('operator:read') || hasRole('superuser')")
    ResponseEntity<UserRepresentation> getOperator(
            @RequestParam(name = "operatorId", required = false) String operatorId,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "email", required = false) String email
    ) {
        GetOperatorCommand command = beanFactory.getBean(GetOperatorCommand.class, operatorId, username, email);
        return command.execute();
    }

    /*
     * returns the complete list of operators from keycloak
     */
    @GetMapping(value = "/list")
    @PreAuthorize("hasRole('operator:read') || hasRole('superuser')")
    ResponseEntity<List<UserRepresentation>> getOperators() {
        GetAllOperatorsCommand command = beanFactory.getBean(GetAllOperatorsCommand.class);
        return command.execute();
    }

    /*
     * test endpoint
     */
    @GetMapping(value = "/test")
    @PreAuthorize("(hasRole('operator:read') && hasRole('operator:write')) || hasRole('superuser')")
    ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello from Operator Module test endpoint!");
    }

}
