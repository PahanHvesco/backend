package com.myapp.backend.controller;

import com.myapp.backend.dto.TranslatorDTO;
import com.myapp.backend.service.TranslatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslatorController {
    TranslatorService translatorService;

    public TranslatorController(TranslatorService translatorService){
        this.translatorService = translatorService;
    }
    @GetMapping("/translate")
    public TranslatorDTO translate(@RequestParam(value = "word", defaultValue = "") String word,
                                   @RequestParam(value = "reverse", defaultValue = "false") boolean reverse){
        return translatorService.getTranslatorDTO(reverse, word.toLowerCase());
    }
}