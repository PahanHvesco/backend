package com.myapp.backend.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class TagDtoTests {

    @Test
    public void testTagDto() {
        long id = 1L;
        String nameTag = "tag";
        List<TranslatorDto> translators = new ArrayList<>();

        TagDto dto = new TagDto(id, nameTag, translators);

        assertEquals(id, dto.getId());
        assertEquals(nameTag, dto.getNameTag());
        assertEquals(translators, dto.getTranslators());
    }
}
