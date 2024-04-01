package com.myapp.backend.mapper;

import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.mapper.interfaces.IMapper;
import com.myapp.backend.model.Translator;
import org.springframework.stereotype.Component;

@Component
public final class TranslatorMapper implements IMapper<TranslatorDto, Translator> {
    @Override
    public TranslatorDto entityToDto(final Translator entity) {
        return new TranslatorDto(entity.getId(), entity.getEn(), entity.getRu());
    }

    @Override
    public Translator dtoToEntity(final TranslatorDto dto) {
        return new Translator(dto.getId(), dto.getEn(), dto.getRu(), null, null);
    }
}
