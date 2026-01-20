package com.poc.tableentryservice.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity representing a table entry with three fields:
 * a numeric value, a selector value, and free text.
 */
@Entity
@Table(name = "table_entry")
public class TableEntry extends PanacheEntity {

    /**
     * Numeric value for the entry.
     */
    @Column(name = "number_value", nullable = false)
    public Integer numberValue;

    /**
     * Selected option value from a predefined list.
     */
    @Column(name = "selector_value", nullable = false)
    public String selectorValue;

    /**
     * Free-form text input.
     */
    @Column(name = "free_text", nullable = false)
    public String freeText;

    /**
     * Default constructor required by JPA.
     */
    public TableEntry() {
    }

    /**
     * Creates a new TableEntry with the specified values.
     *
     * @param numberValue   the numeric value
     * @param selectorValue the selected option
     * @param freeText      the free text content
     */
    public TableEntry(Integer numberValue, String selectorValue, String freeText) {
        this.numberValue = numberValue;
        this.selectorValue = selectorValue;
        this.freeText = freeText;
    }
}
