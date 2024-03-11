package com.myapp.backend.service;

import com.myapp.backend.dto.TagDto;
import com.myapp.backend.mapper.TagMapper;
import com.myapp.backend.model.*;
import com.myapp.backend.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    public Tag getTagById(long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(long id) {
        tagRepository.deleteById(id);
    }

    public Tag updateTag(long id, Tag updateTag) {
        Optional<Tag> existingTag = tagRepository.findById(id);
        if (existingTag.isPresent()) {
            Tag tagToUpdate = existingTag.get();
            tagToUpdate.setNameTag(updateTag.getNameTag());
            tagToUpdate.setTranslators(updateTag.getTranslators());
            return tagRepository.save(tagToUpdate);
        }
        return null;
    }
    public Tag createTag(String nameTag, Set<Translator> translators) {
        Tag tag = new Tag();
        tag.setTranslators(translators);
        tag.setNameTag(nameTag);
        return tagRepository.save(tag);
    }

    public TagDto getDto(long id) {
        return tagMapper.toDto(getTagById(id));
    }
}
