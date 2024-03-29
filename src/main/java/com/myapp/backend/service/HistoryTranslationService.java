package com.myapp.backend.service;

import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.mapper.HistoryTranslationMapper;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.HistoryTranslationRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryTranslationService {
    private final HistoryTranslationRepository historyTranslationRepository;
    private final HistoryTranslationMapper historyTranslationMapper;
    public HistoryTranslationService(HistoryTranslationRepository historyTranslationRepository, HistoryTranslationMapper historyTranslationMapper) {
        this.historyTranslationRepository = historyTranslationRepository;
        this.historyTranslationMapper = historyTranslationMapper;
    }

    public HistoryTranslation createHistoryTranslation(HistoryTranslation historyTranslation) {
        return historyTranslationRepository.save(historyTranslation);
    }

    @Cacheable("history_translation")
    public HistoryTranslation getHistoryTranslationById(Long id) {
        return historyTranslationRepository.findById(id).orElse(null);
    }

    public List<HistoryTranslation> getAllHistoryTranslations() {
        return historyTranslationRepository.findAll();
    }

    public void deleteHistoryTranslationById(Long id) {
        historyTranslationRepository.deleteById(id);
    }

    public HistoryTranslation updateHistoryTranslation(Long id, HistoryTranslation updatedTranslation) {
        Optional<HistoryTranslation> existingTranslation = historyTranslationRepository.findById(id);
        if (existingTranslation.isPresent()) {
            HistoryTranslation translationToUpdate = existingTranslation.get();
            translationToUpdate.setDate(updatedTranslation.getDate());
            return historyTranslationRepository.save(translationToUpdate);
        }
        return null;
    }

    public HistoryTranslation createHistoryTranslation(Translator translator) {
        HistoryTranslation historyTranslation = new HistoryTranslation();
        historyTranslation.setTranslator(translator);
        historyTranslation.setDate(Timestamp.valueOf(LocalDateTime.now()));
        return historyTranslationRepository.save(historyTranslation);
    }

    public HistoryTranslationDto getDto(long id) {
        return historyTranslationMapper.toDTO(getHistoryTranslationById(id));
    }
}
