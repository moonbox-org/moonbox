package com.moonbox.beneficiaries.services;

import com.moonbox.beneficiaries.entities.Country;
import com.moonbox.beneficiaries.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Iterable<Country> list() {
        return countryRepository.findAll();
    }

    public Iterable<Country> save(List<Country> countries) {
        return countryRepository.saveAll(countries);
    }

    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }
}
