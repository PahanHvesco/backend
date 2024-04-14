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
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HistoryTranslationServiceTest {
    @Mock
    private HistoryTranslationRepository historyTranslationRepository;

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
    void testCreateHistoryTranslation() {
        // Устанавливаем заглушки для методов репозитория
        HistoryTranslation historyTranslation = new HistoryTranslation();
        when(historyTranslationRepository.save(historyTranslation)).thenReturn(historyTranslation);

        // Вызываем метод сервиса
        HistoryTranslation result = historyTranslationService.createHistoryTranslation(historyTranslation);

        // Проверяем, что метод репозитория был вызван один раз с правильным аргументом
        verify(historyTranslationRepository, times(1)).save(historyTranslation);

        // Проверяем, что возвращенный результат равен ожидаемому объекту
        assertEquals(historyTranslation, result);
    }

    @Test
    void testGetHistoryTranslationById_foundInCache() {
        // Создаем объект HistoryTranslation и устанавливаем заглушку для метода get у SimpleCacheComponent
        HistoryTranslation historyTranslation = new HistoryTranslation();
        historyTranslation.setId(1L);
        when(simpleCacheComponent.get(1L)).thenReturn(historyTranslation);

        // Вызываем метод сервиса
        HistoryTranslation result = historyTranslationService.getHistoryTranslationById(1L);

        // Проверяем, что метод get у SimpleCacheComponent был вызван один раз с правильным аргументом
        verify(simpleCacheComponent, times(1)).get(1L);

        // Проверяем, что репозиторий не вызывался
        verifyNoInteractions(historyTranslationRepository);

        // Проверяем, что возвращенный результат равен ожидаемому объекту
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
        // Устанавливаем заглушки для методов existsById и findById у HistoryTranslationRepository
        when(simpleCacheComponent.get(1L)).thenReturn(null); // Подменяем заглушку, чтобы вернуть null
        when(historyTranslationRepository.existsById(1L)).thenReturn(false);

        // Вызываем метод сервиса и проверяем, что выбрасывается исключение ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> historyTranslationService.getHistoryTranslationById(1L));

        // Проверяем, что метод existsById у HistoryTranslationRepository был вызван один раз с правильным аргументом
        verify(historyTranslationRepository, times(1)).existsById(1L);

        // Проверяем, что метод findById у HistoryTranslationRepository не вызывался
        verifyNoMoreInteractions(historyTranslationRepository);

        // Проверяем, что метод get у SimpleCacheComponent был вызван один раз с правильным аргументом
        verify(simpleCacheComponent, times(1)).get(1L);
    }

    @Test
    void testGetTranslationsBySourceAndTarget() {
        String source = "English";
        String target = "Russian";

        // Устанавливаем заглушку для метода getTranslationsBySourceAndTarget у HistoryTranslationRepository
        List<TranslatorDto> expectedTranslators = new ArrayList<>();
        when(historyTranslationRepository.getTranslationsBySourceAndTarget(source, target)).thenReturn(expectedTranslators);

        // Вызываем метод сервиса
        List<TranslatorDto> result = historyTranslationService.getTranslationsBySourceAndTarget(source, target);

        // Проверяем, что метод getTranslationsBySourceAndTarget у HistoryTranslationRepository был вызван один раз с правильными аргументами
        verify(historyTranslationRepository, times(1)).getTranslationsBySourceAndTarget(source, target);

        // Проверяем, что возвращенный результат равен ожидаемому списку
        assertEquals(expectedTranslators, result);
    }

    @Test
    void testGetAllHistoryTranslations() {
        // Устанавливаем заглушку для метода findAll у HistoryTranslationRepository
        List<HistoryTranslation> expectedHistoryTranslations = new ArrayList<>();
        when(historyTranslationRepository.findAll()).thenReturn(expectedHistoryTranslations);

        // Вызываем метод сервиса
        List<HistoryTranslation> result = historyTranslationService.getAllHistoryTranslations();

        // Проверяем, что метод findAll у HistoryTranslationRepository был вызван один раз
        verify(historyTranslationRepository, times(1)).findAll();

        // Проверяем, что возвращенный результат равен ожидаемому списку
        assertEquals(expectedHistoryTranslations, result);
    }

    @Test
    void testGetAllHistoryTranslationsDto() {
        // Создаем список объектов HistoryTranslation и устанавливаем заглушку для метода findAll у HistoryTranslationRepository
        List<HistoryTranslation> historyTranslations = new ArrayList<>();
        historyTranslations.add(new HistoryTranslation());
        historyTranslations.add(new HistoryTranslation());
        historyTranslations.add(new HistoryTranslation());
        when(historyTranslationRepository.findAll()).thenReturn(historyTranslations);

        // Создаем список объектов HistoryTranslationDto и устанавливаем заглушку для метода entityToDto у HistoryTranslationMapper
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

        // Вызываем метод сервиса
        List<HistoryTranslationDto> result = historyTranslationService.getAllHistoryTranslationsDto();

        // Проверяем, что метод findAll у HistoryTranslationRepository был вызван один раз
        verify(historyTranslationRepository, times(1)).findAll();

        // Проверяем, что метод entityToDto у HistoryTranslationMapper был вызван три раза с конкретными аргументами
        verify(historyTranslationMapper, times(1)).entityToDto(historyTranslations.get(0));
        verify(historyTranslationMapper, times(1)).entityToDto(historyTranslations.get(1));
        verify(historyTranslationMapper, times(1)).entityToDto(historyTranslations.get(2));

        // Проверяем, что возвращенный результат равен ожидаемому списку
        assertEquals(expectedHistoryTranslationsDto, result);
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
