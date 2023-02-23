package com.moonbox.beneficiaries.services;

import com.moonbox.beneficiaries.repositories.LanguageRepository;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }
}
