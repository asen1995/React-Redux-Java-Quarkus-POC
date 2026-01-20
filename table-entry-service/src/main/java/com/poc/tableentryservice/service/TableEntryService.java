package com.poc.tableentryservice.service;

import com.poc.tableentryservice.dto.CreateTableEntryDto;
import com.poc.tableentryservice.dto.PagedResponse;
import com.poc.tableentryservice.dto.TableEntryDto;
import com.poc.tableentryservice.entity.TableEntry;
import com.poc.tableentryservice.mapper.TableEntryMapper;
import com.poc.tableentryservice.repository.TableEntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

/**
 * Service layer for managing {@link TableEntry} entities.
 * Handles business logic and transaction management.
 */
@ApplicationScoped
public class TableEntryService {

    private final TableEntryRepository repository;
    private final TableEntryMapper mapper;

    /**
     * Constructs the service with the required dependencies.
     *
     * @param repository the table entry repository
     * @param mapper     the MapStruct mapper
     */
    public TableEntryService(TableEntryRepository repository, TableEntryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    private static final int MAX_PAGE_SIZE = 50;
    private static final String DEFAULT_SORT_BY = "createdAt";
    private static final java.util.Set<String> ALLOWED_SORT_FIELDS = java.util.Set.of(
            "createdAt", "numberValue", "selectorValue", "freeText"
    );

    /**
     * Retrieves table entries with pagination and sorting.
     *
     * @param page          the page number (0-indexed)
     * @param size          the page size (max 50)
     * @param sortBy        the field to sort by (default: createdAt)
     * @param sortDirection the sort direction: "asc" or "desc" (default: desc)
     * @return paginated response with entries
     */
    public PagedResponse<TableEntryDto> findAll(int page, int size, String sortBy, String sortDirection) {
        int pageSize = Math.min(size, MAX_PAGE_SIZE);
        String sortField = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : DEFAULT_SORT_BY;
        boolean ascending = "asc".equalsIgnoreCase(sortDirection);
        var entries = repository.findAllPaged(page, pageSize, sortField, ascending);
        long total = repository.count();
        return PagedResponse.of(mapper.toDtoList(entries), page, pageSize, total);
    }

    /**
     * Finds a table entry by its ID.
     *
     * @param id the entry ID
     * @return an Optional containing the entry DTO if found, empty otherwise
     */
    public Optional<TableEntryDto> findById(Long id) {
        return Optional.ofNullable(repository.findById(id))
                .map(mapper::toDto);
    }

    /**
     * Creates a new table entry.
     *
     * @param dto the entry data to create
     * @return the persisted entry as DTO with generated ID and timestamps
     */
    @Transactional
    public TableEntryDto create(CreateTableEntryDto dto) {
        TableEntry entity = mapper.toEntity(dto);
        repository.persist(entity);
        return mapper.toDto(entity);
    }

    /**
     * Updates an existing table entry.
     *
     * @param id  the ID of the entry to update
     * @param dto the new entry data
     * @return an Optional containing the updated entry DTO if found, empty otherwise
     */
    @Transactional
    public Optional<TableEntryDto> update(Long id, CreateTableEntryDto dto) {
        TableEntry existing = repository.findById(id);
        if (existing == null) {
            return Optional.empty();
        }
        mapper.updateEntity(dto, existing);
        return Optional.of(mapper.toDto(existing));
    }

    /**
     * Deletes a table entry by its ID.
     *
     * @param id the ID of the entry to delete
     * @return true if the entry was deleted, false if not found
     */
    @Transactional
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }
}
