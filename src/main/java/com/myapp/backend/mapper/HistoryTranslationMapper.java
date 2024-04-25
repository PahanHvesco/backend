package com.myapp.backend.mapper;

import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.mapper.interfaces.IMapper;
import com.myapp.backend.model.HistoryTranslation;
import org.springframework.stereotype.Component;

@Component
public final class HistoryTranslationMapper implements IMapper<HistoryTranslationDto, HistoryTranslation> {
    @Override
    public HistoryTranslationDto entityToDto(final HistoryTranslation entity) {
        TranslatorMapper translatorMapper = new TranslatorMapper();
        TranslatorDto translatorDto;
        if (entity.getTranslator() != null) {
            translatorDto = translatorMapper.entityToDto(entity.getTranslator());
        } else {
            translatorDto = null;
        }

        return new HistoryTranslationDto(entity.getId(), entity.getDate(), entity.getSource(),
                                         entity.getTarget(), translatorDto);
    }

    @Override
    public HistoryTranslation dtoToEntity(final HistoryTranslationDto dto) {
        return new HistoryTranslation(dto.getId(), dto.getDate(), dto.getSource(), dto.getTarget(), null);
    }
}
