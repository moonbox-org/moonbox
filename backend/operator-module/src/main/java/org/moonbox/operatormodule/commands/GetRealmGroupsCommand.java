package org.moonbox.operatormodule.commands;

import io.micrometer.core.instrument.util.StringUtils;
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

    private final String groupId;


    /* ----- CONSTRUCTOR ----- */


    public GetRealmGroupsCommand(String groupId) {
        this.groupId = groupId;
    }


    /* ----- METHODS ----- */


    public ResponseEntity<GroupRepresentation> execute() {

        GroupRepresentation realmGroup;

        if (StringUtils.isEmpty(groupId)) {
            log.info(("Group ID must be provided"));
            return ResponseEntity.badRequest().build();
        }

        realmGroup = keycloakService.getRealmGroupById(groupId);

        if (realmGroup == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(realmGroup);
    }

}
