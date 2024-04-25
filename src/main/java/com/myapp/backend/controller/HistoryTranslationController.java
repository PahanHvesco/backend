package com.myapp.backend.controller;

import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.service.HistoryTranslationService;
import com.myapp.backend.service.RequestCounterService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/history/translation")
@Log4j2
@AllArgsConstructor
public final class HistoryTranslationController {
    private final HistoryTranslationService historyTranslationService;
    private final RequestCounterService requestCounterService;

    @GetMapping("/{id}")
    public HistoryTranslationDto getHistoryTranslationById(@PathVariable final long id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        //log.info("GET endpoint /history/translation/{id} was called.");
        return historyTranslationService.convertEntityToDto(historyTranslationService.getHistoryTranslationById(id));
    }

    @GetMapping("/all")
    public List<HistoryTranslationDto> getAllHistoryTranslations() {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        return historyTranslationService.getAllHistoryTranslationsDto();
    }

    @GetMapping("/{source}/{target}")
    public List<TranslatorDto> getTranslationsBySourceAndTarget(@PathVariable final String source,
                                                                @PathVariable final String target) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        //log.info("GET endpoint /history/translation/{source}/{target} was called.");
        return historyTranslationService.getTranslationsBySourceAndTarget(source, target);
    }

    @PostMapping
    public HistoryTranslation createHistoryTranslation(@RequestBody final HistoryTranslation historyTranslation) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        //log.info("POST endpoint /history/translation/{id} was called.");
        return historyTranslationService.createHistoryTranslation(historyTranslation);
    }

    @PostMapping("/bulk")
    public void createHistoryTranslations(@RequestBody final List<HistoryTranslation> historyTranslations) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        historyTranslationService.createHistoryTranslationsBulk(historyTranslations);
    }

    @PutMapping("/{id}")
    public HistoryTranslation updateHistoryTranslation(@PathVariable final long id,
                                                       @RequestBody final HistoryTranslation historyTranslation) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        //log.info("PUT endpoint /history/translation/{id} was called.");
        return historyTranslationService.updateHistoryTranslation(id, historyTranslation);
    }

    @DeleteMapping("/{id}")
    public void deleteHistoryTranslation(@PathVariable final long id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        //log.info("DELETE endpoint /history/translation/{id} was called.");
        historyTranslationService.deleteHistoryTranslationById(id);
    }
}
