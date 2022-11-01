package org.moonbox.operatormodule.commands;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.moonbox.operatormodule.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Scope("prototype")
public class DeleteOperatorCommand {

    /* ----- PARAMETERS ----- */

    @Autowired
    KeycloakService keycloakService;

    private final String operatorId;
    private final String username;
    private final String email;

    UserRepresentation operatorToDelete = new UserRepresentation();

    /* ----- CONSTRUCTOR ----- */

    public DeleteOperatorCommand(String operatorId, String username, String email) {
        this.operatorId = operatorId;
        this.username = username;
        this.email = email;
    }


    /* ----- METHODS ----- */


    public ResponseEntity<Void> execute() {

        if (StringUtils.isEmpty(operatorId) && StringUtils.isEmpty(username) && StringUtils.isEmpty(email)) {
            log.info("At least one between operator ID, username or email must be provided");
            return ResponseEntity.badRequest().build();
        }

        if (StringUtils.isNotEmpty(operatorId)) {
            operatorToDelete = keycloakService.getOperatorById(operatorId);
        }
        if (StringUtils.isNotEmpty(username)) {
            getOperatorByUsername();
        }
        if (StringUtils.isNotEmpty(email)) {
            getOperatorByEmail();
        }

        if (operatorToDelete != null) {
            keycloakService.deleteOperatorById(operatorToDelete.getId());
        } else {
            log.info("No operator to be deleted");
        }

        return ResponseEntity.ok().build();
    }


    /* ----- AUXILIARY METHODS ----- */


    private void getOperatorByUsername() {
        List<UserRepresentation> operatorsList = keycloakService.getOperatorByUsername(username);
        if (operatorsList.stream().noneMatch(u -> u.getUsername().equalsIgnoreCase(username))) {
            operatorToDelete = null;
            return;
        }
        operatorsList.forEach(userRepresentation -> {
            if (userRepresentation.getUsername().equalsIgnoreCase(username)) {
                operatorToDelete = userRepresentation;
            }
        });
    }

    private void getOperatorByEmail() {
        List<UserRepresentation> operatorsList = keycloakService.getOperatorByEmail(email);
        if (operatorsList.stream().noneMatch(u -> u.getEmail().equalsIgnoreCase(email))) {
            operatorToDelete = null;
            return;
        }
        operatorsList.forEach(userRepresentation -> {
            if (userRepresentation.getEmail().equalsIgnoreCase(email)) {
                operatorToDelete = userRepresentation;
            }
        });
    }
}
