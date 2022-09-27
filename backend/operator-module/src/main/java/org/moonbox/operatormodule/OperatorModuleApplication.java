package org.moonbox.operatormodule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class OperatorModuleApplication {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
    }

    public static void main(String[] args) {
        SpringApplication.run(OperatorModuleApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            log.info("Currently active profile: " + Arrays.toString(environment.getActiveProfiles()));
            log.info("Spring boot application running in UTC timezone: " + new Date());   // It will print UTC timezone
        };
    }

}
