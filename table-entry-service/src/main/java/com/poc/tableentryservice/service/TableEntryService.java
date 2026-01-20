package com.poc.tableentryservice.service;

import com.poc.tableentryservice.dto.CreateTableEntryDto;
import com.poc.tableentryservice.dto.PagedResponse;
import com.poc.tableentryservice.dto.TableEntryDto;
import com.poc.tableentryservice.exception.EntryNotFoundException;

/**
 * Service interface for managing table entries.
 * Defines the contract for business logic operations.
 */
public interface TableEntryService {

    /**
     * Retrieves table entries with pagination and sorting.
     *
     * @param page          the page number (0-indexed)
     * @param size          the page size (max 50)
     * @param sortBy        the field to sort by (default: createdAt)
     * @param sortDirection the sort direction: "asc" or "desc" (default: desc)
     * @return paginated response with entries
     */
    PagedResponse<TableEntryDto> findAll(int page, int size, String sortBy, String sortDirection);

    /**
     * Finds a table entry by its ID.
     *
     * @param id the entry ID
     * @return the entry DTO
     * @throws EntryNotFoundException if the entry is not found
     */
    TableEntryDto findById(Long id);

    /**
     * Creates a new table entry.
     *
     * @param dto the entry data to create
     * @return the persisted entry as DTO with generated ID and timestamps
     */
    TableEntryDto create(CreateTableEntryDto dto);

    /**
     * Updates an existing table entry.
     *
     * @param id  the ID of the entry to update
     * @param dto the new entry data
     * @return the updated entry DTO
     * @throws EntryNotFoundException if the entry is not found
     */
    TableEntryDto update(Long id, CreateTableEntryDto dto);

    /**
     * Deletes a table entry by its ID.
     *
     * @param id the ID of the entry to delete
     * @throws EntryNotFoundException if the entry is not found
     */
    void delete(Long id);
}
