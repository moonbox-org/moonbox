package org.moonbox.operatormodule.services;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.moonbox.operatormodule.models.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Service
public class KeycloakService {


    /* ----- PARAMETERS ----- */


    @Value("${keycloak.realm}")
    private String realm;
    @Value("keycloak.credentials.secret")
    private String clientSecret;
    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Autowired
    private Keycloak keycloakRestTemplate;


    /* ----- METHODS ----- */


    public Operator getLoggedInOperator() {
        log.info("START getLoggedInOperator() of KeycloakService");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();

        return Operator.builder()
                .id(accessToken.getSubject())
                .firstName(accessToken.getGivenName())
                .lastName(accessToken.getFamilyName())
                .email(accessToken.getEmail())
                .emailVerified(accessToken.getEmailVerified())
                .username(accessToken.getPreferredUsername())
                .roles(accessToken.getRealmAccess().getRoles())
                .build();
    }

    public List<UserRepresentation> getOperators() {
        log.info("Fetching all realm operators");

        List<UserRepresentation> operators;

        try {
            operators = keycloakRestTemplate
                    .realm(realm)
                    .users()
                    .list();
        } catch (NotFoundException e) {
            if (e.getMessage().contains("404")) {
                log.info("No operators found in database");
                return Collections.emptyList();
            } else {
                log.info("An error occurred trying to fetch all operators: {}", e.getMessage());
                throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
            }
        }
        return operators;
    }

    public UserRepresentation getOperatorById(String operatorId) {

        UserRepresentation operator;
        List<RoleRepresentation> groupRealmRoleRepresentations = new ArrayList<>();

        try {
            operator = keycloakRestTemplate
                    .realm("moonbox")
                    .users()
                    .get(operatorId).toRepresentation();
        } catch (Exception e) {
            if (e.getMessage().contains("404 Not Found")) {
                log.info("No operator found for operatorId: {}", operatorId);
                return null;
            } else {
                log.error("An error occurred trying to fetch operator with operatorId: {}", operatorId);
                throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
            }
        }

        List<GroupRepresentation> operatorGroupRepresentations = keycloakRestTemplate
                .realm(realm)
                .users()
                .get(operatorId)
                .groups();

        if (!operatorGroupRepresentations.isEmpty()) {
            operatorGroupRepresentations.forEach(g -> {
                groupRealmRoleRepresentations.addAll(keycloakRestTemplate
                        .realm(realm)
                        .groups()
                        .group(g.getId())
                        .roles()
                        .realmLevel()
                        .listAll());
            });
        }
        operator.setGroups(operatorGroupRepresentations.isEmpty() ? Collections.emptyList() : operatorGroupRepresentations.stream().map(GroupRepresentation::getName).toList());
        operator.setRealmRoles(groupRealmRoleRepresentations.isEmpty() ? Collections.emptyList() : groupRealmRoleRepresentations.stream().map(RoleRepresentation::getName).toList());
        return operator;
    }

    public List<UserRepresentation> getOperatorByUsername(String username) {

        List<UserRepresentation> searchResult = Collections.emptyList();

        try {
            searchResult = keycloakRestTemplate
                    .realm(realm)
                    .users()
                    .search(username, true);
        } catch (Exception e) {
            if (e.getMessage().contains("404")) {
                log.info("No operator found for username: {}", username);
            } else {
                log.error("An error occurred trying to fetch operator with username: {}", username);
                throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
            }
        }
        return searchResult;
    }

    public List<UserRepresentation> getOperatorByEmail(String email) {

        List<UserRepresentation> searchResult = Collections.emptyList();

        try {
            searchResult = keycloakRestTemplate
                    .realm(realm)
                    .users()
                    .search("", "", "", email, 0, 1);
        } catch (Exception e) {
            if (e.getMessage().contains("404")) {
                log.info("No operator found for email: {}", email);
            } else {
                log.error("An error occurred trying to fetch operator with email: {}", email);
                throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
            }
        }
        return searchResult;
    }

    public List<GroupRepresentation> getOperatorGroups(String operatorId) {
        log.info("Fetching groups for operator with operator ID: {}", operatorId);

        List<GroupRepresentation> operatorGroups = new ArrayList<>();

        try {
            operatorGroups = keycloakRestTemplate
                    .realm(realm)
                    .users()
                    .get(operatorId)
                    .groups();
        } catch (Exception e) {
            if (e.getMessage().contains("404")) {
                log.info("No operator found for operatorId: {}", operatorId);
            } else {
                log.error("An error occurred trying to fetch operator with operatorId: {}", operatorId);
                throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
            }
        }
        return operatorGroups;
    }

    public List<RoleRepresentation> getGroupRealmRoles(String groupId) {
        log.info("Fetching realm roles for group with group ID: {}", groupId);

        List<RoleRepresentation> groupRealmRoles;

        try {
            groupRealmRoles = keycloakRestTemplate
                    .realm(realm)
                    .groups()
                    .group(groupId)
                    .roles()
                    .realmLevel()
                    .listAll();
        } catch (Exception e) {
            log.error("An error occurred trying to fetch realm roles for group with groupId: {}", groupId);
            throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
        }
        return groupRealmRoles;
    }

    public List<GroupRepresentation> getRealmGroups() {

        List<GroupRepresentation> realmGroups;

        try {
            realmGroups = keycloakRestTemplate
                    .realm(realm)
                    .groups()
                    .groups();
        } catch (Exception e) {
            log.error("An error occurred trying to fetch realm groups");
            throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
        }
        return realmGroups;
    }

    public GroupRepresentation getRealmGroupById(String groupId) {

        GroupRepresentation realmGroup;

        try {
            realmGroup = keycloakRestTemplate
                    .realm(realm)
                    .groups()
                    .group(groupId)
                    .toRepresentation();
        } catch (Exception e) {
            if (e.getMessage().contains("404 Not Found")) {
                log.info("No group found for groupId: {}", groupId);
                return null;
            } else {
                log.error("An error occurred trying to fetch group with id: {}", groupId);
                throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
            }
        }
        return realmGroup;
    }

    public List<RoleRepresentation> getRealmRoles() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("briefRepresentation", Collections.singletonList("false"));

        List<RoleRepresentation> realmRoles;

        try {
            realmRoles = keycloakRestTemplate
                    .realm(realm)
                    .roles()
                    .list();
        } catch (Exception e) {
            log.error("An error occurred trying to fetch realm roles");
            throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
        }
        return realmRoles;
    }

    public RoleRepresentation getRealmRoleByName(String roleName) {

        RoleRepresentation realmRole;

        try {
            realmRole = keycloakRestTemplate
                    .realm(realm)
                    .roles()
                    .get(roleName)
                    .toRepresentation();
        } catch (Exception e) {
            if (e.getMessage().contains("404 Not Found")) {
                log.info("No role found for roleName: {}", roleName);
                return null;
            } else {
                log.error("An error occurred trying to fetch role with name: {}", roleName);
                throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
            }
        }
        return realmRole;
    }

    public RoleRepresentation getRealmRoleById(String roleId) {

        RoleRepresentation realmRole;

        try {
            realmRole = keycloakRestTemplate
                    .realm(realm)
                    .rolesById()
                    .getRole(roleId);
        } catch (Exception e) {
            if (e.getMessage().contains("404 Not Found")) {
                log.info("No role found for roleId: {}", roleId);
                return null;
            } else {
                log.error("An error occurred trying to fetch role with ID: {}", roleId);
                throw new ServerErrorException(e.getMessage(), INTERNAL_SERVER_ERROR.value());
            }
        }
        return realmRole;
    }


    /* ----- AUXILIARY METHODS ----- */


}
