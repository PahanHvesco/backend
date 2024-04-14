package com.myapp.backend.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.model.Translator;
import com.myapp.backend.service.TranslatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
public class TranslatorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TranslatorService translatorService;

    @InjectMocks
    private TranslatorController translatorController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(translatorController).build();
    }

    @Test
    public void testTranslate() throws Exception {
        String lineToTranslate = "Привет";
        String languageFrom = "ru";
        String languageTo = "en";
        TranslatorDto translatorDto = new TranslatorDto(); // Создайте объект TranslatorDto с нужными данными
        when(translatorService.translate(languageFrom, languageTo, lineToTranslate.toLowerCase())).thenReturn(translatorDto);

        mockMvc.perform(get("/translator/translate")
                        .param("str", lineToTranslate)
                        .param("from", languageFrom)
                        .param("to", languageTo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(translatorDto.getId())); // Замените "fieldName" на актуальное имя поля
    }

    @Test
    public void testGetTranslatorById() throws Exception {
        long translatorId = 1L;
        TranslatorDto translatorDto = new TranslatorDto(); // Создайте объект TranslatorDto с нужными данными
        when(translatorService.convertEntityToDto(any())).thenReturn(translatorDto);
        when(translatorService.getTranslatorById(translatorId)).thenReturn(new Translator());

        mockMvc.perform(get("/translator/" + translatorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(translatorDto.getId())); // Замените "fieldName" на актуальное имя поля
    }

    @Test
    public void testGetAllTranslators() throws Exception {
        TranslatorDto translatorDto = new TranslatorDto(); // Создайте объект TranslatorDto с нужными данными
        when(translatorService.getAllTranslatorsDto()).thenReturn(Collections.singletonList(translatorDto));

        mockMvc.perform(get("/translator/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(translatorDto.getId())); // Замените "fieldName" на актуальное имя поля
    }

    @Test
    public void testCreateTranslator() throws Exception {
        Translator translator = new Translator(); // Создайте объект Translator с нужными данными
        when(translatorService.createTranslator(any(Translator.class))).thenReturn(translator);

        mockMvc.perform(post("/translator")
                        .contentType("application/json")
                        .content("{}")) // Замените {} на JSON объект с нужными данными для создания переводчика
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateHistoryTranslations() throws Exception {
        Translator translator1 = new Translator(); // Создайте объекты Translator с нужными данными
        Translator translator2 = new Translator();
        doNothing().when(translatorService).createTranslatorsBulk(anyList());

        mockMvc.perform(post("/translator/bulk")
                        .contentType("application/json")
                        .content("[{}, {}]")) // Замените {} на JSON объекты с нужными данными для создания переводчиков
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTranslator() throws Exception {
        long translatorId = 1L;
        Translator translator = new Translator(); // Создайте объект Translator с нужными данными
        when(translatorService.updateTranslator(eq(translatorId), any(Translator.class))).thenReturn(translator);

        mockMvc.perform(put("/translator/" + translatorId)
                        .contentType("application/json")
                        .content("{}")) // Замените {} на JSON объект с нужными данными для обновления переводчика
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTranslator() throws Exception {
        long translatorId = 1L;
        doNothing().when(translatorService).deleteTranslatorById(translatorId);

        mockMvc.perform(delete("/translator/" + translatorId))
                .andExpect(status().isOk());
    }
}
