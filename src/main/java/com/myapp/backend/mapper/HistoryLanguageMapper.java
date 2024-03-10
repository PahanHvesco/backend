package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryLanguageDto;
import com.myapp.backend.model.HistoryLanguage;
import org.springframework.stereotype.Component;

@Component
public class HistoryLanguageMapper {
    public HistoryLanguageDto toDTO(HistoryLanguage historyLanguage) {
        HistoryLanguageDto historyLanguageDTO = new HistoryLanguageDto();
        historyLanguageDTO.setId(historyLanguage.getId());
        historyLanguageDTO.setTranslatorId(historyLanguage.getTranslator().getId());
        historyLanguageDTO.setSourceLanguage(historyLanguage.getSourceLanguage());
        historyLanguageDTO.setTargetLanguage(historyLanguage.getTargetLanguage());
        return historyLanguageDTO;
    }
}
