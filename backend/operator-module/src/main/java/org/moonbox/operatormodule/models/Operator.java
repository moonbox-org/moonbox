package org.moonbox.operatormodule.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.Set;

@Getter
@Setter
@Builder
public class Operator {

    @JsonProperty("id")
    private String id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @Email
    @JsonProperty("email")
    private String email;
    @JsonProperty("emailVerified")
    private boolean emailVerified;
    @JsonProperty("username")
    private String username;
    @JsonProperty("roles")
    private Set<String> roles;
}
