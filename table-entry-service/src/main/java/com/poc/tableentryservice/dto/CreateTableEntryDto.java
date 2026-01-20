package com.poc.tableentryservice.dto;

/**
 * Data Transfer Object for creating a new TableEntry.
 * Contains only the fields that can be set by the client.
 */
public record CreateTableEntryDto(
        Integer numberValue,
        String selectorValue,
        String freeText
) {
}
