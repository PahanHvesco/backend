package com.myapp.backend.service;

import com.myapp.backend.dto.TranslatorDTO;
import com.myapp.backend.mapper.TranslatorMapper;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.TranslatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslatorService {
    private final TranslatorRepository translatorRepository;
    public TranslatorService(TranslatorRepository translatorRepository){
        this.translatorRepository = translatorRepository;
    }

    public TranslatorDTO getTranslatorDTO(boolean reverse, String word){
        TranslatorMapper translatorMapper = new TranslatorMapper();
        return translatorMapper.toDto(translate(reverse, word));
    }
    public Translator translate(boolean reverse, String word) {
        Translator translator = searchByTable(reverse, word);
        if(translator!=null) {
            return translator;
        } else {
            if(reverse) {
                String wordTranslate = net.suuft.libretranslate.Translator.translate("ru", "en", word);
                translator = new Translator(translatorRepository.count()+1, wordTranslate, word);
            } else {
                String wordTranslate = net.suuft.libretranslate.Translator.translate("en", "ru", word);
                translator = new Translator(translatorRepository.count()+1, word, wordTranslate);
            }
            if(!translator.getEn().equals(translator.getRu())){
                translatorRepository.save(translator);
            }

            return translator;
        }
    }

    public Translator searchByTable(boolean reverse, String word) {
        List<Translator> translators = translatorRepository.findAll();

        for(Translator translator : translators) {
            if(reverse && translator.getRu().equals(word)){
                return new Translator(translator.getId(), translator.getEn(), translator.getRu());
            } else if(!reverse && translator.getEn().equals(word)) {
                return translator;
            }
        }
        return null;
    }
}
