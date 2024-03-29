package com.myapp.backend.service;

import com.myapp.backend.dto.HistoryDto;
import com.myapp.backend.mapper.HistoryMapper;
import com.myapp.backend.model.HistoryLanguage;
import com.myapp.backend.model.HistoryTranslation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.myapp.backend.model.History;
import com.myapp.backend.repository.HistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    public HistoryService(HistoryRepository historyRepository, HistoryMapper historyMapper) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    @Cacheable("history")
    public History getHistoryById(long id) {
        return historyRepository.findById(id).orElse(null);
    }

    public History createHistory(History history) {
        return historyRepository.save(history);
    }

    public void deleteHistory(long id) {
        historyRepository.deleteById(id);
    }

    public History updateHistory(long id, History updatedHistory) {
        Optional<History> existingHistory = historyRepository.findById(id);
        if (existingHistory.isPresent()) {
            History historyToUpdate = existingHistory.get();
            historyToUpdate.setHistoryLanguage(updatedHistory.getHistoryLanguage());
            historyToUpdate.setHistoryTranslation(updatedHistory.getHistoryTranslation());
            return historyRepository.save(historyToUpdate);
        }
        return null;
    }
    public History createHistory(HistoryLanguage historyLanguage, HistoryTranslation historyTranslation) {
        History history = new History();
        history.setHistoryLanguage(historyLanguage);
        history.setHistoryTranslation(historyTranslation);
        return historyRepository.save(history);
    }

    public HistoryDto getDto(long id) {
        return historyMapper.toDTO(getHistoryById(id));
    }
}
