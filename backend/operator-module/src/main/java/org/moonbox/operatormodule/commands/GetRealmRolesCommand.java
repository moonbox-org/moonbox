package org.moonbox.operatormodule.commands;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.moonbox.operatormodule.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
public class GetRealmRolesCommand {


    /* ----- PARAMETERS ----- */


    @Autowired
    KeycloakService keycloakService;

    private final String roleId;
    private final String roleName;

    RoleRepresentation role = new RoleRepresentation();


    /* ----- CONSTRUCTOR ----- */


    public GetRealmRolesCommand(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }


    /* ----- METHODS ----- */


    public ResponseEntity<RoleRepresentation> execute() {

        if (StringUtils.isEmpty(roleId) && StringUtils.isEmpty(roleName)) {
            log.info("At least one between role ID and role name must be provided");
            return ResponseEntity.badRequest().build();
        }

        if (!StringUtils.isEmpty(roleId)) {
            role = keycloakService.getRealmRoleById(roleId);
        }

        if (!StringUtils.isEmpty(roleName)) {
            role = keycloakService.getRealmRoleByName(roleName);
        }

        if (role == null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.ok(role);
    }
}
