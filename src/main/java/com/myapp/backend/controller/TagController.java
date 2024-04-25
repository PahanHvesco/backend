package com.myapp.backend.controller;

import com.myapp.backend.dto.TagDto;
import com.myapp.backend.model.Tag;
import com.myapp.backend.service.RequestCounterService;
import com.myapp.backend.service.TagService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tag")
@Log4j2
@AllArgsConstructor
public final class TagController {
    private final TagService tagService;
    private final RequestCounterService requestCounterService;

    @PostMapping("/add/tags")
    public void addTagToTranslatorAndTranslatorToTag(@RequestParam(value = "id") final long translatorId,
                                                     @RequestParam(value = "name") final String nameTag) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        tagService.addTranslatorToTag(translatorId, nameTag);
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable final long id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        //log.info("GET endpoint /tag/{id} was called.");
        return tagService.convertEntityToDto(tagService.getTagById(id));
    }

    @GetMapping("/all")
    public List<TagDto> getAllTags() {
        return tagService.getAllTagDto();
    }

    @PostMapping
    public Tag createTag(@RequestBody final Tag tag) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        //log.info("POST endpoint /tag/{id} was called.");
        return tagService.createTag(tag);
    }

    @PostMapping("/bulk")
    public void createHistoryTranslations(@RequestBody final List<Tag> tags) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        tagService.createTagsBulk(tags);
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable final long id, @RequestBody final Tag tag) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        //log.info("PUT endpoint /tag/{id} was called.");
        return tagService.updateTag(id, tag);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable final long id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        //log.info("DELETE endpoint /tag/{id} was called.");
        tagService.deleteTag(id);
    }
}
