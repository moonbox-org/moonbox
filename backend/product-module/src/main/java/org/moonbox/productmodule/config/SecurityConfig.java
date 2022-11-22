package org.moonbox.productmodule.config;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    private static final String SUPERUSER = "superuser";
    private static final String[] PUBLIC_URLS = {"/actuator/**", "/public/**"};
    private static final String[] PROTECTED_URLS = {"/api/**"};
    private static final String[] ALL_PATHS = {"**"};
    private static final String[] ALLOWED_HTTP_METHODS = {HttpMethod.HEAD.toString(), HttpMethod.OPTIONS.toString()};

    private static final String[] READ_AUTHORITY_ROLES = {
            "product:read",
            SUPERUSER
    };
    private static final String[] WRITE_AUTHORITY_ROLES = {
            "product:write",
            SUPERUSER
    };
    private static final String[] DELETE_AUTHORITY_ROLES = {
            "product:delete",
            SUPERUSER
    };

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Autowired
    public KeycloakClientRequestFactory keycloakClientRequestFactory;

    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(KeycloakAuthenticationProcessingFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(KeycloakPreAuthActionsFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakAuthenticatedActionsFilterBean(KeycloakAuthenticatedActionsFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakSecurityContextRequestFilterBean(KeycloakSecurityContextRequestFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    @Override
    @ConditionalOnMissingBean(HttpSessionManager.class)
    protected HttpSessionManager httpSessionManager() {
        return new HttpSessionManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http
                .authorizeRequests()
                // public URLs and methods
                .antMatchers(PUBLIC_URLS).permitAll()
                .antMatchers(ALLOWED_HTTP_METHODS).permitAll()
                // protected URLs
                .antMatchers(GET, PROTECTED_URLS).hasAnyRole(READ_AUTHORITY_ROLES)
                .antMatchers(PUT, PROTECTED_URLS).hasAnyRole(WRITE_AUTHORITY_ROLES)
                .antMatchers(POST, PROTECTED_URLS).hasAnyRole(WRITE_AUTHORITY_ROLES)
                .antMatchers(PATCH, PROTECTED_URLS).hasAnyRole(WRITE_AUTHORITY_ROLES)
                .antMatchers(DELETE, PROTECTED_URLS).hasAnyRole(DELETE_AUTHORITY_ROLES)
                // superuser rule
                .antMatchers(ALL_PATHS).hasRole(SUPERUSER)
                // other requests
                .anyRequest().authenticated()
                .and()
                // session management
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                // csrf
                .csrf().disable();
    }
}
