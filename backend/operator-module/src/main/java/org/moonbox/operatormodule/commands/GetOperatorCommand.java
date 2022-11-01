package org.moonbox.operatormodule.commands;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.moonbox.operatormodule.models.OperatorGroupsAndRoles;
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
public class GetOperatorCommand {

    /* ----- PARAMETERS ----- */

    @Autowired
    KeycloakService keycloakService;

    private final String operatorId;
    private final String username;
    private final String email;

    UserRepresentation operator = new UserRepresentation();

    /* ----- CONSTRUCTOR ----- */

    public GetOperatorCommand(String operatorId, String username, String email) {
        this.operatorId = operatorId;
        this.username = username;
        this.email = email;
    }


    /* ----- METHODS ----- */


    public ResponseEntity<UserRepresentation> execute() {
        log.info("START execute() of GetOperatorCommand");

        if (StringUtils.isEmpty(operatorId) && StringUtils.isEmpty(username) && StringUtils.isEmpty(email)) {
            log.info("At least one between operator ID, username or email must be provided");
            return ResponseEntity.badRequest().build();
        }

        // search by operatorID

        if (operatorId != null) {
            log.info("Searching with operatorId: {}", operatorId);

            operator = keycloakService.getOperatorById(operatorId);

            if (operator == null) {
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.ok(operator);
        }

        // search by username

        if (username != null) {
            log.info("Searching with username: {}", username);

            List<UserRepresentation> operatorsList = keycloakService.getOperatorByUsername(username);

            if (operatorsList.stream().noneMatch(u -> u.getUsername().equalsIgnoreCase(username))) {
                return ResponseEntity.ok().build();
            }

            operatorsList.forEach(userRepresentation -> {
                if (userRepresentation.getUsername().equalsIgnoreCase(username)) {
                    operator = userRepresentation;
                }
            });

            OperatorGroupsAndRoles operatorGroupsAndRoles = getOperatorGroupsAndRoles(operator.getId());
            operator.setGroups(operatorGroupsAndRoles.getOperatorGroups().stream().map(GroupRepresentation::getName).toList());
            operator.setRealmRoles(operatorGroupsAndRoles.getOperatorRoles().stream().map(RoleRepresentation::getName).toList());

            return ResponseEntity.ok(operator);
        }

        // search by email

        log.info("Searching with email: {}", email);

        List<UserRepresentation> operatorsList = keycloakService.getOperatorByEmail(email);

        if (operatorsList.stream().noneMatch(u -> u.getEmail().equalsIgnoreCase(email))) {
            return ResponseEntity.ok().build();
        }

        operatorsList.forEach(userRepresentation -> {
            if (userRepresentation.getEmail().equalsIgnoreCase(email)) {
                operator = userRepresentation;
            }
        });

        OperatorGroupsAndRoles operatorGroupsAndRoles = getOperatorGroupsAndRoles(operator.getId());
        operator.setGroups(operatorGroupsAndRoles.getOperatorGroups().stream().map(GroupRepresentation::getName).toList());
        operator.setRealmRoles(operatorGroupsAndRoles.getOperatorRoles().stream().map(RoleRepresentation::getName).toList());

        return ResponseEntity.ok(operator);
    }


    /* ----- AUXILIARY METHODS ----- */


    private OperatorGroupsAndRoles getOperatorGroupsAndRoles(String operatorId) {

        OperatorGroupsAndRoles operatorGroupsAndRoles = new OperatorGroupsAndRoles();
        List<RoleRepresentation> operatorRoles = new ArrayList<>();
        List<GroupRepresentation> operatorGroups;

        log.info("Fetching operator groups and roles");

        operatorGroups = keycloakService.getOperatorGroups(operatorId);
        operatorGroups.forEach(group -> {
            List<RoleRepresentation> groupRealmRolesResponse = keycloakService.getGroupRealmRoles(group.getId());
            operatorRoles.addAll(groupRealmRolesResponse);
        });

        operatorGroupsAndRoles.setOperatorGroups(operatorGroups);
        operatorGroupsAndRoles.setOperatorRoles(operatorRoles);

        return operatorGroupsAndRoles;
    }
}
