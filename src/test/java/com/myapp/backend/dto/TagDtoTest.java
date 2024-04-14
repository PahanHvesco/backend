package com.myapp.backend.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class TagDtoTests {
    @Test
    void testGetterAndSetter() {
        // Создаем объект TagDto
        TagDto tagDto = new TagDto();

        // Устанавливаем значения с помощью сеттеров
        tagDto.setId(1);
        tagDto.setNameTag("TestTag");

        // Создаем список объектов TranslatorDto
        List<TranslatorDto> translators = new ArrayList<>();
        TranslatorDto translatorDto1 = new TranslatorDto(1, "Hello", "Привет");
        TranslatorDto translatorDto2 = new TranslatorDto(2, "Goodbye", "Пока");
        translators.add(translatorDto1);
        translators.add(translatorDto2);
        tagDto.setTranslators(translators);

        // Проверяем, что геттеры возвращают ожидаемые значения
        assertEquals(1, tagDto.getId());
        assertEquals("TestTag", tagDto.getNameTag());
        assertEquals(translators, tagDto.getTranslators());
    }

    @Test
    void testAllArgsConstructor() {
        // Создаем список объектов TranslatorDto
        List<TranslatorDto> translators = new ArrayList<>();
        TranslatorDto translatorDto1 = new TranslatorDto(1, "Hello", "Привет");
        TranslatorDto translatorDto2 = new TranslatorDto(2, "Goodbye", "Пока");
        translators.add(translatorDto1);
        translators.add(translatorDto2);

        // Создаем объект TagDto с помощью всех аргументов конструктора
        TagDto tagDto = new TagDto(1, "TestTag", translators);

        // Проверяем, что все значения установлены корректно
        assertEquals(1, tagDto.getId());
        assertEquals("TestTag", tagDto.getNameTag());
        assertEquals(translators, tagDto.getTranslators());
    }

    @Test
    void testNoArgsConstructor() {
        // Создаем объект TagDto с помощью конструктора без аргументов
        TagDto tagDto = new TagDto();

        // Проверяем, что все значения по умолчанию (0 или null)
        assertEquals(0, tagDto.getId());
        assertEquals(null, tagDto.getNameTag());
        assertEquals(null, tagDto.getTranslators());
    }
}
