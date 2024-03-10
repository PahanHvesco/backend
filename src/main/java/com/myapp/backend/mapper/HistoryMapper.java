package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryDto;
import com.myapp.backend.model.History;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {
    public HistoryDto toDTO(History history) {
        HistoryDto historyDTO = new HistoryDto();
        historyDTO.setId(history.getId());
        historyDTO.setHistoryLanguageId(history.getHistoryLanguage().getId());
        historyDTO.setHistoryTranslationId(history.getHistoryTranslation().getId());
        return historyDTO;
    }
}
