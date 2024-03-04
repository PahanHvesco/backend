package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryLanguageDTO;
import com.myapp.backend.model.HistoryLanguage;
import org.springframework.stereotype.Component;

@Component
public class HistoryLanguageMapper {
    public HistoryLanguageDTO toDTO(HistoryLanguage historyLanguage) {
        HistoryLanguageDTO historyLanguageDTO = new HistoryLanguageDTO();
        historyLanguageDTO.setId(historyLanguage.getId());
        historyLanguageDTO.setTranslatorId(historyLanguage.getTranslator().getId());
        historyLanguageDTO.setSourceLanguage(historyLanguage.getSourceLanguage());
        historyLanguageDTO.setTargetLanguage(historyLanguage.getTargetLanguage());
        return historyLanguageDTO;
    }
}
