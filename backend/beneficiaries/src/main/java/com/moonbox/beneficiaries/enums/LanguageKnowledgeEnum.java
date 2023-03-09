package com.moonbox.beneficiaries.enums;

public enum LanguageKnowledgeEnum {

    MOTHER_TONGUE("MOTHER_TONGUE"),
    ADVANCED("ADVANCED"),
    GOOD("GOOD"),
    BASIC("BASIC"),
    TRANSLATOR_NEEDED("TRANSLATOR_NEEDED");

    private final String languageKnowledge;

    LanguageKnowledgeEnum(String languageKnowledge) {
        this.languageKnowledge = languageKnowledge;
    }

    public String getLanguageKnowledge() {
        return languageKnowledge;
    }
}
