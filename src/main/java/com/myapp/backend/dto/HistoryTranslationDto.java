package com.myapp.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryTranslationDto {
    private long id;
    private Timestamp date;
    private String source;
    private String target;
}
