package com.myapp.backend.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TranslatorDtoTests {

    @Test
    void testGetterAndSetter() {
        TranslatorDto translatorDto = new TranslatorDto();

        translatorDto.setId(1);
        translatorDto.setEn("Hello");
        translatorDto.setRu("Привет");

        assertEquals(1, translatorDto.getId());
        assertEquals("Hello", translatorDto.getEn());
        assertEquals("Привет", translatorDto.getRu());
    }

    @Test
    void testAllArgsConstructor() {
        TranslatorDto translatorDto = new TranslatorDto(1, "Hello", "Привет");

        assertEquals(1, translatorDto.getId());
        assertEquals("Hello", translatorDto.getEn());
        assertEquals("Привет", translatorDto.getRu());
    }

    @Test
    void testNoArgsConstructor() {
        TranslatorDto translatorDto = new TranslatorDto();

        assertEquals(0, translatorDto.getId());
        assertEquals(null, translatorDto.getEn());
        assertEquals(null, translatorDto.getRu());
    }
}
