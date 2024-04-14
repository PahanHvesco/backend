package com.myapp.backend.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TranslatorDtoTests {

    @Test
    void testGetterAndSetter() {
        // Создаем объект TranslatorDto
        TranslatorDto translatorDto = new TranslatorDto();

        // Устанавливаем значения с помощью сеттеров
        translatorDto.setId(1);
        translatorDto.setEn("Hello");
        translatorDto.setRu("Привет");

        // Проверяем, что геттеры возвращают ожидаемые значения
        assertEquals(1, translatorDto.getId());
        assertEquals("Hello", translatorDto.getEn());
        assertEquals("Привет", translatorDto.getRu());
    }

    @Test
    void testAllArgsConstructor() {
        // Создаем объект TranslatorDto с помощью всех аргументов конструктора
        TranslatorDto translatorDto = new TranslatorDto(1, "Hello", "Привет");

        // Проверяем, что все значения установлены корректно
        assertEquals(1, translatorDto.getId());
        assertEquals("Hello", translatorDto.getEn());
        assertEquals("Привет", translatorDto.getRu());
    }

    @Test
    void testNoArgsConstructor() {
        // Создаем объект TranslatorDto с помощью конструктора без аргументов
        TranslatorDto translatorDto = new TranslatorDto();

        // Проверяем, что все значения по умолчанию (0 или null)
        assertEquals(0, translatorDto.getId());
        assertEquals(null, translatorDto.getEn());
        assertEquals(null, translatorDto.getRu());
    }
}
