package com.myapp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class HistoryLanguageDto {
    private long id;
    private long translatorId;
    private String sourceLanguage;
    private String targetLanguage;
}
