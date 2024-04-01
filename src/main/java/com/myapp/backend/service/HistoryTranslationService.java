package com.myapp.backend.service;

import com.myapp.backend.component.SimpleCacheComponent;
import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.exception.InvalidDataException;
import com.myapp.backend.exception.ResourceNotFoundException;
import com.myapp.backend.mapper.HistoryTranslationMapper;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.HistoryTranslationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public final class HistoryTranslationService {
    private final HistoryTranslationRepository historyTranslationRepository;
    private final HistoryTranslationMapper historyTranslationMapper;
    private final SimpleCacheComponent<HistoryTranslation> simpleCacheComponent;
    private final String notFound = "Not found History Translation by id: ";

    public HistoryTranslation createHistoryTranslation(final HistoryTranslation historyTranslation) {
        log.info("POST endpoint success.");
        return historyTranslationRepository.save(historyTranslation);
    }

    public HistoryTranslation getHistoryTranslationById(final Long id) {
        HistoryTranslation historyTranslation = simpleCacheComponent.get(id);
        if (historyTranslation == null) {
            if (!historyTranslationRepository.existsById(id)) {
                throw new ResourceNotFoundException(notFound + id);
            }
            historyTranslation = historyTranslationRepository
                    .findById(id).orElse(null);
            simpleCacheComponent.put(id, historyTranslation);
            log.info("GET endpoint from BD.");
        } else {
            log.info("GET endpoint from CACHE.");
        }
        log.info("GET endpoint success.");
        return historyTranslation;
    }

    public List<TranslatorDto> getTranslationsBySourceAndTarget(final String source, final String target) {
        return historyTranslationRepository.getTranslationsBySourceAndTarget(source, target);
    }

    public List<HistoryTranslation> getAllHistoryTranslations() {
        return historyTranslationRepository.findAll();
    }

    public List<HistoryTranslationDto> getAllHistoryTranslationsDto() {
        List<HistoryTranslationDto> historyTranslationsDto = new ArrayList<>();
        for (HistoryTranslation historyTranslation : historyTranslationRepository.findAll()) {
            historyTranslationsDto.add(historyTranslationMapper.entityToDto(historyTranslation));
        }
        return historyTranslationsDto;
    }

    public void deleteHistoryTranslationById(final Long id) {
        if (!historyTranslationRepository.existsById(id)) {
            throw new ResourceNotFoundException(notFound + id);
        }
        simpleCacheComponent.remove(id);
        historyTranslationRepository.deleteById(id);
        log.info("DELETE endpoint success.");
    }

    public HistoryTranslation updateHistoryTranslation(final long id, final HistoryTranslation updatedTranslation) {
        if (!historyTranslationRepository.existsById(id)) {
            throw new ResourceNotFoundException(notFound + id);
        }
        Optional<HistoryTranslation> existingTranslation = historyTranslationRepository.findById(id);
        if (existingTranslation.isPresent()) {
            HistoryTranslation translationToUpdate = existingTranslation.get();
            translationToUpdate.setDate(updatedTranslation.getDate());
            translationToUpdate.setSource(updatedTranslation.getSource());
            translationToUpdate.setTarget(updatedTranslation.getTarget());
            translationToUpdate.setTranslator(updatedTranslation.getTranslator());
            return historyTranslationRepository.save(translationToUpdate);
        }
        log.info("PUT endpoint success.");
        return null;
    }

    public void createHistoryTranslation(final Translator translator,
                                         final String target,
                                         final String source) {
        HistoryTranslation historyTranslation = new HistoryTranslation();
        historyTranslation.setTranslator(translator);
        historyTranslation.setDate(Timestamp.valueOf(LocalDateTime.now()));
        historyTranslation.setTarget(target);
        historyTranslation.setSource(source);
        historyTranslationRepository.save(historyTranslation);
    }

    public void createHistoryTranslationsBulk(final List<HistoryTranslation> historyTranslations) {
        if (historyTranslations.isEmpty()) {
            throw new InvalidDataException("Data is empty: List empty");
        }
        historyTranslations.stream()
                .map(historyTranslationRepository::save)
                .forEach(savedHistoryTranslations -> log.info("History translation saved ID{}", savedHistoryTranslations.getId()));
    }

    public HistoryTranslationDto convertEntityToDto(final HistoryTranslation historyTranslation) {
        return historyTranslationMapper.entityToDto(historyTranslation);
    }

    public HistoryTranslation convertDtoToEntity(final HistoryTranslationDto historyTranslationDto) {
        return historyTranslationMapper.dtoToEntity(historyTranslationDto);
    }
}
