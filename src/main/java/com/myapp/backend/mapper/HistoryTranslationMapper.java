package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.model.HistoryTranslation;
import org.springframework.stereotype.Component;

@Component
public class HistoryTranslationMapper {
    public HistoryTranslationDto toDTO(HistoryTranslation historyTranslation) {
        HistoryTranslationDto historyTranslationDTO = new HistoryTranslationDto();
        historyTranslationDTO.setId(historyTranslation.getId());
        historyTranslationDTO.setTranslatorId(historyTranslation.getTranslator().getId());
        historyTranslationDTO.setData(historyTranslation.getDate());
        return historyTranslationDTO;
    }
}
