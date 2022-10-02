package org.moonbox.operatormodule.services;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.moonbox.operatormodule.models.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class KeycloakService {


    /* ----- PARAMETERS ----- */


    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Autowired
    private KeycloakRestTemplate restTemplate;

    @Autowired
    private Keycloak keycloakAdminClient;

    private static final String KC_USERS_ENDPOINT = "/admin/realms/moonbox/users";
    private static final String KC_GROUPS_ENDPOINT = "/admin/realms/moonbox/groups";
    private static final String KC_ROLES_ENDPOINT = "/admin/realms/moonbox/roles";


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
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("briefRepresentation", Collections.singletonList("false"));
        try {
            log.info("Fetching all realm operators");
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForEntity(keycloakUrl + KC_USERS_ENDPOINT, UserRepresentation[].class, queryParams).getBody())).toList();
        } catch (Exception e) {
            log.info("An error occurred trying to fetch all operators");
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public ResponseEntity<UserRepresentation> getOperatorById(String operatorId) {
        try {
            log.info("Searching for operator with ID: {}", operatorId);
            return restTemplate.getForEntity(keycloakUrl + KC_USERS_ENDPOINT + "/" + operatorId, UserRepresentation.class);
        } catch (Exception e) {
            if (e.getMessage().contains("404 Not Found")) {
                log.info("No operator found for operator ID: {}", operatorId);
                return ResponseEntity.notFound().build();
            } else {
                log.info("An error occurred trying to fetch operator with ID: {}", operatorId);
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    public List<UserRepresentation> getOperatorByUsername(String username) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("exact", Collections.singletonList("true"));
        queryParams.put("username", Collections.singletonList(username));
        queryParams.put("briefRepresentation", Collections.singletonList("false"));
        try {
            log.info("Searching for operator with username: {}", username);
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForEntity(keycloakUrl + KC_USERS_ENDPOINT, UserRepresentation[].class, queryParams).getBody())).toList();
        } catch (Exception e) {
            log.info("An error occurred trying to fetch operator with username: {}", username);
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<UserRepresentation> getOperatorByEmail(String email) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("exact", Collections.singletonList("true"));
        queryParams.put("email", Collections.singletonList(email));
        queryParams.put("briefRepresentation", Collections.singletonList("false"));
        try {
            log.info("Searching for operator with email: {}", email);
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForEntity(keycloakUrl + KC_USERS_ENDPOINT, UserRepresentation[].class, queryParams).getBody())).toList();
        } catch (Exception e) {
            log.info("An error occurred trying to fetch operator with email: {}", email);
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<GroupRepresentation> getOperatorGroups(String operatorId) {
        try {
            log.info("Fetching groups for operator with operator ID: {}", operatorId);
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForEntity(keycloakUrl + KC_USERS_ENDPOINT + "/" + operatorId + "/groups", GroupRepresentation[].class).getBody())).toList();
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<RoleRepresentation> getGroupRealmRoles(String groupId) {
        try {
            log.info("Fetching realm roles for group with group ID: {}", groupId);
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForEntity(keycloakUrl + KC_GROUPS_ENDPOINT + "/" + groupId + "/role-mappings/realm", RoleRepresentation[].class).getBody())).toList();
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<GroupRepresentation> getRealmGroups() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("briefRepresentation", Collections.singletonList("false"));
        try {
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForEntity(keycloakUrl + KC_GROUPS_ENDPOINT, GroupRepresentation[].class, queryParams).getBody())).toList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<RoleRepresentation> getRealmRoles() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("briefRepresentation", Collections.singletonList("false"));
        try {
            return Arrays.stream(Objects.requireNonNull(restTemplate.getForEntity(keycloakUrl + KC_ROLES_ENDPOINT, RoleRepresentation[].class, queryParams).getBody())).toList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
