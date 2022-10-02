package org.moonbox.operatormodule;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moonbox.operatormodule.controllers.OperatorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OperatorControllerTest {

    /*----- PARAMETERS -----*/

    public static final String KEYCLOAK_CONTAINER_IMAGE = "quay.io/keycloak/keycloak:19.0.1";
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

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OperatorController operatorController;

    @Container
    static KeycloakContainer KEYCLOAK = new KeycloakContainer(KEYCLOAK_CONTAINER_IMAGE)
            .withRealmImportFile(KEYCLOAK_REALM_IMPORT_FILE);

    @DynamicPropertySource
    static void keycloakTestProperties(DynamicPropertyRegistry registry) {
        registry.add("keycloak.auth-server-url", () -> KEYCLOAK.getAuthServerUrl());
    }

    static RestTemplate restTemplate = new RestTemplate();

    private static String superuserAuthToken;
    private static String operatorAuthToken;

    /*----- TESTS -----*/


    @BeforeAll
    static void getAuthToken() {
        assertTrue(KEYCLOAK.isRunning());

        HttpHeaders headers = new HttpHeaders();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
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

        String superuserResponse = restTemplate.postForEntity(KEYCLOAK.getAuthServerUrl() + TOKEN_PATH, superuserRequest, String.class).getBody();
        String operatorResponse = restTemplate.postForEntity(KEYCLOAK.getAuthServerUrl() + TOKEN_PATH, operatorRequest, String.class).getBody();

        superuserAuthToken = jsonParser.parseMap(superuserResponse).get(ACCESS_TOKEN).toString();
        operatorAuthToken = jsonParser.parseMap(operatorResponse).get(ACCESS_TOKEN).toString();
    }

    @Test
    @DisplayName("context loads")
    void contextLoads() {
        Assertions.assertNotNull(operatorController);
    }

    @Test
    @DisplayName("return 3xx redirect")
    void testShouldReturn3xxRedirect() throws Exception {
        mockMvc
                .perform(get("/api/v1/operators/test"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("return default string")
    void testShouldReturnDefaultString() throws Exception {
        mockMvc
                .perform(get("/api/v1/operators/test").header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("return logged-in superuser")
    void testShouldReturnLoggedInSuperuser() throws Exception {
        mockMvc
                .perform(get("/api/v1/operators/loggedInOperator").header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("superuser"));
    }

    @Test
    @DisplayName("return logged-in operator user")
    void testShouldReturnLoggedInOperator() throws Exception {
        mockMvc
                .perform(get("/api/v1/operators/loggedInOperator").header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + operatorAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("operator"));
    }

}
