package com.myapp.backend.controller;

import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.model.Translator;
import com.myapp.backend.service.TranslatorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/translator")
@Log4j2
@AllArgsConstructor
public final class TranslatorController {
    private final TranslatorService translatorService;

    @GetMapping("/translate")
    public TranslatorDto translate(@RequestParam(value = "str", defaultValue = "") final String lineToTranslate,
                                   @RequestParam(value = "from", defaultValue = "ru") final String languageFrom,
                                   @RequestParam(value = "to", defaultValue = "en") final String languageTo) {
        return translatorService.translate(languageFrom, languageTo, lineToTranslate.toLowerCase());
    }

    @GetMapping("/{id}")
    public TranslatorDto getTranslatorById(@PathVariable final long id) {
        log.info("GET endpoint /translator/{id} was called.");
        return translatorService.convertEntityToDto(translatorService.getTranslatorById(id));
    }

    @GetMapping("/all")
    public List<TranslatorDto> getAllTranslators() {
        return translatorService.getAllTranslatorsDto();
    }

    @PostMapping
    public Translator createTranslator(@RequestBody final Translator translator) {
        log.info("POST endpoint /translator/{id} was called.");
        return translatorService.createTranslator(translator);
    }

    @PostMapping("/bulk")
    public void createHistoryTranslations(@RequestBody final List<Translator> translators) {
        translatorService.createTranslatorsBulk(translators);
    }

    @PutMapping("/{id}")
    public Translator updateTranslator(@PathVariable final long id, @RequestBody final Translator updatedTranslator) {
        log.info("PUT endpoint /translator/{id} was called.");
        return translatorService.updateTranslator(id, updatedTranslator);
    }

    @DeleteMapping("/{id}")
    public void deleteTranslator(@PathVariable final long id) {
        log.info("DELETE endpoint /translator/{%d} was called.");
        translatorService.deleteTranslatorById(id);
    }
}
