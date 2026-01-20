package com.poc.tableentryservice.service;

import com.poc.tableentryservice.dto.CreateTableEntryDto;
import com.poc.tableentryservice.dto.PagedResponse;
import com.poc.tableentryservice.dto.TableEntryDto;
import com.poc.tableentryservice.entity.TableEntry;
import com.poc.tableentryservice.exception.EntryNotFoundException;
import com.poc.tableentryservice.mapper.TableEntryMapper;
import com.poc.tableentryservice.repository.TableEntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.Set;

/**
 * Implementation of {@link TableEntryService}.
 * Handles business logic and transaction management for table entries.
 */
@ApplicationScoped
public class TableEntryServiceImpl implements TableEntryService {

    private static final Logger LOG = Logger.getLogger(TableEntryServiceImpl.class);

    private static final int MAX_PAGE_SIZE = 50;
    private static final String DEFAULT_SORT_BY = "createdAt";
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "createdAt", "numberValue", "selectorValue", "freeText"
    );
    private static final String ASC_ORDER = "asc";

    private final TableEntryRepository repository;
    private final TableEntryMapper mapper;

    /**
     * Constructs the service with the required dependencies.
     *
     * @param repository the table entry repository
     * @param mapper     the MapStruct mapper
     */
    public TableEntryServiceImpl(TableEntryRepository repository, TableEntryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PagedResponse<TableEntryDto> findAll(int page, int size, String sortBy, String sortDirection) {
        int pageSize = Math.min(size, MAX_PAGE_SIZE);
        String sortField = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : DEFAULT_SORT_BY;
        boolean ascending = ASC_ORDER.equalsIgnoreCase(sortDirection);
        var entries = repository.findAllPaged(page, pageSize, sortField, ascending);
        long total = repository.count();
        return PagedResponse.of(mapper.toDtoList(entries), page, pageSize, total);
    }

    @Override
    public TableEntryDto findById(Long id) {
        TableEntry entity = repository.findById(id);
        if (entity == null) {
            LOG.warnf("Entry not found with id: %d", id);
            throw new EntryNotFoundException(id);
        }
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public TableEntryDto create(CreateTableEntryDto dto) {
        TableEntry entity = mapper.toEntity(dto);
        repository.persist(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public TableEntryDto update(Long id, CreateTableEntryDto dto) {
        TableEntry existing = repository.findById(id);
        if (existing == null) {
            LOG.warnf("Cannot update - entry not found with id: %d", id);
            throw new EntryNotFoundException(id);
        }
        mapper.updateEntity(dto, existing);
        return mapper.toDto(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean deleted = repository.deleteById(id);
        if (!deleted) {
            LOG.warnf("Cannot delete - entry not found with id: %d", id);
            throw new EntryNotFoundException(id);
        }
    }
}
