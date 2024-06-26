package com.myapp.backend.service;

import com.myapp.backend.component.SimpleCacheComponent;
import com.myapp.backend.dto.HistoryTranslationDto;
import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.exception.ResourceNotFoundException;
import com.myapp.backend.mapper.HistoryTranslationMapper;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.HistoryTranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HistoryTranslationServiceTest {
    @Mock
    private HistoryTranslationRepository historyTranslationRepository;

    @Mock
    private Logger log;

    @Mock
    private HistoryTranslationMapper historyTranslationMapper;

    @Mock
    private SimpleCacheComponent<HistoryTranslation> simpleCacheComponent;

    @InjectMocks
    private HistoryTranslationService historyTranslationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deleteHistoryTranslationById_ExistingId_RemovesFromCacheAndDeletesFromRepository() {
        // Arrange
        long id = 1L;

        // Mock behavior
        when(historyTranslationRepository.existsById(id)).thenReturn(true);

        // Act
        historyTranslationService.deleteHistoryTranslationById(id);

        // Assert and verify
        verify(simpleCacheComponent, times(1)).remove(id);
        verify(historyTranslationRepository, times(1)).deleteById(id);

        // Debugging statements
        System.out.println("Verify log.info invocations: " + Mockito.mockingDetails(log).getInvocations());
    }

    @Test
    void deleteHistoryTranslationById_NonExistingId_ThrowsResourceNotFoundException() {
        // Arrange
        Long nonExistingId = 1L;

        // Mock the behavior of historyTranslationRepository.existsById() to return false
        when(historyTranslationRepository.existsById(nonExistingId)).thenReturn(false);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            historyTranslationService.deleteHistoryTranslationById(nonExistingId);
        });

        // Verify that simpleCacheComponent.remove is never called
        verify(simpleCacheComponent, never()).remove(anyLong());

        // Verify that historyTranslationRepository.deleteById is never called
        verify(historyTranslationRepository, never()).deleteById(anyLong());

        // Verify that log.info is never called
        verify(log, never()).info(anyString());
    }

    @Test
    void updateHistoryTranslation_ExistingId_ReturnsUpdatedTranslation() {
        // Arrange
        long id = 1L;

        HistoryTranslation existingTranslation = new HistoryTranslation();
        existingTranslation.setId(id);
        existingTranslation.setDate(new Timestamp(System.currentTimeMillis()));
        existingTranslation.setSource("source");
        existingTranslation.setTarget("target");

        HistoryTranslation updatedTranslation = new HistoryTranslation();
        updatedTranslation.setId(id);
        existingTranslation.setDate(new Timestamp(System.currentTimeMillis() + 1000));
        updatedTranslation.setSource("updated source");
        updatedTranslation.setTarget("updated target");

        // Mock behavior
        when(historyTranslationRepository.existsById(id)).thenReturn(true);
        when(historyTranslationRepository.findById(id)).thenReturn(Optional.of(existingTranslation));
        when(historyTranslationRepository.save(any(HistoryTranslation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        HistoryTranslation result = historyTranslationService.updateHistoryTranslation(id, updatedTranslation);

        // Assert
        assertNotNull(result);
        assertEquals(updatedTranslation.getId(), result.getId());
        assertEquals(updatedTranslation.getDate(), result.getDate());
        assertEquals(updatedTranslation.getSource(), result.getSource());
        assertEquals(updatedTranslation.getTarget(), result.getTarget());
        assertEquals(updatedTranslation.getTranslator(), result.getTranslator());

        // Verify that log.info is called with the correct message
        System.out.println("Verify log.info invocations: " + Mockito.mockingDetails(log).getInvocations());
    }

    @Test
    void createHistoryTranslation_CreatesHistoryTranslation() {
        // Arrange
        Translator translator = new Translator();
        translator.setId(1L);
        translator.setEn("Hello");
        translator.setRu("Привет");

        String target = "target";
        String source = "source";

        // Mock behavior
        doAnswer(invocation -> {
            HistoryTranslation historyTranslation = invocation.getArgument(0);
            assertNotNull(historyTranslation);
            assertEquals(translator, historyTranslation.getTranslator());
            assertNotNull(historyTranslation.getDate());
            assertEquals(target, historyTranslation.getTarget());
            assertEquals(source, historyTranslation.getSource());
            return null;
        }).when(historyTranslationRepository).save(any(HistoryTranslation.class));

        // Act
        historyTranslationService.createHistoryTranslation(translator, target, source);

        // Assert
        // Verify that historyTranslationRepository.save is called with the correct parameters
        verify(historyTranslationRepository, times(1)).save(any(HistoryTranslation.class));
    }

    @Test
    void createHistoryTranslationsBulk_NonEmptyList_SavesAndLogsHistoryTranslations() {
        // Arrange
        List<HistoryTranslation> historyTranslations = new ArrayList<>();
        HistoryTranslation historyTranslation1 = new HistoryTranslation();
        // Populate historyTranslation1 with data
        historyTranslations.add(historyTranslation1);

        // Mock behavior
        when(historyTranslationRepository.save(any(HistoryTranslation.class))).thenAnswer(invocation -> {
            HistoryTranslation savedHistoryTranslation = invocation.getArgument(0);
            savedHistoryTranslation.setId(100L); // Simulating saved ID
            return savedHistoryTranslation;
        });

        // Act
        historyTranslationService.createHistoryTranslationsBulk(historyTranslations);

        // Assert
        // Verify that historyTranslationRepository.save is called for each history translation
        verify(historyTranslationRepository, times(historyTranslations.size())).save(any(HistoryTranslation.class));

        // Verify that log.info is called with the correct message for each saved history translation
        System.out.println("History translation saved ID" + Mockito.mockingDetails(log).getInvocations());
    }

    @Test
    void testCreateHistoryTranslation() {
        HistoryTranslation historyTranslation = new HistoryTranslation();
        when(historyTranslationRepository.save(historyTranslation)).thenReturn(historyTranslation);

        HistoryTranslation result = historyTranslationService.createHistoryTranslation(historyTranslation);

        verify(historyTranslationRepository, times(1)).save(historyTranslation);

        assertEquals(historyTranslation, result);
    }

    @Test
    void testGetHistoryTranslationById_foundInCache() {
        HistoryTranslation historyTranslation = new HistoryTranslation();
        historyTranslation.setId(1L);
        when(simpleCacheComponent.get(1L)).thenReturn(historyTranslation);

        HistoryTranslation result = historyTranslationService.getHistoryTranslationById(1L);

        verify(simpleCacheComponent, times(1)).get(1L);

        verifyNoInteractions(historyTranslationRepository);

        assertEquals(historyTranslation, result);
    }

    @Test
    void testGetHistoryTranslationById_notFoundInCache_foundInDB() {
        when(simpleCacheComponent.get(1L)).thenReturn(null);
        when(historyTranslationRepository.existsById(1L)).thenReturn(true);
        HistoryTranslation historyTranslation = new HistoryTranslation();
        when(historyTranslationRepository.findById(1L)).thenReturn(Optional.of(historyTranslation));
        HistoryTranslation result = historyTranslationService.getHistoryTranslationById(1L);
        verify(historyTranslationRepository, times(1)).existsById(1L);
        verify(historyTranslationRepository, times(1)).findById(1L);
        verify(simpleCacheComponent, times(1)).put(1L, historyTranslation);
        assertEquals(historyTranslation, result);
    }

    @Test
    void testGetHistoryTranslationById_notFoundInCache_notFoundInDB() {
        when(simpleCacheComponent.get(1L)).thenReturn(null);
        when(historyTranslationRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> historyTranslationService.getHistoryTranslationById(1L));

        verify(historyTranslationRepository, times(1)).existsById(1L);

        verifyNoMoreInteractions(historyTranslationRepository);

        verify(simpleCacheComponent, times(1)).get(1L);
    }

    @Test
    void testGetTranslationsBySourceAndTarget() {
        String source = "English";
        String target = "Russian";

        List<TranslatorDto> expectedTranslators = new ArrayList<>();
        when(historyTranslationRepository.getTranslationsBySourceAndTarget(source, target)).thenReturn(expectedTranslators);

        List<TranslatorDto> result = historyTranslationService.getTranslationsBySourceAndTarget(source, target);

        verify(historyTranslationRepository, times(1)).getTranslationsBySourceAndTarget(source, target);

        assertEquals(expectedTranslators, result);
    }

    @Test
    void testGetAllHistoryTranslations() {
        List<HistoryTranslation> expectedHistoryTranslations = new ArrayList<>();
        when(historyTranslationRepository.findAll()).thenReturn(expectedHistoryTranslations);

        List<HistoryTranslation> result = historyTranslationService.getAllHistoryTranslations();

        verify(historyTranslationRepository, times(1)).findAll();

        assertEquals(expectedHistoryTranslations, result);
    }

    @Test
    void testGetAllHistoryTranslationsDto() {
        List<HistoryTranslation> historyTranslations = new ArrayList<>();
        historyTranslations.add(new HistoryTranslation());
        historyTranslations.add(new HistoryTranslation());
        historyTranslations.add(new HistoryTranslation());
        when(historyTranslationRepository.findAll()).thenReturn(historyTranslations);

        List<HistoryTranslationDto> expectedHistoryTranslationsDto = new ArrayList<>();
        HistoryTranslationDto dto1 = new HistoryTranslationDto();
        HistoryTranslationDto dto2 = new HistoryTranslationDto();
        HistoryTranslationDto dto3 = new HistoryTranslationDto();
        when(historyTranslationMapper.entityToDto(historyTranslations.get(0))).thenReturn(dto1);
        when(historyTranslationMapper.entityToDto(historyTranslations.get(1))).thenReturn(dto2);
        when(historyTranslationMapper.entityToDto(historyTranslations.get(2))).thenReturn(dto3);
        expectedHistoryTranslationsDto.add(dto1);
        expectedHistoryTranslationsDto.add(dto2);
        expectedHistoryTranslationsDto.add(dto3);

        List<HistoryTranslationDto> result = historyTranslationService.getAllHistoryTranslationsDto();

        verify(historyTranslationRepository, times(1)).findAll();

        verify(historyTranslationMapper, times(1)).entityToDto(historyTranslations.get(0));
        verify(historyTranslationMapper, times(1)).entityToDto(historyTranslations.get(1));
        verify(historyTranslationMapper, times(1)).entityToDto(historyTranslations.get(2));

        assertEquals(expectedHistoryTranslationsDto, result);
    }


    @Test
    void testConvertEntityToDto() {
        // Mocking
        HistoryTranslation historyTranslation = new HistoryTranslation();
        when(historyTranslationMapper.entityToDto(historyTranslation)).thenReturn(new HistoryTranslationDto());

        // Testing
        HistoryTranslationDto result = historyTranslationService.convertEntityToDto(historyTranslation);

        // Verification
        assertNotNull(result);
    }

    @Test
    void testConvertDtoToEntity() {
        // Mocking
        HistoryTranslationDto historyTranslationDto = new HistoryTranslationDto();
        when(historyTranslationMapper.dtoToEntity(historyTranslationDto)).thenReturn(new HistoryTranslation());

        // Testing
        HistoryTranslation result = historyTranslationService.convertDtoToEntity(historyTranslationDto);

        // Verification
        assertNotNull(result);
    }
}
