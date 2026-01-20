package com.poc.tableentryservice.service;

import com.poc.tableentryservice.dto.CreateTableEntryDto;
import com.poc.tableentryservice.dto.PagedResponse;
import com.poc.tableentryservice.dto.TableEntryDto;
import com.poc.tableentryservice.entity.TableEntry;
import com.poc.tableentryservice.exception.EntryNotFoundException;
import com.poc.tableentryservice.mapper.TableEntryMapper;
import com.poc.tableentryservice.repository.TableEntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link TableEntryServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class TableEntryServiceImplTest {

    @Mock
    private TableEntryRepository repository;

    @Mock
    private TableEntryMapper mapper;

    private TableEntryServiceImpl service;

    private TableEntry sampleEntity;
    private TableEntryDto sampleDto;
    private CreateTableEntryDto createDto;

    @BeforeEach
    void setUp() {
        service = new TableEntryServiceImpl(repository, mapper);

        sampleEntity = new TableEntry();
        sampleEntity.id = 1L;
        sampleEntity.numberValue = 42;
        sampleEntity.selectorValue = "Option A";
        sampleEntity.freeText = "Test text";
        sampleEntity.createdAt = LocalDateTime.now();
        sampleEntity.updatedAt = LocalDateTime.now();

        sampleDto = new TableEntryDto(
                1L, 42, "Option A", "Test text",
                sampleEntity.createdAt, sampleEntity.updatedAt
        );

        createDto = new CreateTableEntryDto(42, "Option A", "Test text");
    }

    @Test
    void findAll_returnsPagedResponse() {
        List<TableEntry> entities = List.of(sampleEntity);
        List<TableEntryDto> dtos = List.of(sampleDto);

        when(repository.findAllPaged(0, 10, "createdAt", false)).thenReturn(entities);
        when(repository.count()).thenReturn(1L);
        when(mapper.toDtoList(entities)).thenReturn(dtos);

        PagedResponse<TableEntryDto> result = service.findAll(0, 10, "createdAt", "desc");

        assertEquals(1, result.content().size());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(1, result.totalElements());
        verify(repository).findAllPaged(0, 10, "createdAt", false);
    }


    @Test
    void findById_whenFound_returnsDto() {
        when(repository.findById(1L)).thenReturn(sampleEntity);
        when(mapper.toDto(sampleEntity)).thenReturn(sampleDto);

        TableEntryDto result = service.findById(1L);

        assertEquals(sampleDto, result);
    }

    @Test
    void findById_whenNotFound_throwsException() {
        when(repository.findById(999L)).thenReturn(null);

        assertThrows(EntryNotFoundException.class, () -> service.findById(999L));
    }

    @Test
    void create_persistsAndReturnsDto() {
        when(mapper.toEntity(createDto)).thenReturn(sampleEntity);
        when(mapper.toDto(sampleEntity)).thenReturn(sampleDto);

        TableEntryDto result = service.create(createDto);

        assertEquals(sampleDto, result);
        verify(repository).persist(sampleEntity);
    }

    @Test
    void replace_whenFound_replacesAndReturnsDto() {
        when(repository.findById(1L)).thenReturn(sampleEntity);
        when(mapper.toDto(sampleEntity)).thenReturn(sampleDto);

        TableEntryDto result = service.replace(1L, createDto);

        assertEquals(sampleDto, result);
        assertEquals(createDto.numberValue(), sampleEntity.numberValue);
        assertEquals(createDto.selectorValue(), sampleEntity.selectorValue);
        assertEquals(createDto.freeText(), sampleEntity.freeText);
    }

    @Test
    void replace_whenNotFound_throwsException() {
        when(repository.findById(999L)).thenReturn(null);

        assertThrows(EntryNotFoundException.class, () -> service.replace(999L, createDto));
    }

    @Test
    void patch_whenFound_updatesOnlyProvidedFields() {
        when(repository.findById(1L)).thenReturn(sampleEntity);
        when(mapper.toDto(sampleEntity)).thenReturn(sampleDto);
        CreateTableEntryDto partialDto = new CreateTableEntryDto(99, null, null);

        service.patch(1L, partialDto);

        assertEquals(99, sampleEntity.numberValue);
        assertEquals("Option A", sampleEntity.selectorValue); // unchanged
        assertEquals("Test text", sampleEntity.freeText); // unchanged
    }

    @Test
    void patch_whenNotFound_throwsException() {
        when(repository.findById(999L)).thenReturn(null);

        assertThrows(EntryNotFoundException.class, () -> service.patch(999L, createDto));
    }

    @Test
    void delete_whenFound_succeeds() {
        when(repository.deleteById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    void delete_whenNotFound_throwsException() {
        when(repository.deleteById(999L)).thenReturn(false);

        assertThrows(EntryNotFoundException.class, () -> service.delete(999L));
    }
}
