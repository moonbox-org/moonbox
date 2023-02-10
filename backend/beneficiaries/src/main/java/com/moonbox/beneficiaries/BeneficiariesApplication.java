package com.moonbox.beneficiaries;

import com.moonbox.beneficiaries.config.AuthorizationHeaderInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@EnableJpaAuditing
@SpringBootApplication
public class BeneficiariesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeneficiariesApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.setInterceptors(Collections.singletonList(new AuthorizationHeaderInterceptor()));
        return template;
    }
}
