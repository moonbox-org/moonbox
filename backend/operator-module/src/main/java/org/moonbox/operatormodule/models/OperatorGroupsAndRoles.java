package org.moonbox.operatormodule.models;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

@Getter
@Setter
public class OperatorGroupsAndRoles {

    private List<GroupRepresentation> operatorGroups;
    private List<RoleRepresentation> operatorRoles;

}
