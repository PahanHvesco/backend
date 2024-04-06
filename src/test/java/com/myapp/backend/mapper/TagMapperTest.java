package com.myapp.backend.mapper;

import com.myapp.backend.dto.TagDto;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.model.Tag;
import com.myapp.backend.model.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TagMapperTest {

    @Autowired
    private TagMapper tagMapper;

    @Test
    public void testEntityToDto() {
        // Given
        Translator translator1 = new Translator(1L, "Hello", "Привет", null, null);
        Translator translator2 = new Translator(2L, "Goodbye", "До свидания", null, null);
        Tag tag = new Tag(1L, "Greeting", List.of(translator1, translator2));

        // When
        TagDto tagDto = tagMapper.entityToDto(tag);

        // Then
        assertNotNull(tagDto);
        assertEquals(tag.getId(), tagDto.getId());
        assertEquals(tag.getNameTag(), tagDto.getNameTag());
        assertEquals(tag.getTranslators().size(), tagDto.getTranslators().size());

        for (int i = 0; i < tag.getTranslators().size(); i++) {
            Translator expectedTranslator = tag.getTranslators().get(i);
            TranslatorDto actualTranslatorDto = tagDto.getTranslators().get(i);

            assertNotNull(actualTranslatorDto);
            assertEquals(expectedTranslator.getId(), actualTranslatorDto.getId());
            assertEquals(expectedTranslator.getEn(), actualTranslatorDto.getEn());
            assertEquals(expectedTranslator.getRu(), actualTranslatorDto.getRu());
        }
    }

    @Test
    public void testDtoToEntity() {
        // Given
        TranslatorDto translatorDto1 = new TranslatorDto(1L, "Hello", "Привет");
        TranslatorDto translatorDto2 = new TranslatorDto(2L, "Goodbye", "До свидания");
        TagDto tagDto = new TagDto(1L, "Greeting", List.of(translatorDto1, translatorDto2));

        // When
        Tag tag = tagMapper.dtoToEntity(tagDto);

        // Then
        assertNotNull(tag);
        assertEquals(tagDto.getId(), tag.getId());
        assertEquals(tagDto.getNameTag(), tag.getNameTag());
        assertEquals(tagDto.getTranslators().size(), tag.getTranslators().size());

        for (int i = 0; i < tagDto.getTranslators().size(); i++) {
            TranslatorDto expectedTranslatorDto = tagDto.getTranslators().get(i);
            Translator actualTranslator = tag.getTranslators().get(i);

            assertNotNull(actualTranslator);
            assertEquals(expectedTranslatorDto.getId(), actualTranslator.getId());
            assertEquals(expectedTranslatorDto.getEn(), actualTranslator.getEn());
            assertEquals(expectedTranslatorDto.getRu(), actualTranslator.getRu());
        }
    }
}
