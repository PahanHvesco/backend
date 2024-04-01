package com.myapp.backend.service;

import com.myapp.backend.component.SimpleCacheComponent;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.exception.InvalidDataException;
import com.myapp.backend.exception.ResourceNotFoundException;
import com.myapp.backend.mapper.TranslatorMapper;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.TranslatorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public final class TranslatorService {
    private final TranslatorRepository translatorRepository;
    private final SimpleCacheComponent<Translator> simpleCacheComponent;
    private final TranslatorMapper translatorMapper;
    private final HistoryTranslationService historyTranslationService;
     private static final String NOT_FOUND = "Not found Translator by id: ";

    public TranslatorDto translate(final String languageFrom,
                                   final String languageTo,
                                   final String lineToTranslate) {
        if (languageFrom.equals(languageTo)) {
            throw new InvalidDataException("LanguageTo and LanguageFrom can't be the same");
        }
        TranslatorDto translatorDTO = searchByTable(lineToTranslate);
        Translator translator;
        if (translatorDTO == null) {
            String lineTranslate = net.suuft.libretranslate.Translator.translate(languageFrom, languageTo, lineToTranslate);
            if (languageFrom.equals("ru") && languageTo.equals("en")) {
                translatorDTO = new TranslatorDto(0, lineTranslate, lineToTranslate);
                historyTranslationService.createHistoryTranslation(createTranslator(translatorMapper.dtoToEntity(translatorDTO)), languageTo, languageFrom);
            } else if (languageFrom.equals("en") && languageTo.equals("ru")) {
                translatorDTO = new TranslatorDto(0, lineToTranslate, lineTranslate);
                historyTranslationService.createHistoryTranslation(createTranslator(translatorMapper.dtoToEntity(translatorDTO)), languageTo, languageFrom);
            }
        }
        return translatorDTO;
    }

    public TranslatorDto searchByTable(final String lineToTranslate) {
        for (Translator translator : translatorRepository.findAll()) {
            if (translator.getRu().equals(lineToTranslate)
                    || translator.getEn().equals(lineToTranslate)) {
                return new TranslatorDto(translator.getId(), translator.getEn(), translator.getRu());
            }
        }
        return null;
    }

    public Translator createTranslator(final Translator translator) {
        log.info("POST endpoint success.");
        return translatorRepository.save(translator);
    }

    public List<Translator> getAllTranslators() {
        return translatorRepository.findAll();
    }

    public List<TranslatorDto> getAllTranslatorsDto() {
        List<TranslatorDto> translatorsDto = new ArrayList<>();
        for (Translator translator : translatorRepository.findAll()) {
            translatorsDto.add(translatorMapper.entityToDto(translator));
        }
        return translatorsDto;
    }


    public Translator getTranslatorById(final long id) {
        Translator translator = simpleCacheComponent.get(id);
        if (translator == null) {
            if (!translatorRepository.existsById(id)) {
                throw new ResourceNotFoundException(NOT_FOUND + id);
            }
            translator = translatorRepository.findById(id).orElse(null);
            simpleCacheComponent.put(id, translator);
            log.info("GET endpoint from BD.");
        } else {
            log.info("GET endpoint from CACHE.");
        }
        log.info("GET endpoint success.");
        return translator;
    }

    public Translator updateTranslator(final long id, final Translator updatedTranslator) {
        if (!translatorRepository.existsById(id)) {
            throw new ResourceNotFoundException(NOT_FOUND + id);
        }
        Optional<Translator> existingTranslator = translatorRepository.findById(id);
        if (existingTranslator.isPresent()) {
            Translator translatorToUpdate = existingTranslator.get();
            translatorToUpdate.setEn(updatedTranslator.getEn());
            translatorToUpdate.setRu(updatedTranslator.getRu());
            translatorToUpdate.setHistoryTranslations(updatedTranslator.getHistoryTranslations());
            translatorToUpdate.setTags(updatedTranslator.getTags());
            return translatorRepository.save(translatorToUpdate);
        }
        log.info("PUT endpoint success.");
        return null;
    }

    public void deleteTranslatorById(final long id) {
        if (!translatorRepository.existsById(id)) {
            throw new ResourceNotFoundException(NOT_FOUND + id);
        }
        simpleCacheComponent.remove(id);
        translatorRepository.deleteById(id);
        log.info("DELETE endpoint success.");
    }

    public TranslatorDto convertEntityToDto(final Translator translator) {
        return translatorMapper.entityToDto(translator);
    }

    public Translator convertDtoToEntity(final TranslatorDto translatorDto) {
        return translatorMapper.dtoToEntity(translatorDto);
    }
}
