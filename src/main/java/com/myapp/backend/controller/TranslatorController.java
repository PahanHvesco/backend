package com.myapp.backend.controller;

import com.myapp.backend.dto.TranslatorDTO;
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
    @GetMapping("/translate")
    public TranslatorDTO translate(@RequestParam(value = "str", defaultValue = "") String lineToTranslate,
                                   @RequestParam(value = "from", defaultValue = "ru") String languageFrom,
                                   @RequestParam(value = "to", defaultValue = "en") String languageTo){
        return translatorService.translate(languageFrom, languageTo, lineToTranslate.toLowerCase());
    }

    @GetMapping
    public List<Translator> getAllTranslators() {
        return translatorService.getAllTranslators();
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
