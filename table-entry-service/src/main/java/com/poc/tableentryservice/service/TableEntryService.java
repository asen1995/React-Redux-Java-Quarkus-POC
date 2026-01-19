package com.poc.tableentryservice.service;

import com.poc.tableentryservice.entity.TableEntry;
import com.poc.tableentryservice.repository.TableEntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TableEntryService {

    private final TableEntryRepository repository;

    public TableEntryService(TableEntryRepository repository) {
        this.repository = repository;
    }

    public List<TableEntry> findAll() {
        return repository.listAll();
    }

    public Optional<TableEntry> findById(Long id) {
        return Optional.ofNullable(repository.findById(id));
    }

    @Transactional
    public TableEntry create(TableEntry entry) {
        repository.persist(entry);
        return entry;
    }

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

    @Transactional
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }
}
