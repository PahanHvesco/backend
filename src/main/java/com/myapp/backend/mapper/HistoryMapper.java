package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryDto;
import com.myapp.backend.model.History;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {
    public HistoryDto toDTO(History history) {
        return new HistoryDto(history.getId(), history.getHistoryLanguage().getId(), history.getHistoryTranslation().getId());
    }
}
