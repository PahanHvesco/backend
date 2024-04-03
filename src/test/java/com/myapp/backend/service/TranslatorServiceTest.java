package com.myapp.backend.service;

import com.myapp.backend.component.SimpleCacheComponent;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.mapper.TranslatorMapper;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.TranslatorRepository;
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

class TranslatorServiceTest {
    @InjectMocks
    private TranslatorService translatorService;

    @Mock
    private TranslatorRepository translatorRepository;

    @Mock
    private SimpleCacheComponent<Translator> simpleCacheComponent;

    @Mock
    private TranslatorMapper translatorMapper;

    @Mock
    private HistoryTranslationService historyTranslationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchByTable() {
        // Mocking
        String lineToTranslate = "Hello";
        Translator translator = new Translator();
        translator.setId(1L);
        translator.setEn("Hello");
        translator.setRu("Привет");
        when(translatorRepository.findAll()).thenReturn(List.of(translator));

        // Testing
        TranslatorDto result = translatorService.searchByTable(lineToTranslate);

        // Verification
        assertNotNull(result);
        assertEquals(translator.getEn(), result.getEn());
        assertEquals(translator.getRu(), result.getRu());
    }

    @Test
    public void testCreateTranslator() {
        // Mocking
        Translator translator = new Translator();
        when(translatorRepository.save(translator)).thenReturn(translator);

        // Testing
        Translator result = translatorService.createTranslator(translator);

        // Verification
        assertNotNull(result);
        assertEquals(translator, result);
    }

    @Test
    public void testCreateTranslatorsBulk() {
        // Mocking
        List<Translator> translators = new ArrayList<>();
        Translator translator = new Translator();
        translators.add(translator);
        when(translatorRepository.save(translator)).thenReturn(translator);

        // Testing
        translatorService.createTranslatorsBulk(translators);

        // Verification
        verify(translatorRepository, times(1)).save(translator);
    }

    @Test
    public void testGetAllTranslators() {
        // Mocking
        List<Translator> translators = new ArrayList<>();
        translators.add(new Translator());
        when(translatorRepository.findAll()).thenReturn(translators);

        // Testing
        List<Translator> result = translatorService.getAllTranslators();

        // Verification
        assertNotNull(result);
        assertEquals(translators, result);
    }

    @Test
    public void testGetAllTranslatorsDto() {
        // Mocking
        List<Translator> translators = new ArrayList<>();
        translators.add(new Translator());
        when(translatorRepository.findAll()).thenReturn(translators);
        when(translatorMapper.entityToDto(any())).thenReturn(new TranslatorDto());

        // Testing
        List<TranslatorDto> result = translatorService.getAllTranslatorsDto();

        // Verification
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetTranslatorById() {
        // Mocking
        long id = 1L;
        Translator translator = new Translator();
        translator.setId(id);
        when(simpleCacheComponent.get(id)).thenReturn(null);
        when(translatorRepository.existsById(id)).thenReturn(true);
        when(translatorRepository.findById(id)).thenReturn(Optional.of(translator));

        // Testing
        Translator result = translatorService.getTranslatorById(id);

        // Verification
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void testUpdateTranslator() {
        // Mocking
        long id = 1L;
        Translator updatedTranslator = new Translator();
        when(translatorRepository.existsById(id)).thenReturn(true);
        when(translatorRepository.findById(id)).thenReturn(Optional.of(new Translator()));

        // Testing
        Translator result = translatorService.updateTranslator(id, updatedTranslator);

        // Verification
        assertNull(result); // Assuming that this method returns null
    }

    @Test
    public void testDeleteTranslatorById() {
        // Mocking
        long id = 1L;
        Translator translator = new Translator();
        translator.setId(id);
        when(translatorRepository.existsById(id)).thenReturn(true);

        // Testing
        translatorService.deleteTranslatorById(id);

        // Verification
        verify(simpleCacheComponent, times(1)).remove(id);
        verify(translatorRepository, times(1)).deleteById(id);
    }

    @Test
    public void testConvertEntityToDto() {
        // Mocking
        Translator translator = new Translator();
        when(translatorMapper.entityToDto(translator)).thenReturn(new TranslatorDto());

        // Testing
        TranslatorDto result = translatorService.convertEntityToDto(translator);

        // Verification
        assertNotNull(result);
    }

    @Test
    public void testConvertDtoToEntity() {
        // Mocking
        TranslatorDto translatorDto = new TranslatorDto();
        when(translatorMapper.dtoToEntity(translatorDto)).thenReturn(new Translator());

        // Testing
        Translator result = translatorService.convertDtoToEntity(translatorDto);

        // Verification
        assertNotNull(result);
    }
}
