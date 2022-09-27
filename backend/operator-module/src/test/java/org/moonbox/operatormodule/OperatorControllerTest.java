package org.moonbox.operatormodule;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.*;
import org.moonbox.operatormodule.controllers.OperatorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OperatorControllerTest {

    /*----- PARAMETERS -----*/


    @Autowired
    MockMvc mockMvc;

    @Autowired
    OperatorController operatorController;

    @Container
    static KeycloakContainer KEYCLOAK = new KeycloakContainer("quay.io/keycloak/keycloak:19.0.1")
            .withRealmImportFile("keycloak/moonbox-realm-export.json");

    static RestTemplate restTemplate = new RestTemplate();
    private static String authToken;

    /*----- TESTS -----*/

    @DynamicPropertySource
    static void keycloakTestProperties(DynamicPropertyRegistry registry) {
        registry.add("keycloak.auth-server-url", () -> KEYCLOAK.getAuthServerUrl());
    }

    @BeforeAll
    static void getAuthToken() {
        assertTrue(KEYCLOAK.isRunning());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("grant_type", Collections.singletonList("password"));
        formData.put("client_id", Collections.singletonList("moonbox-operator-module-dev"));
        formData.put("client_secret", Collections.singletonList("testContainerSecret"));
        formData.put("username", Collections.singletonList("superuser"));
        formData.put("password", Collections.singletonList("password"));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        String response = restTemplate.postForEntity(KEYCLOAK.getAuthServerUrl() + "/realms/moonbox/protocol/openid-connect/token", request, String.class).getBody();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        authToken = jsonParser.parseMap(response).get("access_token").toString();
    }

    @Test
    @Order(1)
    @DisplayName("context loads")
    void contextLoads() {
        Assertions.assertNotNull(operatorController);
    }

    @Test
    @DisplayName("return default string")
    void testShouldReturnDefaultString() throws Exception {
        mockMvc
                .perform(get("/api/v1/operator/test").header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk());
    }
}
