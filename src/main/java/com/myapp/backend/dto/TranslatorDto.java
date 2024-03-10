package com.myapp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TranslatorDto {
    private long id;
    private String line;
    private String translatedLine;

    public TranslatorDto(String line, String translatedLine) {
        this.line = line;
        this.translatedLine = translatedLine;
    }
}
