package org.moonbox.operatormodule;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class BaseTest {

    /*----- PARAMETERS -----*/

    public static final String KEYCLOAK_CONTAINER_IMAGE = "quay.io/keycloak/keycloak:20.0.0";
    public static final String KEYCLOAK_REALM_IMPORT_FILE = "keycloak/moonbox-realm-export.json";
    public static final String GRANT_TYPE = "grant_type";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String OPERATOR_MODULE_CLIENT_ID = "moonbox-operator-module-dev";
    public static final String OPERATOR_MODULE_CLIENT_SECRET = "testContainerSecret";
    public static final String TOKEN_PATH = "/realms/moonbox/protocol/openid-connect/token";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";

    public static final String SUPERUSER_USERNAME = "superuser";
    public static final String SUPERUSER_PASSWORD = "password";
    public static final String OPERATOR_USERNAME = "operator";
    public static final String OPERATOR_PASSWORD = "password";

    public static String superuserAuthToken;
    public static String operatorAuthToken;

    @Container
    static KeycloakContainer KEYCLOAK = new KeycloakContainer(KEYCLOAK_CONTAINER_IMAGE)
            .withRealmImportFile(KEYCLOAK_REALM_IMPORT_FILE);

    @DynamicPropertySource
    static void keycloakTestProperties(DynamicPropertyRegistry registry) {
        registry.add("keycloak.auth-server-url", () -> KEYCLOAK.getAuthServerUrl());
        registry.add("keycloak.credentials.secret", () -> "testContainerSecret");
    }

    static JacksonJsonParser jsonParser = new JacksonJsonParser();
    static RestTemplate restTemplate = new RestTemplate();
    static String superuserResponse;
    static String operatorResponse;


    /*----- METHODS -----*/


    @BeforeAll
    static void getAuthToken() {
        assertTrue(KEYCLOAK.isRunning());

        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> superuserFormData = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> operatorFormData = new LinkedMultiValueMap<>();

        headers.setContentType(APPLICATION_FORM_URLENCODED);

        superuserFormData.put(GRANT_TYPE, Collections.singletonList(PASSWORD));
        superuserFormData.put(CLIENT_ID, Collections.singletonList(OPERATOR_MODULE_CLIENT_ID));
        superuserFormData.put(CLIENT_SECRET, Collections.singletonList(OPERATOR_MODULE_CLIENT_SECRET));
        superuserFormData.put(USERNAME, Collections.singletonList(SUPERUSER_USERNAME));
        superuserFormData.put(PASSWORD, Collections.singletonList(SUPERUSER_PASSWORD));

        operatorFormData.put(GRANT_TYPE, Collections.singletonList(PASSWORD));
        operatorFormData.put(CLIENT_ID, Collections.singletonList(OPERATOR_MODULE_CLIENT_ID));
        operatorFormData.put(CLIENT_SECRET, Collections.singletonList(OPERATOR_MODULE_CLIENT_SECRET));
        operatorFormData.put(USERNAME, Collections.singletonList(OPERATOR_USERNAME));
        operatorFormData.put(PASSWORD, Collections.singletonList(OPERATOR_PASSWORD));

        HttpEntity<MultiValueMap<String, String>> superuserRequest = new HttpEntity<>(superuserFormData, headers);
        HttpEntity<MultiValueMap<String, String>> operatorRequest = new HttpEntity<>(operatorFormData, headers);

        superuserResponse = restTemplate.postForEntity(KEYCLOAK.getAuthServerUrl() + TOKEN_PATH, superuserRequest, String.class).getBody();
        operatorResponse = restTemplate.postForEntity(KEYCLOAK.getAuthServerUrl() + TOKEN_PATH, operatorRequest, String.class).getBody();

        superuserAuthToken = jsonParser.parseMap(superuserResponse).get(ACCESS_TOKEN).toString();
        operatorAuthToken = jsonParser.parseMap(operatorResponse).get(ACCESS_TOKEN).toString();
    }
}
