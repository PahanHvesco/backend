package com.myapp.backend.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.model.Translator;
import org.junit.jupiter.api.Test;

class TranslatorMapperTests {

    @Test
    public void testEntityToDto() {
        TranslatorMapper mapper = new TranslatorMapper();

        long id = 1L;
        String en = "English";
        String ru = "Russian";

        Translator entity = new Translator(id, en, ru, null, null);

        TranslatorDto dto = mapper.entityToDto(entity);

        assertEquals(id, dto.getId());
        assertEquals(en, dto.getEn());
        assertEquals(ru, dto.getRu());
    }

    @Test
    public void testDtoToEntity() {
        TranslatorMapper mapper = new TranslatorMapper();

        long id = 1L;
        String en = "English";
        String ru = "Russian";

        TranslatorDto dto = new TranslatorDto(id, en, ru);

        Translator entity = mapper.dtoToEntity(dto);

        assertEquals(id, entity.getId());
        assertEquals(en, entity.getEn());
        assertEquals(ru, entity.getRu());
    }
}
