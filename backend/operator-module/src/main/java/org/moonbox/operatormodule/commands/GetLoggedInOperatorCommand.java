package org.moonbox.operatormodule.commands;

import lombok.extern.slf4j.Slf4j;
import org.moonbox.operatormodule.models.Operator;
import org.moonbox.operatormodule.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
public class GetLoggedInOperatorCommand {

    /* ----- PARAMETERS ----- */

    @Autowired
    KeycloakService keycloakService;

    /* ----- METHODS ----- */

    public Operator execute() {
        log.info("START execute() of GetLoggedInOperatorCommand");
        return keycloakService.getLoggedInOperator();
    }

}
