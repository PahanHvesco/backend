package com.myapp.backend.controller;

import com.myapp.backend.dto.TagDto;
import com.myapp.backend.model.Tag;
import com.myapp.backend.service.TagService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
@RequestMapping("/tag")
@Log4j2
@AllArgsConstructor
public final class TagController {
    private final TagService tagService;

    @PostMapping("/add/tags")
    public void addTagToTranslatorAndTranslatorToTag(@RequestParam(value = "id") final long translatorId, @RequestParam(value = "name") final String nameTag) {
        tagService.addTranslatorToTag(translatorId, nameTag);
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable final long id) {
        log.info("GET endpoint /tag/{id} was called.");
        return tagService.convertEntityToDto(tagService.getTagById(id));
    }

    @GetMapping("/all")
    public List<TagDto> getAllTags() {
        return tagService.getAllTagDto();
    }

    @PostMapping
    public Tag createTag(@RequestBody final Tag tag) {
        log.info("POST endpoint /tag/{id} was called.");
        return tagService.createTag(tag);
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable final long id, @RequestBody final Tag tag) {
        log.info("PUT endpoint /tag/{id} was called.");
        return tagService.updateTag(id, tag);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable final long id) {
        log.info("DELETE endpoint /tag/{id} was called.");
        tagService.deleteTag(id);
    }
}
