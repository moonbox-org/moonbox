package com.moonbox.beneficiaries.controllers;

import com.moonbox.beneficiaries.entities.Country;
import com.moonbox.beneficiaries.repositories.CountryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/countries")
public class CountriesController {

    private final CountryRepository countryRepository;

    public CountriesController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<Country> listCountries() {
        return countryRepository.findAll();
    }
}
