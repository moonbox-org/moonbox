package org.moonbox.operatormodule.commands;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.moonbox.operatormodule.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Scope("prototype")
public class GetAllOperatorsCommand {

    /* ----- PARAMETERS ----- */

    @Autowired
    KeycloakService keycloakService;


    /* ----- METHODS ----- */


    public ResponseEntity<List<UserRepresentation>> execute() {

        List<UserRepresentation> output = new ArrayList<>();

        List<UserRepresentation> operatorsList = keycloakService.getOperators();

        operatorsList.forEach(o -> output.add(keycloakService.getOperatorById(o.getId())));

        return ResponseEntity.ok(output);
    }
}
