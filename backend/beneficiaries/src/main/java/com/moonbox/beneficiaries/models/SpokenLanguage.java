package com.moonbox.beneficiaries.models;

import com.moonbox.beneficiaries.entities.BaseEntity;
import com.moonbox.beneficiaries.entities.Language;
import com.moonbox.beneficiaries.enums.LanguageKnowledgeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpokenLanguage extends BaseEntity {

    private Language language;
    private LanguageKnowledgeEnum languageKnowledge;
}
