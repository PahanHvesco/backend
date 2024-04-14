package com.myapp.backend.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.model.HistoryTranslation;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;

class HistoryTranslationMapperTests {

    @Test
    public void testEntityToDto() {
        HistoryTranslationMapper mapper = new HistoryTranslationMapper();

        long id = 1L;
        Timestamp date = new Timestamp(System.currentTimeMillis());
        String source = "source";
        String target = "target";

        HistoryTranslation entity = new HistoryTranslation(id, date, source, target, null);

        HistoryTranslationDto dto = mapper.entityToDto(entity);

        assertEquals(id, dto.getId());
        assertEquals(date, dto.getDate());
        assertEquals(source, dto.getSource());
        assertEquals(target, dto.getTarget());
    }

    @Test
    public void testDtoToEntity() {
        HistoryTranslationMapper mapper = new HistoryTranslationMapper();

        long id = 1L;
        Timestamp date = new Timestamp(System.currentTimeMillis());
        String source = "source";
        String target = "target";

        HistoryTranslationDto dto = new HistoryTranslationDto(id, date, source, target);

        HistoryTranslation entity = mapper.dtoToEntity(dto);

        assertEquals(id, entity.getId());
        assertEquals(date, entity.getDate());
        assertEquals(source, entity.getSource());
        assertEquals(target, entity.getTarget());
    }
}
