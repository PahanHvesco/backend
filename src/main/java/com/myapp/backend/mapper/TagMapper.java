package com.myapp.backend.mapper;

import com.myapp.backend.dto.TagDto;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.mapper.interfaces.IMapper;
import com.myapp.backend.model.Tag;
import com.myapp.backend.model.Translator;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public final class TagMapper implements IMapper<TagDto, Tag> {
    @Override
    public TagDto entityToDto(final Tag entity) {
        return new TagDto(entity.getId(), entity.getNameTag(),
                entity.getTranslators().stream().map(translator -> new TranslatorDto(translator.getId(),
                        translator.getEn(), translator.getRu())).collect(Collectors.toList()));
    }

    @Override
    public Tag dtoToEntity(final TagDto dto) {
        return new Tag(dto.getId(), dto.getNameTag(), dto.getTranslators().stream().map(translatorDto ->
                new Translator(translatorDto.getId(), translatorDto.getEn(), translatorDto.getRu(),
                        null, null)).collect(Collectors.toList()));
    }
}
