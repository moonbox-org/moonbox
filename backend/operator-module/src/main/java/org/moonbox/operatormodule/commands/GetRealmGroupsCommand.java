package org.moonbox.operatormodule.commands;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.GroupRepresentation;
import org.moonbox.operatormodule.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
public class GetRealmGroupsCommand {

    /* ----- PARAMETERS ----- */

    @Autowired
    KeycloakService keycloakService;


    /* ----- METHODS ----- */


    public ResponseEntity<GroupRepresentation> execute() {
        return ResponseEntity.ok(null);
    }

}
