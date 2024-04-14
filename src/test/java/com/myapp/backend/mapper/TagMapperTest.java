package com.myapp.backend.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.myapp.backend.dto.TagDto;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.model.Tag;
import com.myapp.backend.model.Translator;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class TagMapperTests {

    @Test
    public void testEntityToDto() {
        TagMapper mapper = new TagMapper();

        long id = 1L;
        String nameTag = "tag";
        List<Translator> translators = new ArrayList<>();
        Translator translator = new Translator(1L, "en", "ru", null, null);
        translators.add(translator);

        Tag entity = new Tag(id, nameTag, translators);

        TagDto dto = mapper.entityToDto(entity);

        assertEquals(id, dto.getId());
        assertEquals(nameTag, dto.getNameTag());
        assertEquals(translators.size(), dto.getTranslators().size());
        assertEquals(translator.getId(), dto.getTranslators().get(0).getId());
        assertEquals(translator.getEn(), dto.getTranslators().get(0).getEn());
        assertEquals(translator.getRu(), dto.getTranslators().get(0).getRu());
    }

    @Test
    public void testDtoToEntity() {
        TagMapper mapper = new TagMapper();

        long id = 1L;
        String nameTag = "tag";
        List<TranslatorDto> translators = new ArrayList<>();
        TranslatorDto translatorDto = new TranslatorDto(1L, "en", "ru");
        translators.add(translatorDto);

        TagDto dto = new TagDto(id, nameTag, translators);

        Tag entity = mapper.dtoToEntity(dto);

        assertEquals(id, entity.getId());
        assertEquals(nameTag, entity.getNameTag());
        assertEquals(translators.size(), entity.getTranslators().size());
        assertEquals(translatorDto.getId(), entity.getTranslators().iterator().next().getId());
        assertEquals(translatorDto.getEn(), entity.getTranslators().iterator().next().getEn());
        assertEquals(translatorDto.getRu(), entity.getTranslators().iterator().next().getRu());
    }
}
