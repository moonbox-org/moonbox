package org.moonbox.operatormodule.controllers;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.GroupRepresentation;
import org.moonbox.operatormodule.commands.GetRealmGroupsCommand;
import org.moonbox.operatormodule.services.KeycloakService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/groups")
public class GroupsController {


    /* ----- PARAMETERS ----- */

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    KeycloakService keycloakService;


    /* ----- METHODS ----- */


    @GetMapping(value = "/list")
    ResponseEntity<List<GroupRepresentation>> getGroups() {
        return ResponseEntity.ok(keycloakService.getRealmGroups());
    }

    @GetMapping()
    ResponseEntity<GroupRepresentation> getGroupById(
            @RequestParam(name = "groupId", required = true) String groupId
    ) {
        GetRealmGroupsCommand command = beanFactory.getBean(GetRealmGroupsCommand.class, groupId);
        return command.execute();
    }
}
