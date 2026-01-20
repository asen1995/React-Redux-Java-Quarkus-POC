package com.poc.tableentryservice.service;

import com.poc.tableentryservice.entity.TableEntry;
import com.poc.tableentryservice.repository.TableEntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing {@link TableEntry} entities.
 * Handles business logic and transaction management.
 */
@ApplicationScoped
public class TableEntryService {

    private final TableEntryRepository repository;

    /**
     * Constructs the service with the required repository.
     *
     * @param repository the table entry repository
     */
    public TableEntryService(TableEntryRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all table entries.
     *
     * @return list of all entries
     */
    public List<TableEntry> findAll() {
        return repository.listAll();
    }

    /**
     * Finds a table entry by its ID.
     *
     * @param id the entry ID
     * @return an Optional containing the entry if found, empty otherwise
     */
    public Optional<TableEntry> findById(Long id) {
        return Optional.ofNullable(repository.findById(id));
    }

    /**
     * Creates a new table entry.
     *
     * @param entry the entry to create
     * @return the persisted entry with generated ID
     */
    @Transactional
    public TableEntry create(TableEntry entry) {
        repository.persist(entry);
        return entry;
    }

    /**
     * Updates an existing table entry.
     *
     * @param id    the ID of the entry to update
     * @param entry the new entry data
     * @return an Optional containing the updated entry if found, empty otherwise
     */
    @Transactional
    public Optional<TableEntry> update(Long id, TableEntry entry) {
        TableEntry existing = repository.findById(id);
        if (existing == null) {
            return Optional.empty();
        }
        existing.numberValue = entry.numberValue;
        existing.selectorValue = entry.selectorValue;
        existing.freeText = entry.freeText;
        return Optional.of(existing);
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
