package com.poc.tableentryservice.repository;

import com.poc.tableentryservice.entity.TableEntry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TableEntryRepository implements PanacheRepository<TableEntry> {
}
