package com.myapp.backend.mapper;

import com.myapp.backend.dto.TagDto;
import com.myapp.backend.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public TagDto toDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getNameTag(), tag.getTranslators());
    }
}
