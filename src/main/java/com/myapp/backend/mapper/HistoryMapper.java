package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryDTO;
import com.myapp.backend.model.History;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {
    public HistoryDTO toDTO(History history) {
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setId(history.getId());
        historyDTO.setHistoryLanguageId(history.getHistoryLanguage().getId());
        historyDTO.setHistoryTranslationId(history.getHistoryTranslation().getId());
        return historyDTO;
    }
}
