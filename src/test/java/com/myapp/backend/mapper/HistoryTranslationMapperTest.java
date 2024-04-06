package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.model.HistoryTranslation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class HistoryTranslationMapperTest {

    @Autowired
    private HistoryTranslationMapper historyTranslationMapper;

    @Test
    public void testEntityToDto() {
        // Given
        HistoryTranslation historyTranslation = new HistoryTranslation(1L, Timestamp.valueOf("2024-04-06"), "english", "russian", null);

        // When
        HistoryTranslationDto historyTranslationDto = historyTranslationMapper.entityToDto(historyTranslation);

        // Then
        assertNotNull(historyTranslationDto);
        assertEquals(historyTranslation.getId(), historyTranslationDto.getId());
        assertEquals(historyTranslation.getDate(), historyTranslationDto.getDate());
        assertEquals(historyTranslation.getSource(), historyTranslationDto.getSource());
        assertEquals(historyTranslation.getTarget(), historyTranslationDto.getTarget());
    }

    @Test
    public void testDtoToEntity() {
        // Given
        HistoryTranslationDto historyTranslationDto = new HistoryTranslationDto(1L, Timestamp.valueOf("2024-04-06"), "english", "russian");

        // When
        HistoryTranslation historyTranslation = historyTranslationMapper.dtoToEntity(historyTranslationDto);

        // Then
        assertNotNull(historyTranslation);
        assertEquals(historyTranslationDto.getId(), historyTranslation.getId());
        assertEquals(historyTranslationDto.getDate(), historyTranslation.getDate());
        assertEquals(historyTranslationDto.getSource(), historyTranslation.getSource());
        assertEquals(historyTranslationDto.getTarget(), historyTranslation.getTarget());
    }
}
