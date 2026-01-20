package com.poc.tableentryservice.repository;

import com.poc.tableentryservice.entity.TableEntry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for {@link TableEntry} entities.
 * Provides CRUD operations through Panache.
 */
@ApplicationScoped
public class TableEntryRepository implements PanacheRepository<TableEntry> {
}
