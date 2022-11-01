package org.moonbox.operatormodule.commands;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.GroupRepresentation;
import org.moonbox.operatormodule.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Scope("prototype")
public class GetRealmGroupsCommand {

    /* ----- PARAMETERS ----- */

    @Autowired
    KeycloakService keycloakService;

    private final String groupId;
    private final String groupName;


    /* ----- CONSTRUCTOR ----- */


    public GetRealmGroupsCommand(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }


    /* ----- METHODS ----- */


    public ResponseEntity<GroupRepresentation> execute() {

        List<GroupRepresentation> searchResult;
        GroupRepresentation realmGroup = null;

        if (StringUtils.isEmpty(groupId) && StringUtils.isEmpty(groupName)) {
            log.info(("At least one between group ID and group name must be provided"));
            return ResponseEntity.badRequest().build();
        }

        if (StringUtils.isNotEmpty(groupId)) {
            realmGroup = keycloakService.getRealmGroupById(groupId);
        }

        if (StringUtils.isNotEmpty(groupName)) {
            searchResult = keycloakService.getRealmGroupByName(groupName);
            if (searchResult.isEmpty()) {
                return ResponseEntity.ok().build();
            }
            realmGroup = searchResult.get(0);
        }

        if (realmGroup == null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.ok(realmGroup);
    }

}
