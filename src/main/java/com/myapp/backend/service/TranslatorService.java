package com.myapp.backend.service;

import com.myapp.backend.dto.TranslatorDTO;
import com.myapp.backend.mapper.TranslatorMapper;
import com.myapp.backend.model.History;
import com.myapp.backend.model.HistoryLanguage;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.TranslatorRepository;
import org.apache.tika.language.LanguageIdentifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TranslatorService {
    private final TranslatorRepository translatorRepository;
    private final HistoryTranslationService historyTranslationService;
    private final HistoryLanguageService historyLanguageService;
    private final HistoryService historyService;
    public TranslatorService(TranslatorRepository translatorRepository, HistoryTranslationService historyTranslationService, HistoryLanguageService historyLanguageService, HistoryService historyService){
        this.translatorRepository = translatorRepository;
        this.historyTranslationService = historyTranslationService;
        this.historyLanguageService = historyLanguageService;
        this.historyService = historyService;
    }

    public TranslatorDTO translate(String languageFrom, String languageTo, String lineToTranslate){
        Translator translator;

        languageFrom = checkLanguage(languageFrom, lineToTranslate);
        if(languageFrom == null) {
            return new TranslatorDTO(-1, null, null);
        } else if(languageFrom.equals(languageTo)) {
            return new TranslatorDTO(-1, lineToTranslate, lineToTranslate);
        }

        TranslatorDTO translatorDTO = searchByTable(languageFrom, lineToTranslate);
        if(translatorDTO==null) {
            String lineTranslate = net.suuft.libretranslate.Translator.translate(languageFrom, languageTo, lineToTranslate);
            translatorDTO = new TranslatorDTO(lineToTranslate, lineTranslate);

            TranslatorMapper translatorMapper = new TranslatorMapper();
            translator = translatorMapper.toEntity(translatorDTO);
            translatorRepository.save(translator);
            translatorDTO.setId(translator.getId());
        }

        TranslatorMapper translatorMapper = new TranslatorMapper();
        HistoryLanguage historyLanguage =  historyLanguageService.createHistoryLanguage(translatorMapper.toEntity(translatorDTO), languageFrom, languageTo);
        HistoryTranslation historyTranslation =  historyTranslationService.createHistoryTranslation(translatorMapper.toEntity(translatorDTO));
        History history = historyService.createHistory(historyLanguage, historyTranslation);
        return translatorDTO;
    }

    public TranslatorDTO searchByTable(String languageFrom, String lineToTranslate) {
        for(Translator translator : translatorRepository.findAll()) {
            if(languageFrom.equals("ru") && translator.getRu().equals(lineToTranslate)){
                return new TranslatorDTO(translator.getId(), translator.getRu(), translator.getEn());
            } else if(languageFrom.equals("en") && translator.getEn().equals(lineToTranslate)) {
                return new TranslatorDTO(translator.getId(), translator.getEn(), translator.getRu());
            }
        }
        return null;
    }

    public String checkLanguage(String languageFrom, String lineToTranslate) {
        LanguageIdentifier languageIdentifier = new LanguageIdentifier(lineToTranslate);
        if(languageIdentifier.getLanguage().equals("ru") ||
                languageIdentifier.getLanguage().equals("uk") ||
                languageIdentifier.getLanguage().equals("be")) {
            return languageFrom.equals("ru")?"ru":null;
        } else if(languageIdentifier.getLanguage().equals("en") ||
                languageIdentifier.getLanguage().equals("sk") ||
                languageIdentifier.getLanguage().equals("et") ||
                languageIdentifier.getLanguage().equals("fr") ||
                languageIdentifier.getLanguage().equals("it") ||
                languageIdentifier.getLanguage().equals("lt") ||
                languageIdentifier.getLanguage().equals("ro") ||
                languageIdentifier.getLanguage().equals("pl") ||
                languageIdentifier.getLanguage().equals("lt") ||
                languageIdentifier.getLanguage().equals("eo") ||
                languageIdentifier.getLanguage().equals("hu") ||
                languageIdentifier.getLanguage().equals("sl")) {
            return languageFrom.equals("en")?"en":null;
        }
        return null;
    }

    @Transactional
    public Translator createTranslator(Translator translator) {
        return translatorRepository.save(translator);
    }

    public List<Translator> getAllTranslators() {
        return translatorRepository.findAll();
    }

    public Translator getTranslatorById(long id) {
        return translatorRepository.findById(id).orElse(null);
    }

    public Translator updateTranslator(long id, Translator updatedTranslator) {
        Optional<Translator> existingTranslator = translatorRepository.findById(id);
        if (existingTranslator.isPresent()) {
            Translator translatorToUpdate = existingTranslator.get();
            translatorToUpdate.setEn(updatedTranslator.getEn());
            translatorToUpdate.setRu(updatedTranslator.getRu());
            return translatorRepository.save(translatorToUpdate);
        }
        return null;
    }

    public void deleteTranslatorById(long id) {
        translatorRepository.deleteById(id);
    }
}
