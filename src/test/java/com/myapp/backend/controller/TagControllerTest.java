package com.myapp.backend.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.backend.dto.TagDto;
import org.springframework.http.MediaType;
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
public class TagControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    public void testAddTagToTranslatorAndTranslatorToTag() throws Exception {
        long translatorId = 4L;
        String nameTag = "translation";

        mockMvc.perform(post("/add/tags")
                        .param("id", String.valueOf(translatorId))
                        .param("name", nameTag)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetTagById() throws Exception {
        long tagId = 1L;
        TagDto tagDto = new TagDto(); // Создайте объект TagDto с нужными данными
        when(tagService.convertEntityToDto(any())).thenReturn(tagDto);
        when(tagService.getTagById(tagId)).thenReturn(new Tag());

        mockMvc.perform(get("/tag/" + tagId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tagDto.getId()));
    }

    @Test
    public void testGetAllTags() throws Exception {
        TagDto tagDto = new TagDto();
        when(tagService.getAllTagDto()).thenReturn(Collections.singletonList(tagDto));

        mockMvc.perform(get("/tag/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(tagDto.getId()));
    }

    @Test
    public void testCreateTag() throws Exception {
        Tag tag = new Tag();
        when(tagService.createTag(any(Tag.class))).thenReturn(tag);

        mockMvc.perform(post("/tag")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateHistoryTranslations() throws Exception {
        List<Tag> tags = Arrays.asList(new Tag(), new Tag()); // Создайте объекты Tag с нужными данными
        doNothing().when(tagService).createTagsBulk(tags);

        mockMvc.perform(post("/tag/bulk")
                        .contentType("application/json")
                        .content("[{}, {}]")) // Замените {} на JSON объекты с нужными данными для создания тегов
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTag() throws Exception {
        long tagId = 1L;
        Tag tag = new Tag(); // Создайте объект Tag с нужными данными
        when(tagService.updateTag(eq(tagId), any(Tag.class))).thenReturn(tag);

        mockMvc.perform(put("/tag/" + tagId)
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTag() throws Exception {
        long tagId = 1L;
        doNothing().when(tagService).deleteTag(tagId);

        mockMvc.perform(delete("/tag/" + tagId))
                .andExpect(status().isOk());
    }
}
