package com.myapp.backend.service;

import com.myapp.backend.component.SimpleCacheComponent;
import com.myapp.backend.dto.TagDto;
import com.myapp.backend.exception.ResourceNotFoundException;
import com.myapp.backend.mapper.TagMapper;
import com.myapp.backend.model.Tag;
import com.myapp.backend.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public final  class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final SimpleCacheComponent<Tag> simpleCacheComponent;
    private final TranslatorService translatorService;
    private static final String NOT_FOUND = "Not found Tag by id: ";

    public void addTranslatorToTag(final long id, final String nameTag) {
        ArrayList<Tag> tags = new ArrayList<>(getAllTag());
        for (Tag tag : tags) {
            if (tag.getNameTag().equals(nameTag)) {
                tag.getTranslators()
                        .add(translatorService.getTranslatorById(id));
                updateTag(tag.getId(), tag);
            }
        }
    }

    public List<TagDto> getAllTagDto() {
        List<TagDto> tagsDto = new ArrayList<>();
        for (Tag tag : tagRepository.findAll()) {
            tagsDto.add(tagMapper.entityToDto(tag));
        }
        return tagsDto;
    }

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    public Tag getTagById(final long id) {
        Tag tag = simpleCacheComponent.get(id);
        if (tag == null) {
            if (!tagRepository.existsById(id)) {
                throw new ResourceNotFoundException(NOT_FOUND + id);
            }
            tag = tagRepository.findById(id).orElse(null);
            simpleCacheComponent.put(id, tag);
            log.info("GET endpoint from BD.");
        } else {
            log.info("GET endpoint from CACHE.");
        }
        log.info("GET endpoint success.");
        return tag;
    }

    public Tag createTag(final Tag tag) {
        log.info("POST endpoint success.");
        return tagRepository.save(tag);
    }

    public void deleteTag(final long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException(NOT_FOUND + id);
        }
        simpleCacheComponent.remove(id);
        tagRepository.deleteById(id);
        log.info("DELETE endpoint success.");
    }

    public Tag updateTag(final long id, final Tag updateTag) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException(NOT_FOUND + id);
        }
        Optional<Tag> existingTag = tagRepository.findById(id);
        if (existingTag.isPresent()) {
            Tag tagToUpdate = existingTag.get();
            tagToUpdate.setNameTag(updateTag.getNameTag());
            tagToUpdate.setTranslators(updateTag.getTranslators());
            return tagRepository.save(tagToUpdate);
        }
        log.info("PUT endpoint success.");
        return null;
    }

    public TagDto convertEntityToDto(final Tag tag) {
        return tagMapper.entityToDto(tag);
    }

    public Tag convertDtoToEntity(final TagDto tagDto) {
        return tagMapper.dtoToEntity(tagDto);
    }
}
