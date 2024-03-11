package com.myapp.backend.controller;

import com.myapp.backend.model.Tag;
import com.myapp.backend.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTag();
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable long id) {
        return tagService.getTagById(id);
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable long id, @RequestBody Tag tag) {
        return tagService.updateTag(id, tag);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
    }
}
