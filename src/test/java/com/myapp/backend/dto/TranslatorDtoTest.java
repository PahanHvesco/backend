package com.myapp.backend.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TranslatorDtoTests {

    @Test
    public void testTranslatorDto() {
        long id = 1L;
        String en = "English";
        String ru = "Russian";

        TranslatorDto dto = new TranslatorDto(id, en, ru);

        assertEquals(id, dto.getId());
        assertEquals(en, dto.getEn());
        assertEquals(ru, dto.getRu());
    }
}
