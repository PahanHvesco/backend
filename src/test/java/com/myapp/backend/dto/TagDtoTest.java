package com.myapp.backend.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class TagDtoTests {
    @Test
    void testGetterAndSetter() {
        TagDto tagDto = new TagDto();

        tagDto.setId(1);
        tagDto.setNameTag("TestTag");

        List<TranslatorDto> translators = new ArrayList<>();
        TranslatorDto translatorDto1 = new TranslatorDto(1, "Hello", "Привет");
        TranslatorDto translatorDto2 = new TranslatorDto(2, "Goodbye", "Пока");
        translators.add(translatorDto1);
        translators.add(translatorDto2);
        tagDto.setTranslators(translators);

        assertEquals(1, tagDto.getId());
        assertEquals("TestTag", tagDto.getNameTag());
        assertEquals(translators, tagDto.getTranslators());
    }

    @Test
    void testAllArgsConstructor() {
        List<TranslatorDto> translators = new ArrayList<>();
        TranslatorDto translatorDto1 = new TranslatorDto(1, "Hello", "Привет");
        TranslatorDto translatorDto2 = new TranslatorDto(2, "Goodbye", "Пока");
        translators.add(translatorDto1);
        translators.add(translatorDto2);

        TagDto tagDto = new TagDto(1, "TestTag", translators);

        assertEquals(1, tagDto.getId());
        assertEquals("TestTag", tagDto.getNameTag());
        assertEquals(translators, tagDto.getTranslators());
    }

    @Test
    void testNoArgsConstructor() {
        TagDto tagDto = new TagDto();

        assertEquals(0, tagDto.getId());
        assertEquals(null, tagDto.getNameTag());
        assertEquals(null, tagDto.getTranslators());
    }
}
