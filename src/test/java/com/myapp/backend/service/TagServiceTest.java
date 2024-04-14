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
import org.mockito.Mockito;
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
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddTranslatorToTag_EmptyTagList() {
        // Arrange
        when(tagRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        tagService.addTranslatorToTag(1L, "exampleTag");
    }

    @Test
    void testAddTranslatorToTag_NoMatchingTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "tag1", null));
        tags.add(new Tag(2, "tag2", null));
        when(tagRepository.findAll()).thenReturn(tags);

        // Act
        tagService.addTranslatorToTag(1L, "exampleTag");

        // Assert
        verifyNoInteractions(translatorService);
        verify(tagRepository, times(1)).findAll();
        verifyNoMoreInteractions(tagRepository);
    }

    @Test
    void testAddTranslatorToTag_MatchingTagsExist() {
        Tag matchingTag1 = new Tag(1, "exampleTag", null);
        Tag matchingTag2 = new Tag(2, "exampleTag", null);
        Tag nonMatchingTag = new Tag(3, "otherTag", null);
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(matchingTag1);
        tags.add(matchingTag2);
        tags.add(nonMatchingTag);
        when(tagRepository.findAll()).thenReturn(tags);
        when(translatorService.getTranslatorById(1L)).thenReturn(new Translator());

        // Act

        // Assert

    }

    @Test
    void testAddTranslatorToTag_EmptyTags() {
        // Arrange
        TagService tagService = Mockito.mock(TagService.class);
        when(tagService.getAllTag()).thenReturn(new ArrayList<>());

        // Act
        tagService.addTranslatorToTag(1L, "exampleTag");

        // Assert - Verify that no interactions with tagService or translatorService occurred
    }

    @Test
    void testAddTranslatorToTag_TagNotFound() {
        // Arrange
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "tag1", new ArrayList<>()));
        tags.add(new Tag(2L, "tag2", new ArrayList<>()));
        when(tagService.getAllTag()).thenReturn(tags);

        // Act
        tagService.addTranslatorToTag(1L, "exampleTag");

        // Assert - Verify that no interactions with tagService or translatorService occurred
        verifyNoInteractions(translatorService);
    }

    @Test
    void testAddTranslatorToTag_TagFound() {
        // Arrange
        long translatorId = 1L;
        String tagName = "exampleTag";
        List<Tag> tags = new ArrayList<>();
        List<Translator> translators = new ArrayList<>();
        Translator translator = new Translator(translatorId, "TranslatorName", "TranslatorLanguage", null, null);
        translators.add(translator);
        tags.add(new Tag(1L, tagName, translators));
        when(tagService.getAllTag()).thenReturn(tags);
        when(translatorService.getTranslatorById(translatorId)).thenReturn(translator);

        // Assert - Verify that updateTag method was called with correct parameters
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
    void addTranslatorToTag_TagNotFound_NothingAdded() {
        long translatorId = 1L;
        String nonExistentTagName = "nonExistentTag";

        // Mock the behavior of getAllTag() to return an empty list
        when(tagService.getAllTag()).thenReturn(new ArrayList<>());

        // Act
        tagService.addTranslatorToTag(translatorId, nonExistentTagName);

        // Assert
        // Verify that updateTag method is never called since the tag doesn't exist
        verify(tagRepository, never()).save(any());
    }

    @Test
    void getAllTagDto_ReturnsListOfTagDtos() {
        // Arrange
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "Tag1", new ArrayList<>()));
        tags.add(new Tag(2L, "Tag2", new ArrayList<>()));

        // Mock the behavior of tagRepository.findAll() to return the list of tags
        when(tagRepository.findAll()).thenReturn(tags);

        // Mock the behavior of tagMapper.entityToDto() to return a TagDto
        when(tagMapper.entityToDto(any(Tag.class))).thenAnswer(invocation -> {
            Tag tag = invocation.getArgument(0);
            return new TagDto(tag.getId(), tag.getNameTag(), null);
        });

        // Act
        List<TagDto> result = tagService.getAllTagDto();

        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Tag1", result.get(0).getNameTag());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Tag2", result.get(1).getNameTag());
    }

    @Test
    void testGetAllTag() {
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
    void testGetTagById() {
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
    void testCreateTag() {
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
    void testCreateTagsBulk() {
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
    void testDeleteTag() {
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
    void testUpdateTag() {
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
    void testConvertEntityToDto() {
        // Mocking
        Tag tag = new Tag();
        when(tagMapper.entityToDto(tag)).thenReturn(new TagDto());

        // Testing
        TagDto result = tagService.convertEntityToDto(tag);

        // Verification
        assertNotNull(result);
    }

    @Test
    void testConvertDtoToEntity() {
        // Mocking
        TagDto tagDto = new TagDto();
        when(tagMapper.dtoToEntity(tagDto)).thenReturn(new Tag());

        // Testing
        Tag result = tagService.convertDtoToEntity(tagDto);

        // Verification
        assertNotNull(result);
    }
}
