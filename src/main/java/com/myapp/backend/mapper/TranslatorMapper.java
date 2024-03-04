package com.myapp.backend.mapper;

import com.myapp.backend.dto.TranslatorDTO;
import com.myapp.backend.model.Translator;
import org.apache.tika.language.LanguageIdentifier;
import org.springframework.stereotype.Component;

@Component
public class TranslatorMapper {
    public Translator toEntity(TranslatorDTO dto) {
        Translator translator = new Translator();
        translator.setId(dto.getId());
        LanguageIdentifier languageIdentifier = new LanguageIdentifier(dto.getLine());
        if(languageIdentifier.getLanguage().equals("ru") ||
                languageIdentifier.getLanguage().equals("uk") ||
                languageIdentifier.getLanguage().equals("be")) {
            translator.setEn(dto.getTranslatedLine());
            translator.setRu(dto.getLine());
        } else {
            translator.setRu(dto.getTranslatedLine());
            translator.setEn(dto.getLine());
        }

        return translator;
    }
}
