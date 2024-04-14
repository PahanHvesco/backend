package com.myapp.backend.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

class HistoryTranslationDtoTests {

    @Test
    void testHistoryTranslationDto() {
        long id = 1L;
        Timestamp date = new Timestamp(System.currentTimeMillis());
        String source = "source";
        String target = "target";

        HistoryTranslationDto dto = new HistoryTranslationDto(id, date, source, target);

        assertEquals(id, dto.getId());
        assertEquals(date, dto.getDate());
        assertEquals(source, dto.getSource());
        assertEquals(target, dto.getTarget());
    }
}
