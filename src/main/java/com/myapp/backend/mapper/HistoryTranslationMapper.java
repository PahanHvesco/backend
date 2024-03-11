package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.model.HistoryTranslation;
import org.springframework.stereotype.Component;

@Component
public class HistoryTranslationMapper {
    public HistoryTranslationDto toDTO(HistoryTranslation historyTranslation) {
        return new HistoryTranslationDto(historyTranslation.getId(), historyTranslation.getTranslator().getId(), historyTranslation.getDate());
    }
}
