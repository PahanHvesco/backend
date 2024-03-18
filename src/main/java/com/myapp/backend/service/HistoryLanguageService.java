package com.myapp.backend.service;

import com.myapp.backend.dto.HistoryLanguageDto;
import com.myapp.backend.mapper.HistoryLanguageMapper;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.HistoryLanguageRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.myapp.backend.model.HistoryLanguage;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryLanguageService {
    private final HistoryLanguageRepository historyLanguageRepository;
    private final HistoryLanguageMapper historyLanguageMapper;

    public HistoryLanguageService(HistoryLanguageRepository historyLanguageRepository, HistoryLanguageMapper historyLanguageMapper) {
        this.historyLanguageRepository = historyLanguageRepository;
        this.historyLanguageMapper = historyLanguageMapper;
    }

    public List<HistoryLanguage> getAllHistoryLanguages() {
        return historyLanguageRepository.findAll();
    }

    @Cacheable("history_language")
    public HistoryLanguage getHistoryLanguageById(long id) {
        return historyLanguageRepository.findById(id).orElse(null);
    }

    public HistoryLanguage createHistoryLanguage(HistoryLanguage historyLanguage) {
        return historyLanguageRepository.save(historyLanguage);
    }

    public void deleteHistoryLanguage(long id) {
        historyLanguageRepository.deleteById(id);
    }

    public HistoryLanguage updateHistoryLanguage(Long id, HistoryLanguage updatedLanguage) {
        Optional<HistoryLanguage> existingLanguage = historyLanguageRepository.findById(id);
        if (existingLanguage.isPresent()) {
            HistoryLanguage languageToUpdate = existingLanguage.get();
            languageToUpdate.setSourceLanguage(updatedLanguage.getSourceLanguage());
            languageToUpdate.setTargetLanguage(updatedLanguage.getTargetLanguage());
            return historyLanguageRepository.save(languageToUpdate);
        }
        return null;
    }
    public HistoryLanguage createHistoryLanguage(Translator translator, String sourceLanguage, String targetLanguage) {
        HistoryLanguage historyLanguage = new HistoryLanguage();
        historyLanguage.setTranslator(translator);
        historyLanguage.setSourceLanguage(sourceLanguage);
        historyLanguage.setTargetLanguage(targetLanguage);
        return historyLanguageRepository.save(historyLanguage);
    }

    public HistoryLanguageDto getDto(long id) {
        return historyLanguageMapper.toDTO(getHistoryLanguageById(id));
    }
}
