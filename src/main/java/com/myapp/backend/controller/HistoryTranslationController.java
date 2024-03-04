package com.myapp.backend.controller;

import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.model.Translator;
import com.myapp.backend.service.HistoryTranslationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history/translation")
public class HistoryTranslationController {
    private final HistoryTranslationService historyTranslationService;

    public HistoryTranslationController(HistoryTranslationService historyTranslationService) {
        this.historyTranslationService = historyTranslationService;
    }


    @GetMapping
    public List<HistoryTranslation> getAllHistoryTranslation() {
        return historyTranslationService.getAllHistoryTranslations();
    }
    @GetMapping("/{id}")
    public HistoryTranslation getHistoryTranslationById(@PathVariable long id) {
        return historyTranslationService.getHistoryTranslationById(id);
    }

    @PostMapping
    public HistoryTranslation createHistoryTranslation(@RequestBody HistoryTranslation historyTranslation) {
        return historyTranslationService.createHistoryTranslation(historyTranslation);
    }

    @PutMapping("/{id}")
    public HistoryTranslation updateHistoryTranslation(@PathVariable long id, @RequestBody HistoryTranslation historyTranslation) {
        return historyTranslationService.updateHistoryTranslation(id, historyTranslation);
    }

    @DeleteMapping("/{id}")
    public void deleteHistoryTranslation(@PathVariable long id) {
        historyTranslationService.deleteHistoryTranslationById(id);
    }
}
