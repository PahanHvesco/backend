package com.myapp.backend.controller;

import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.service.MainService;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/main")
public class MainController {
    private final MainService mainService;
    MainController(MainService mainService) {
        this.mainService = mainService;
    }
    @GetMapping("/translate")
    public TranslatorDto translate(@RequestParam(value = "str", defaultValue = "") String lineToTranslate,
                                   @RequestParam(value = "from", defaultValue = "ru") String languageFrom,
                                   @RequestParam(value = "to", defaultValue = "en") String languageTo){
        return mainService.translate(languageFrom, languageTo, lineToTranslate.toLowerCase());
    }

    @PostMapping("/add/tags")
    public void addTagToTranslatorAndTranslatorToTag(@RequestParam(value = "id") long translatorId, @RequestParam(value = "name") String nameTag){
        mainService.addTranslatorToTag(translatorId, nameTag);
    }
}
