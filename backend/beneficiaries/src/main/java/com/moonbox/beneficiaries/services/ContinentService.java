package com.moonbox.beneficiaries.services;

import com.moonbox.beneficiaries.repositories.ContinentRepository;
import org.springframework.stereotype.Service;

@Service
public class ContinentService {

    private final ContinentRepository continentRepository;

    public ContinentService(ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }
}
