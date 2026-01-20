package com.poc.tableentryservice.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for TableEntry responses.
 * Contains all fields including auto-generated ones.
 */
public record TableEntryDto(
        Long id,
        Integer numberValue,
        String selectorValue,
        String freeText,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
