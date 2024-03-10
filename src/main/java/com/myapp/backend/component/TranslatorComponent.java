package com.myapp.backend.component;

import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.mapper.TranslatorMapper;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.TranslatorRepository;
import org.apache.tika.language.LanguageIdentifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TranslatorComponent {
    private final TranslatorRepository translatorRepository;
    TranslatorComponent(TranslatorRepository translatorRepository) {
        this.translatorRepository = translatorRepository;
    }
    public TranslatorDto translate(String languageFrom, String languageTo, String lineToTranslate){
        languageFrom = checkLanguage(languageFrom, lineToTranslate);
        if(languageFrom == null) {
            return new TranslatorDto(-1, null, null);
        } else if(languageFrom.equals(languageTo)) {
            return new TranslatorDto(-1, lineToTranslate, lineToTranslate);
        }

        TranslatorDto translatorDTO = searchByTable(languageFrom, lineToTranslate);
        if(translatorDTO==null) {
            String lineTranslate = net.suuft.libretranslate.Translator.translate(languageFrom, languageTo, lineToTranslate);
            translatorDTO = new TranslatorDto(lineToTranslate, lineTranslate);

            TranslatorMapper translatorMapper = new TranslatorMapper();
        }
        return translatorDTO;
    }

    public TranslatorDto searchByTable(String languageFrom, String lineToTranslate) {
        for(Translator translator : translatorRepository.findAll()) {
            if(languageFrom.equals("ru") && translator.getRu().equals(lineToTranslate)){
                return new TranslatorDto(translator.getId(), translator.getRu(), translator.getEn());
            } else if(languageFrom.equals("en") && translator.getEn().equals(lineToTranslate)) {
                return new TranslatorDto(translator.getId(), translator.getEn(), translator.getRu());
            }
        }
        return null;
    }
    private String checkLanguage(String languageFrom, String lineToTranslate) {
        LanguageIdentifier languageIdentifier = new LanguageIdentifier(lineToTranslate);

        Map<String, String[]> languageMappings = new HashMap<>();
        languageMappings.put("ru", new String[]{"ru", "uk", "be"});
        languageMappings.put("en", new String[]{"en", "sk", "et", "fr", "it", "ro", "pl", "lt", "eo", "hu", "sl"});

        String detectedLanguage = languageIdentifier.getLanguage();
        String[] preferredLanguages = languageMappings.get(languageFrom);
        if (preferredLanguages != null) {
            for (String preferredLanguage : preferredLanguages) {
                if (preferredLanguage.equals(detectedLanguage)) {
                    return languageFrom;
                }
            }
        }
        return null;
    }
}
