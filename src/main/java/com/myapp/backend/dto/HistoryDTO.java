package com.myapp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class HistoryDTO {
    private long id;
    private long historyLanguageId;
    private long historyTranslationId;
}
