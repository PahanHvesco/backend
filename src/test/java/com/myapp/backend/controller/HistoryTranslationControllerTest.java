package com.myapp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.service.HistoryTranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistoryTranslationController.class)
public class HistoryTranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoryTranslationService historyTranslationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllHistoryTranslations() throws Exception {
        // Mock service response
        List<HistoryTranslationDto> expectedDtos = List.of(
                new HistoryTranslationDto(1L, null, "english", "russian"),
                new HistoryTranslationDto(2L, null, "french", "german")
        );
        when(historyTranslationService.getAllHistoryTranslationsDto()).thenReturn(expectedDtos);

        // Perform GET request and verify response
        mockMvc.perform(get("/history/translation/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(expectedDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].date").value(expectedDtos.get(0).getDate()))
                .andExpect(jsonPath("$[0].source").value(expectedDtos.get(0).getSource()))
                .andExpect(jsonPath("$[0].target").value(expectedDtos.get(0).getTarget()))
                .andExpect(jsonPath("$[1].id").value(expectedDtos.get(1).getId()))
                .andExpect(jsonPath("$[1].date").value(expectedDtos.get(1).getDate()))
                .andExpect(jsonPath("$[1].source").value(expectedDtos.get(1).getSource()))
                .andExpect(jsonPath("$[1].target").value(expectedDtos.get(1).getTarget()));
    }

    @Test
    public void testGetTranslationsBySourceAndTarget() throws Exception {
        // Mock service response
        List<TranslatorDto> expectedDtos = List.of(
                new TranslatorDto(1L, "Hello", "Привет"),
                new TranslatorDto(2L, "Goodbye", "До свидания")
        );
        when(historyTranslationService.getTranslationsBySourceAndTarget("english", "russian")).thenReturn(expectedDtos);

        // Perform GET request and verify response
        mockMvc.perform(get("/history/translation/english/russian"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(expectedDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].en").value(expectedDtos.get(0).getEn()))
                .andExpect(jsonPath("$[0].ru").value(expectedDtos.get(0).getRu()))
                .andExpect(jsonPath("$[1].id").value(expectedDtos.get(1).getId()))
                .andExpect(jsonPath("$[1].en").value(expectedDtos.get(1).getEn()))
                .andExpect(jsonPath("$[1].ru").value(expectedDtos.get(1).getRu()));
    }

    @Test
    public void testCreateHistoryTranslation() throws Exception {
        // Mock service response
        HistoryTranslation createdTranslation = new HistoryTranslation(1L, null, "english", "russian", null);
        when(historyTranslationService.createHistoryTranslation(any())).thenReturn(createdTranslation);

        // Perform POST request with JSON payload and verify response
        mockMvc.perform(post("/history/translation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdTranslation)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdTranslation.getId()))
                .andExpect(jsonPath("$.date").value(createdTranslation.getDate()))
                .andExpect(jsonPath("$.source").value(createdTranslation.getSource()))
                .andExpect(jsonPath("$.target").value(createdTranslation.getTarget()));
    }

    @Test
    public void testCreateHistoryTranslations() throws Exception {
        // Perform POST request with JSON payload and verify response
        mockMvc.perform(post("/history/translation/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(
                                new HistoryTranslation(1L, null, "english", "russian", null),
                                new HistoryTranslation(2L, null, "french", "german", null)
                        ))))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateHistoryTranslation() throws Exception {
        // Mock service response
        HistoryTranslation updatedTranslation = new HistoryTranslation(1L, null, "english", "russian", null);
        when(historyTranslationService.updateHistoryTranslation(anyLong(), any())).thenReturn(updatedTranslation);

        // Perform PUT request with JSON payload and verify response
        mockMvc.perform(put("/history/translation/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTranslation)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedTranslation.getId()))
                .andExpect(jsonPath("$.date").value(updatedTranslation.getDate()))
                .andExpect(jsonPath("$.source").value(updatedTranslation.getSource()))
                .andExpect(jsonPath("$.target").value(updatedTranslation.getTarget()));
    }

    @Test
    public void testDeleteHistoryTranslation() throws Exception {
        // Perform DELETE request and verify response
        mockMvc.perform(delete("/history/translation/1"))
                .andExpect(status().isOk());
    }
}
