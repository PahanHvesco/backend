package com.myapp.backend.service;

import com.myapp.backend.component.SimpleCacheComponent;
import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.mapper.HistoryTranslationMapper;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.HistoryTranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HistoryTranslationServiceTest {
    @InjectMocks
    private HistoryTranslationService historyTranslationService;

    @Mock
    private HistoryTranslationRepository historyTranslationRepository;

    @Mock
    private HistoryTranslationMapper historyTranslationMapper;

    @Mock
    private SimpleCacheComponent<HistoryTranslation> simpleCacheComponent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateHistoryTranslation() {
        // Mocking
        Translator translator = new Translator();
        String target = "target";
        String source = "source";
        when(historyTranslationRepository.save(any())).thenReturn(new HistoryTranslation());

        // Testing
        historyTranslationService.createHistoryTranslation(translator, target, source);

        // Verification
        verify(historyTranslationRepository, times(1)).save(any());
    }

    @Test
    public void testGetAllHistoryTranslationsDto() {
        // Mocking
        List<HistoryTranslation> historyTranslations = new ArrayList<>();
        historyTranslations.add(new HistoryTranslation());
        when(historyTranslationRepository.findAll()).thenReturn(historyTranslations);
        when(historyTranslationMapper.entityToDto(any())).thenReturn(new HistoryTranslationDto());

        // Testing
        List<HistoryTranslationDto> result = historyTranslationService.getAllHistoryTranslationsDto();

        // Verification
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetHistoryTranslationById() {
        // Mocking
        long id = 1L;
        HistoryTranslation historyTranslation = new HistoryTranslation();
        historyTranslation.setId(id);
        when(simpleCacheComponent.get(id)).thenReturn(null);
        when(historyTranslationRepository.existsById(id)).thenReturn(true);
        when(historyTranslationRepository.findById(id)).thenReturn(Optional.of(historyTranslation));

        // Testing
        HistoryTranslation result = historyTranslationService.getHistoryTranslationById(id);

        // Verification
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void testGetTranslationsBySourceAndTarget() {
        // Mocking
        String source = "source";
        String target = "target";
        List<TranslatorDto> translatorDtos = new ArrayList<>();
        when(historyTranslationRepository.getTranslationsBySourceAndTarget(source, target)).thenReturn(translatorDtos);

        // Testing
        List<TranslatorDto> result = historyTranslationService.getTranslationsBySourceAndTarget(source, target);

        // Verification
        assertNotNull(result);
        assertEquals(translatorDtos, result);
    }

    @Test
    public void testGetAllHistoryTranslations() {
        // Mocking
        List<HistoryTranslation> historyTranslations = new ArrayList<>();
        historyTranslations.add(new HistoryTranslation());
        when(historyTranslationRepository.findAll()).thenReturn(historyTranslations);

        // Testing
        List<HistoryTranslation> result = historyTranslationService.getAllHistoryTranslations();

        // Verification
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testDeleteHistoryTranslationById() {
        // Mocking
        long id = 1L;
        when(historyTranslationRepository.existsById(id)).thenReturn(true);

        // Testing
        historyTranslationService.deleteHistoryTranslationById(id);

        // Verification
        verify(simpleCacheComponent, times(1)).remove(id);
        verify(historyTranslationRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateHistoryTranslation() {
        // Mocking
        long id = 1L;
        HistoryTranslation updatedTranslation = new HistoryTranslation();
        when(historyTranslationRepository.existsById(id)).thenReturn(true);
        when(historyTranslationRepository.findById(id)).thenReturn(Optional.of(new HistoryTranslation()));

        // Testing
        HistoryTranslation result = historyTranslationService.updateHistoryTranslation(id, updatedTranslation);

        // Verification
        assertNull(result); // Assuming that this method returns null
    }

    @Test
    public void testCreateHistoryTranslationsBulk() {
        // Mocking
        List<HistoryTranslation> historyTranslations = new ArrayList<>();
        HistoryTranslation historyTranslation = new HistoryTranslation();
        historyTranslations.add(historyTranslation);
        when(historyTranslationRepository.save(historyTranslation)).thenReturn(historyTranslation);

        // Testing
        historyTranslationService.createHistoryTranslationsBulk(historyTranslations);

        // Verification
        verify(historyTranslationRepository, times(1)).save(historyTranslation);
    }

    @Test
    public void testConvertEntityToDto() {
        // Mocking
        HistoryTranslation historyTranslation = new HistoryTranslation();
        when(historyTranslationMapper.entityToDto(historyTranslation)).thenReturn(new HistoryTranslationDto());

        // Testing
        HistoryTranslationDto result = historyTranslationService.convertEntityToDto(historyTranslation);

        // Verification
        assertNotNull(result);
    }

    @Test
    public void testConvertDtoToEntity() {
        // Mocking
        HistoryTranslationDto historyTranslationDto = new HistoryTranslationDto();
        when(historyTranslationMapper.dtoToEntity(historyTranslationDto)).thenReturn(new HistoryTranslation());

        // Testing
        HistoryTranslation result = historyTranslationService.convertDtoToEntity(historyTranslationDto);

        // Verification
        assertNotNull(result);
    }
}
