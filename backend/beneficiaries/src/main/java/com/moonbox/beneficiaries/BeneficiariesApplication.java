package com.moonbox.beneficiaries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonbox.beneficiaries.config.AuthorizationHeaderInterceptor;
import com.moonbox.beneficiaries.entities.Continent;
import com.moonbox.beneficiaries.entities.Country;
import com.moonbox.beneficiaries.repositories.ContinentRepository;
import com.moonbox.beneficiaries.services.CountryService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

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

    @Bean
    ApplicationRunner applicationRunner(CountryService countryService, ContinentRepository continentRepository) {
        return args -> {
            initDatabaseData(countryService, continentRepository);
        };
    }

    private void initDatabaseData(CountryService countryService, ContinentRepository continentRepository) {
        // read json
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Country>> typeReference = new TypeReference<List<Country>>() {
        };

        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/countries_languages_continents_lookups_noid.json");

        try {
            List<Country> countries = mapper.readValue(inputStream, typeReference);

            for (Country c : countries) {
                if (c.getContinent() != null) {
                    Continent continent = continentRepository.findByCode(c.getContinent().getCode());
                    if (continent != null) {
                        c.setContinent(continent);
                    } else {
                        Continent continentToSave = c.getContinent();
                        continent = continentRepository.save(continentToSave);
                    }
                    c.setContinent(continent);
                    countryService.saveCountry(c);
                } else {
                    countryService.saveCountry(c);
                }
            }
            System.out.println("Countries saved.");
        } catch (IOException e) {
            System.out.println("Unable to save countries: " + e.getMessage());
        }
    }
}
