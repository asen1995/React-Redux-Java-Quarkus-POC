package com.poc.tableentryservice.repository;

import com.poc.tableentryservice.entity.TableEntry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * Repository for {@link TableEntry} entities.
 * Provides CRUD operations through Panache.
 */
@ApplicationScoped
public class TableEntryRepository implements PanacheRepository<TableEntry> {

    /**
     * Finds all entries with pagination and sorting.
     *
     * @param page      the page number (0-indexed)
     * @param size      the page size
     * @param sortBy    the field to sort by
     * @param ascending true for ascending, false for descending
     * @return list of entries for the requested page
     */
    public List<TableEntry> findAllPaged(int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return findAll(sort)
                .page(Page.of(page, size))
                .list();
    }
}
