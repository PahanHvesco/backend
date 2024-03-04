package com.myapp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TranslatorDTO {
    private long id;
    private String line;
    private String translatedLine;

    public TranslatorDTO(String line, String translatedLine) {
        this.line = line;
        this.translatedLine = translatedLine;
    }
}
