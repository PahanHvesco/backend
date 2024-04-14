package com.myapp.backend.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.backend.dto.TagDto;
import com.myapp.backend.model.Tag;
import com.myapp.backend.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class TagControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    void testAddTagToTranslatorAndTranslatorToTag() throws Exception {
        doNothing().when(tagService).addTranslatorToTag(1L, "example");
        mockMvc.perform(post("/tag/add/tags?id=1&name=example"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTagById() throws Exception {
        long tagId = 1L;
        TagDto tagDto = new TagDto();
        when(tagService.convertEntityToDto(any())).thenReturn(tagDto);
        when(tagService.getTagById(tagId)).thenReturn(new Tag());

        mockMvc.perform(get("/tag/" + tagId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tagDto.getId()));
    }

    @Test
    void testGetAllTags() throws Exception {
        TagDto tagDto = new TagDto();
        when(tagService.getAllTagDto()).thenReturn(Collections.singletonList(tagDto));

        mockMvc.perform(get("/tag/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(tagDto.getId()));
    }

    @Test
    void testCreateTag() throws Exception {
        Tag tag = new Tag();
        when(tagService.createTag(any(Tag.class))).thenReturn(tag);

        mockMvc.perform(post("/tag")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateHistoryTranslations() throws Exception {
        List<Tag> tags = Arrays.asList(new Tag(), new Tag());
        doNothing().when(tagService).createTagsBulk(tags);

        mockMvc.perform(post("/tag/bulk")
                        .contentType("application/json")
                        .content("[{}, {}]"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateTag() throws Exception {
        long tagId = 1L;
        Tag tag = new Tag();
        when(tagService.updateTag(eq(tagId), any(Tag.class))).thenReturn(tag);

        mockMvc.perform(put("/tag/" + tagId)
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTag() throws Exception {
        long tagId = 1L;
        doNothing().when(tagService).deleteTag(tagId);

        mockMvc.perform(delete("/tag/" + tagId))
                .andExpect(status().isOk());
    }
}
