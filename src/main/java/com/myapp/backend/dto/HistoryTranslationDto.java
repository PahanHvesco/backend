package com.myapp.backend.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class HistoryTranslationDto {
    private long id;
    private long translatorId;
    private Timestamp data;
}
