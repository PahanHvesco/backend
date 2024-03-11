package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryLanguageDto;
import com.myapp.backend.model.HistoryLanguage;
import org.springframework.stereotype.Component;

@Component
public class HistoryLanguageMapper {
    public HistoryLanguageDto toDTO(HistoryLanguage historyLanguage) {
        return new HistoryLanguageDto(historyLanguage.getId(), historyLanguage.getTranslator().getId(), historyLanguage.getSourceLanguage(), historyLanguage.getTargetLanguage());
    }
}
