package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryTranslationDTO;
import com.myapp.backend.model.HistoryTranslation;
import org.springframework.stereotype.Component;

@Component
public class HistoryTranslationMapper {
    public HistoryTranslationDTO toDTO(HistoryTranslation historyTranslation) {
        HistoryTranslationDTO historyTranslationDTO = new HistoryTranslationDTO();
        historyTranslationDTO.setId(historyTranslation.getId());
        historyTranslationDTO.setTranslatorId(historyTranslation.getTranslator().getId());
        historyTranslationDTO.setData(historyTranslation.getDate());
        return historyTranslationDTO;
    }
}
