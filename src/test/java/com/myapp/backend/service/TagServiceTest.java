package com.myapp.backend.service;

import com.myapp.backend.component.SimpleCacheComponent;
import com.myapp.backend.dto.TagDto;
import com.myapp.backend.mapper.TagMapper;
import com.myapp.backend.model.Tag;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceTest {
    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private SimpleCacheComponent<Tag> simpleCacheComponent;

    @Mock
    private TranslatorService translatorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addTranslatorToTag_TagDoesNotExist_NoTranslatorAdded() {
        // Arrange
        long translatorId = 1L;
        String nonExistentTagName = "nonExistentTag";

        when(tagRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        tagService.addTranslatorToTag(translatorId, nonExistentTagName); // Correct method call

        // Assert
        verify(tagRepository, never()).save(any());
    }

    @Test
    public void testGetAllTag() {
        // Mocking
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag());
        when(tagRepository.findAll()).thenReturn(tags);

        // Testing
        List<Tag> result = tagService.getAllTag();

        // Verification
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetTagById() {
        // Mocking
        long id = 1L;
        Tag tag = new Tag();
        tag.setId(id);
        when(simpleCacheComponent.get(id)).thenReturn(null);
        when(tagRepository.existsById(id)).thenReturn(true);
        when(tagRepository.findById(id)).thenReturn(Optional.of(tag));

        // Testing
        Tag result = tagService.getTagById(id);

        // Verification
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void testCreateTag() {
        // Mocking
        Tag tag = new Tag();
        when(tagRepository.save(tag)).thenReturn(tag);

        // Testing
        Tag result = tagService.createTag(tag);

        // Verification
        assertNotNull(result);
        assertEquals(tag, result);
    }

    @Test
    public void testCreateTagsBulk() {
        // Mocking
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tags.add(tag);
        when(tagRepository.save(tag)).thenReturn(tag);

        // Testing
        tagService.createTagsBulk(tags);

        // Verification
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    public void testDeleteTag() {
        // Mocking
        long id = 1L;
        when(tagRepository.existsById(id)).thenReturn(true);

        // Testing
        tagService.deleteTag(id);

        // Verification
        verify(simpleCacheComponent, times(1)).remove(id);
        verify(tagRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateTag() {
        // Mocking
        long id = 1L;
        Tag updatedTag = new Tag();
        when(tagRepository.existsById(id)).thenReturn(true);
        when(tagRepository.findById(id)).thenReturn(Optional.of(new Tag()));

        // Testing
        Tag result = tagService.updateTag(id, updatedTag);

        // Verification
        assertNull(result); // Assuming that this method returns null
    }

    @Test
    public void testConvertEntityToDto() {
        // Mocking
        Tag tag = new Tag();
        when(tagMapper.entityToDto(tag)).thenReturn(new TagDto());

        // Testing
        TagDto result = tagService.convertEntityToDto(tag);

        // Verification
        assertNotNull(result);
    }

    @Test
    public void testConvertDtoToEntity() {
        // Mocking
        TagDto tagDto = new TagDto();
        when(tagMapper.dtoToEntity(tagDto)).thenReturn(new Tag());

        // Testing
        Tag result = tagService.convertDtoToEntity(tagDto);

        // Verification
        assertNotNull(result);
    }
}
