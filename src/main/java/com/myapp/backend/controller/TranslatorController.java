package com.myapp.backend.controller;

import com.myapp.backend.model.HistoryLanguage;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.model.Translator;
import com.myapp.backend.service.TranslatorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/translator")
public class TranslatorController {
    private final TranslatorService translatorService;

    public TranslatorController(TranslatorService translatorService){
        this.translatorService = translatorService;
    }

    @GetMapping
    public List<Translator> getAllTranslators() {
        return translatorService.getAllTranslators();
    }

    @GetMapping("/history/language/{id}")
    public List<HistoryLanguage> getAllHistoryLanguageById(@PathVariable long id) {
        return translatorService.getAllHistoryLanguageById(id);
    }

    @GetMapping("/history/translation/{id}")
    public List<HistoryTranslation> getAllHistoryTranslationById(@PathVariable long id) {
        return translatorService.getAllHistoryTranslationById(id);
    }

    @GetMapping("/{id}")
    public Translator getTranslatorById(@PathVariable long id) {
        return translatorService.getTranslatorById(id);
    }

    @PostMapping
    public Translator createTranslator(@RequestBody Translator translator) {
        return translatorService.createTranslator(translator);
    }

    @PutMapping("/{id}")
    public Translator updateTranslator(@PathVariable long id, @RequestBody Translator updatedTranslator) {
        return translatorService.updateTranslator(id, updatedTranslator);
    }

    @DeleteMapping("/{id}")
    public void deleteTranslator(@PathVariable long id) {
        translatorService.deleteTranslatorById(id);
    }
}
