package com.myapp.backend.service;

import com.myapp.backend.component.TranslatorComponent;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.mapper.TranslatorMapper;
import com.myapp.backend.model.HistoryLanguage;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.model.Tag;
import com.myapp.backend.model.Translator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MainService {
    private final TranslatorService translatorService;
    private final HistoryLanguageService historyLanguageService;
    private final HistoryTranslationService historyTranslationService;
    private final HistoryService historyService;
    private final TagService tagService;
    private final TranslatorComponent translatorComponent;
    private final TranslatorMapper translatorMapper;

    MainService(TranslatorService translatorService, HistoryLanguageService historyLanguageService, HistoryTranslationService historyTranslationService, HistoryService historyService, TagService tagService, TranslatorComponent translatorComponent, TranslatorMapper translatorMapper) {
        this.translatorService = translatorService;
        this.historyLanguageService = historyLanguageService;
        this.historyTranslationService = historyTranslationService;
        this.historyService = historyService;
        this.tagService = tagService;
        this.translatorComponent = translatorComponent;
        this.translatorMapper = translatorMapper;
    }

    public TranslatorDto translate(String languageFrom, String languageTo, String lineToTranslate) {
        Translator translator;
        TranslatorDto translatorDto = translatorComponent.translate(languageFrom, languageTo, lineToTranslate);
        if(translatorComponent.searchByTable(languageFrom, lineToTranslate) == null) {
            translator = translatorService.createTranslator(translatorMapper.toEntity(translatorDto));
        } else {
            translator = translatorMapper.toEntity(translatorDto);
        }
        HistoryLanguage historyLanguage = historyLanguageService.createHistoryLanguage(translator, languageFrom, languageTo);
        HistoryTranslation historyTranslation = historyTranslationService.createHistoryTranslation(translator);
        historyService.createHistory(historyLanguage, historyTranslation);
        return translatorDto;
    }

    public void addTranslatorToTag(long id, String nameTag) {
        ArrayList<Tag> tags = new ArrayList<>(tagService.getAllTag());
        Translator translator = translatorService.getTranslatorById(id);
        for(Tag tag : tags) {
            if(tag.getNameTag().equals(nameTag)) {
                tag.getTranslators().add(translatorService.getTranslatorById(id));
                tagService.updateTag(tag.getId(), tag);
            }
        }
    }
}
