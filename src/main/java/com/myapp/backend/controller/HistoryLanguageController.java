package com.myapp.backend.controller;

import com.myapp.backend.model.HistoryLanguage;
import com.myapp.backend.service.HistoryLanguageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history/language")
public class HistoryLanguageController {
    private final HistoryLanguageService historyLanguageService;
    public HistoryLanguageController(HistoryLanguageService historyLanguageService) {
        this.historyLanguageService = historyLanguageService;
    }

    @GetMapping
    public List<HistoryLanguage> getAllHistoryLanguage() {
        return historyLanguageService.getAllHistoryLanguages();
    }

    @GetMapping("/{id}")
    public HistoryLanguage getHistoryLanguageById(@PathVariable long id) {
        return historyLanguageService.getHistoryLanguageById(id);
    }

    @PostMapping
    public HistoryLanguage createHistoryTranslation(@RequestBody HistoryLanguage historyLanguage) {
        return historyLanguageService.createHistoryLanguage(historyLanguage);
    }

    @PutMapping("/{id}")
    public HistoryLanguage updateHistoryTranslation(@PathVariable long id, @RequestBody HistoryLanguage historyLanguage) {
        return historyLanguageService.updateHistoryLanguage(id, historyLanguage);
    }

    @DeleteMapping("/{id}")
    public void deleteHistoryTranslation(@PathVariable long id) {
        historyLanguageService.deleteHistoryLanguage(id);
    }
}
