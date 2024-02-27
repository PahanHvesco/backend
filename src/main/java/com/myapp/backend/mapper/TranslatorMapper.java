package com.myapp.backend.mapper;

import com.myapp.backend.dto.TranslatorDTO;
import com.myapp.backend.model.Translator;

public class TranslatorMapper {
    public TranslatorDTO toDto(Translator translator) {
        TranslatorDTO dto = new TranslatorDTO();
        dto.setId(translator.getId());
        dto.setEn(translator.getEn());
        dto.setRu(translator.getRu());
        return dto;
    }

    public Translator toEntity(TranslatorDTO dto) {
        Translator translator = new Translator();
        translator.setId(dto.getId());
        translator.setEn(dto.getEn());
        translator.setRu(dto.getRu());
        return translator;
    }
}
