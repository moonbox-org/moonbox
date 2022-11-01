package org.moonbox.operatormodule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moonbox.operatormodule.controllers.OperatorController;
import org.moonbox.operatormodule.controllers.RolesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OperatorModuleApplicationTest extends BaseTest {


    /* ----- CONSTANTS ----- */


    // operators controller endpoints
    private static final String OPERATORS_TEST = "/api/v1/operators/test";
    private static final String OPERATORS = "/api/v1/operators";
    private static final String OPERATORS_LIST = "/api/v1/operators/list";
    private static final String OPERATORS_LOGGED_IN_OPERATOR = "/api/v1/operators/loggedInOperator";

    // roles controller endpoints
    private static final String ROLES = "/api/v1/roles";
    private static final String ROLES_LIST = "/api/v1/roles/list";

    // groups controller endpoints
    private static final String GROUPS = "/api/v1/groups";
    private static final String GROUPS_LIST = "/api/v1/groups/list";


    /* ----- PARAMETERS ----- */


    @Autowired
    MockMvc mockMvc;

    @Autowired
    OperatorController operatorController;

    @Autowired
    RolesController rolesController;


    /* ----- TESTS ----- */

    @Test
    void contextLoads() {
        Assertions.assertNotNull(operatorController);
        Assertions.assertNotNull(rolesController);
    }

    @Test
    @DisplayName("return 3xx redirect")
    void testShouldReturn3xxRedirect() throws Exception {
        mockMvc
                .perform(get(OPERATORS_TEST))
                .andExpect(status().is3xxRedirection());

        mockMvc
                .perform(get(ROLES_LIST))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("return default string")
    void testShouldReturnDefaultString() throws Exception {
        mockMvc
                .perform(get(OPERATORS_TEST).header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("return logged-in superuser")
    void testShouldReturnLoggedInSuperuser() throws Exception {
        mockMvc
                .perform(get(OPERATORS_LOGGED_IN_OPERATOR).header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("superuser"));
    }

    @Test
    @DisplayName("return logged-in operator user")
    void testShouldReturnLoggedInOperator() throws Exception {
        mockMvc
                .perform(get(OPERATORS_LOGGED_IN_OPERATOR).header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + operatorAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("operator"));
    }

    @Test
    @DisplayName("return operator by operatorId")
    void testShouldReturnOperatorByOperatorId() throws Exception {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("operatorId", Collections.singletonList("1234-5678"));

        mockMvc
                .perform(get(OPERATORS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1234-5678"));

        queryParams.put("operatorId", Collections.singletonList("non-existent-id"));

        mockMvc
                .perform(get(OPERATORS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DisplayName("return operator by email")
    void testShouldReturnOperatorByEmail() throws Exception {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("email", Collections.singletonList("superuser@test.com"));

        mockMvc
                .perform(get(OPERATORS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("superuser@test.com"));

        queryParams.put("email", Collections.singletonList("operator@test.com"));

        mockMvc
                .perform(get(OPERATORS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("operator@test.com"));

        queryParams.put("email", Collections.singletonList("non-existent-email"));

        mockMvc
                .perform(get(OPERATORS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DisplayName("return operator by username")
    void testShouldReturnOperatorByUsername() throws Exception {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("username", Collections.singletonList("superuser"));

        mockMvc
                .perform(get(OPERATORS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("superuser"));

        queryParams.put("username", Collections.singletonList("non-existent-username"));

        mockMvc
                .perform(get(OPERATORS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DisplayName("return bad request")
    void testShouldReturnBadRequest() throws Exception {
        mockMvc
                .perform(get(OPERATORS)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("return all operators")
    void testShouldReturnAllOperators() throws Exception {
        mockMvc
                .perform(get(OPERATORS_LIST)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasSize(2)));
    }


    /* ----- ROLES CONTROLLER ----- */


    @Test
    @DisplayName("get role by ID")
    void testShouldReturnRolesList() throws Exception {
        mockMvc
                .perform(get(ROLES_LIST)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("get role by ID bad request")
    void testShouldReturnBadRequestForRoles() throws Exception {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("roleName", Collections.singletonList(""));

        mockMvc
                .perform(get(ROLES)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("get role by name or id")
    void testShouldReturnRoleByNameOrId() throws Exception {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("roleName", Collections.singletonList("operator:read"));

        mockMvc
                .perform(get(ROLES)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("operator:read"));

        queryParams.clear();
        queryParams.put("roleName", Collections.singletonList("non-existent-name"));

        mockMvc
                .perform(get(ROLES)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        queryParams.clear();
        queryParams.put("roleId", Collections.singletonList("8aec9f53-b9fd-4c67-ab94-0c51c04beaa4"));

        mockMvc
                .perform(get(ROLES)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("operator:write"));

        queryParams.clear();
        queryParams.put("roleId", Collections.singletonList("non-existent-id"));

        mockMvc
                .perform(get(ROLES)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }


    /* ----- ROLES CONTROLLER ----- */


    @Test
    @DisplayName("get all groups")
    void testShouldReturnGroupsList() throws Exception {
        mockMvc
                .perform(get(GROUPS_LIST)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("get group by id")
    void testShouldReturnGroupByIdOrGroupName() throws Exception {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.put("groupId", Collections.singletonList("87555c87-d6a7-4961-91c8-0e57cb70781d"));

        mockMvc
                .perform(get(GROUPS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("moonbox-operators"));

        queryParams.clear();

        mockMvc.perform(get(GROUPS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isBadRequest()
                );

        queryParams.put("groupId", Collections.singletonList("non-existent-groupId"));

        mockMvc.perform(get(GROUPS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        queryParams.clear();
        queryParams.put("groupName", Collections.singletonList("moonbox-operators"));

        mockMvc.perform(get(GROUPS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("moonbox-operators"));

        queryParams.clear();
        queryParams.put("groupName", Collections.singletonList("moonbox-oper"));

        mockMvc.perform(get(GROUPS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("moonbox-operators"));

        queryParams.clear();
        queryParams.put("groupName", Collections.singletonList("super"));

        mockMvc.perform(get(GROUPS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("moonbox-superusers"));

        queryParams.clear();
        queryParams.put("groupName", Collections.singletonList("non-existent-name"));

        mockMvc.perform(get(GROUPS)
                        .params(queryParams)
                        .header(AUTHORIZATION_HEADER, AUTHORIZATION_TOKEN_PREFIX + superuserAuthToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
